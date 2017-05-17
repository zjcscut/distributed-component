package org.throwable.lock.annotation;

import org.springframework.context.annotation.Import;
import org.throwable.lock.support.DistributedLockAspectRegistrar;

import java.lang.annotation.*;

/**
 * @author throwable
 * @version v1.0
 * @function
 * @since 2017/5/17 11:58
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(DistributedLockAspectRegistrar.class)
public @interface EnableDistributedLock {
}
