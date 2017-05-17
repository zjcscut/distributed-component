package org.throwable.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.throwable.lock.annotation.DistributedLock;
import org.throwable.lock.common.LockPolicyEnum;

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
}
