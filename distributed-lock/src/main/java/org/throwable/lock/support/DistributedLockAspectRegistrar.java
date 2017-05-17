package org.throwable.lock.support;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.ProxyMethodInvocation;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.throwable.lock.annotation.DistributedLock;
import org.throwable.lock.common.LockPolicyEnum;
import org.throwable.lock.exception.LockException;
import org.throwable.lock.exception.UnMatchedLockKeyException;
import org.throwable.utils.ArrayUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author throwable
 * @version v1.0
 * @function
 * @since 2017/5/17 12:09
 */
@Slf4j
public class DistributedLockAspectRegistrar implements ImportBeanDefinitionRegistrar {

	private final DistributedLockTargetKeyMatcher keyMatcher = new DistributedLockTargetKeyMatcher();

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
										BeanDefinitionRegistry registry) {
		Advice advice = (MethodInterceptor) invocation -> {
			Method invocationMethod = invocation.getMethod();
			ProxyMethodInvocation pmi = (ProxyMethodInvocation) invocation;
			ProceedingJoinPoint pjp = new MethodInvocationProceedingJoinPoint(pmi);
			Signature signature = pjp.getSignature();
			MethodSignature methodSignature = (MethodSignature) signature;
			if (invocationMethod.isAnnotationPresent(DistributedLock.class)) {
				//FIXME 考虑重入锁的情况,多个注解同时使用
				DistributedLock[] lockAnnotations = invocationMethod.getDeclaredAnnotationsByType(DistributedLock.class);
				return processDistributedLockMethodInterceptor(lockAnnotations, invocation, methodSignature, registry);
			} else {
				return invocation.proceed();
			}
		};
		BeanDefinition aspectJBean = BeanDefinitionBuilder.genericBeanDefinition(AspectJExpressionPointcutAdvisor.class)
				//定义输出路径,一般是日志的输出类,方便排查问题
				.addPropertyValue("location", "$$distributedLockAspect##")
				//定义AspectJ切点表达式
				.addPropertyValue("expression", "@annotation(org.throwable.lock.annotation.DistributedLock)")
				//定义织入的增强对象,就是上面的自定义的around类型的advice的实现
				.addPropertyValue("advice", advice)
				.getBeanDefinition();
		registry.registerBeanDefinition("distributedLockAspect", aspectJBean);
	}

	private Object processDistributedLockMethodInterceptor(DistributedLock[] lockAnnotations,
														   MethodInvocation methodInvocation,
														   MethodSignature methodSignature,
														   BeanDefinitionRegistry registry) {
		Class<?> target = lockAnnotations[0].target();
		String keyName = lockAnnotations[0].keyName();
		LockPolicyEnum policy = lockAnnotations[0].policy();
		long waitSeconds = lockAnnotations[0].waitSeconds();
		String[] parameterNames = methodSignature.getParameterNames();
		if (null == parameterNames || parameterNames.length == 0) {
			throw new UnMatchedLockKeyException("@DistributedLock must be matched to parameter key!!!!Method parameters array's length is zero");
		}
		if (target.isAssignableFrom(String.class)) {
			List<String> parameters = ArrayUtils.arrayToList(parameterNames);
			if (parameters.contains(keyName)) {
				//FIXME 考虑重入锁的情况,多个注解同时使用
				return processDistributedLockWithStringTarget(policy, keyName, waitSeconds, methodInvocation, registry);
			}
			throw new LockException("@DistributedLock must be matched to parameter key!!!!Lock keyName must be match to a parameter name,keyName: " + keyName);
		} else {
			//FIXME 考虑重入锁的情况,多个注解同时使用
			return processDistributedLockWithClassTarget(target, policy, keyName, waitSeconds, methodInvocation, registry);
		}
	}

	private Object processDistributedLockWithStringTarget(LockPolicyEnum policy,
														  String lockPath,
														  long waitSeconds,
														  MethodInvocation methodInvocation,
														  BeanDefinitionRegistry registry) {
		org.throwable.lock.support.DistributedLock distributedLock
				= wrapBeanDefinitionRegistry(registry)
				.getBean(DistributedLockContext.class)
				.getLockByPolicyAndPath(policy, lockPath);
		try {
			if (!distributedLock.isHeldByCurrentThread()
					&& distributedLock.tryLock(waitSeconds, TimeUnit.SECONDS)) {
				return methodInvocation.proceed();
			}
		} catch (Throwable e) {
			throw new LockException("distributedLock execute #tryLock failed for timeout,lockPath: " + lockPath);
		} finally {
			try {
				distributedLock.release();
			} catch (Exception e) {
				//ignore
				log.warn("distributedLock execute #release failed!!!LockPath: " + lockPath);
			}
		}
		throw new LockException("distributedLock acquire lock failed for timeout,lockPath: " + lockPath);
	}

	private Object processDistributedLockWithClassTarget(Class<?> target,
														 LockPolicyEnum policy,
														 String lockPath,
														 long waitSeconds,
														 MethodInvocation methodInvocation,
														 BeanDefinitionRegistry registry) {
		Object[] parameters = methodInvocation.getArguments();
		if (null == parameters || parameters.length == 0) {
			throw new UnMatchedLockKeyException("@DistributedLock must be matched to parameter key!!!!Method parameters array's length is zero");
		}
		Object targetParam = null;
		for (Object parameter : parameters) {
			if (parameter.getClass().isAssignableFrom(target)) {
				targetParam = parameter;
				break;
			}
		}
		if (null == targetParam) {
			throw new UnMatchedLockKeyException("@DistributedLock must be matched to parameter key!!!!Target type must be matched to parameter type!!");
		}
		String targetKey = keyMatcher.matchAndReturnLockKeyByTargetObjectAndKeyName(targetParam, lockPath);
		return processDistributedLockWithStringTarget(policy, targetKey, waitSeconds, methodInvocation, registry);
	}

	private DefaultListableBeanFactory wrapBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
		return (DefaultListableBeanFactory) registry;
	}
}
