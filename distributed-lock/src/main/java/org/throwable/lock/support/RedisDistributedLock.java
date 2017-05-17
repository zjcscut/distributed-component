package org.throwable.lock.support;

import java.util.concurrent.TimeUnit;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/5/18 0:21
 */
public class RedisDistributedLock implements DistributedLock {

	@Override
	public void lock() throws Exception {

	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws Exception {
		return false;
	}

	@Override
	public void release() throws Exception {

	}

	@Override
	public boolean isLocked() {
		return false;
	}

	@Override
	public boolean isHeldByCurrentThread() {
		return false;
	}

	@Override
	public void forceUnlock() {

	}
}
