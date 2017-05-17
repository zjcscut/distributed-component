package org.throwable.lock.support;

import org.junit.Test;
import org.throwable.lock.entity.User;

import static org.junit.Assert.*;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/5/18 2:09
 */
public class DistributedLockTargetKeyMatcherTest {

	private final DistributedLockTargetKeyMatcher matcher = new DistributedLockTargetKeyMatcher();

	@Test
	public void matchAndReturnLockKeyByTargetObjectAndKeyName() throws Exception {
		User user = new User();
		user.setId(10086L);
		user.setAge(24);
		user.setName("zjcscut");
		user.setAccount("zjcscut-10086");
		assertEquals("zjcscut-10086", matcher.matchAndReturnLockKeyByTargetObjectAndKeyName(user, "account"));
	}

}