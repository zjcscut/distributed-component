package org.throwable.lock.support;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/5/17 23:00
 */
@Component
public class ZookeeperDistributedLockFactory implements DistributedLockFactory,InitializingBean{

	private RetryPolicy retryPolicy = new ExponentialBackoffRetry(5000, 3);

	private static CuratorFramework client;

	private static final String baseLockPath = "/zk/lock";

	@Override
	public void afterPropertiesSet() throws Exception {
		TestingServer server = new TestingServer();
		if (null == client) {
			client = CuratorFrameworkFactory.newClient(
					server.getConnectString(),
					5000,
					3000,
					retryPolicy);
		}
		client.start();
	}

	@Override
	public DistributedLock createDistributedLockByPath(String lockPath) {
		try {
			return new ZookeeperDistributedLock(new ZookeeperInterProcessMutex(client, baseLockPath + "/" + lockPath));
		} catch (Exception e) {
           e.printStackTrace();
		}
		return null;
	}
}
