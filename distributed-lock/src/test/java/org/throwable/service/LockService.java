package org.throwable.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.throwable.lock.annotation.DistributedLock;
import org.throwable.lock.annotation.DistributedLocks;
import org.throwable.lock.common.LockPolicyEnum;
import org.throwable.lock.entity.User;

/**
 * @author throwable
 * @version v1.0
 * @function
 * @since 2017/5/17 16:42
 */
@Service
@Slf4j
public class LockService {

	@DistributedLock(policy = LockPolicyEnum.ZOOKEEPER, target = String.class, keyName = "account", waitSeconds = 11)
	public void process(String account) {
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.warn("process account :" + account);
	}

	@DistributedLock(policy = LockPolicyEnum.ZOOKEEPER, target = User.class, keyName = "account", waitSeconds = 11)
	public void processTarget(User user) {
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.warn("process user :" + user.toString());
	}

	@DistributedLocks(locks = {
			@DistributedLock(policy = LockPolicyEnum.ZOOKEEPER, target = User.class, keyName = "account", waitSeconds = 11),
			@DistributedLock(policy = LockPolicyEnum.ZOOKEEPER, target = String.class, keyName = "name", waitSeconds = 11)
	})
	public void reentrantLockProcessTarget(User user, String name) {
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.warn("process user :" + user.toString());
	}
}
