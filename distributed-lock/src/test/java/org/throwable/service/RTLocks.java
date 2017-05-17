package org.throwable.service;

import org.throwable.lock.annotation.DistributedLock;
import org.throwable.lock.common.LockPolicyEnum;
import org.throwable.lock.entity.User;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/5/18 2:33
 */
public class RTLocks {

	public RTLocks() {
	}

	@DistributedLock(policy = LockPolicyEnum.ZOOKEEPER, target = String.class, keyName = "account", waitSeconds = 11)
	@DistributedLock(policy = LockPolicyEnum.ZOOKEEPER, target = User.class, keyName = "account", waitSeconds = 11)
	public void process(){

	}
}
