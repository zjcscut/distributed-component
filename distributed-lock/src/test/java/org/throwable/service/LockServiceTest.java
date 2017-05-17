package org.throwable.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.throwable.Application;
import org.throwable.lock.annotation.EnableDistributedLock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author throwable
 * @version v1.0
 * @function
 * @since 2017/5/17 16:46
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@EnableDistributedLock
public class LockServiceTest {

	@Autowired
	private LockService lockService;

	@Test
	public void process() throws Exception {
		List<Thread> threads = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			final int j = i;
			threads.add(new Thread(new Runnable() {
				@Override
				public void run() {
					lockService.process("account-" + j);
				}
			}));
		}
		threads.forEach(Thread::start);

		Thread.sleep(Integer.MAX_VALUE);
	}

}