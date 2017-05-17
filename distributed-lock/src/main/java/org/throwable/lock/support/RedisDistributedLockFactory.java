package org.throwable.lock.support;

import org.springframework.stereotype.Component;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/5/17 23:01
 */
@Component
public class RedisDistributedLockFactory implements DistributedLockFactory {

	@Override
	public DistributedLock createDistributedLockByPath( String lockPath) {
		return null;
	}
}
