package org.throwable.service;

import org.junit.Test;
import org.throwable.lock.annotation.DistributedLock;

import java.lang.reflect.Method;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/5/18 2:44
 */
public class RTLocksTest {

	@Test
	public void testRTLocks() throws Exception {
		Class<?> clazz = Class.forName("org.throwable.service.RTLocks");
		Method method = clazz.getDeclaredMethod("process");
		DistributedLock[] distributedLocks = method.getDeclaredAnnotationsByType(DistributedLock.class);

		System.out.println(method);
	}
}
