package org.throwable.service;

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
public class LockService {

    @DistributedLock(policy = LockPolicyEnum.ZOOKEEPER,keyName = "account")
    public void process(String account){
        System.out.println("process account :" + account);
    }
}
