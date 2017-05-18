package org.throwable.lock.support;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.throwable.lock.configuration.DistributedLockProperties;
import org.throwable.lock.configuration.ZookeeperClientConfiguration;
import org.throwable.lock.configuration.ZookeeperClientProperties;
import org.throwable.lock.exception.LockException;
import org.throwable.utils.YamlParseUtils;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/5/17 23:00
 */
@Slf4j
@Component
public class ZookeeperDistributedLockFactory implements DistributedLockFactory, InitializingBean, DisposableBean {

    private static CuratorFramework client;

    private String baseLockPath;

    @Autowired
    private DistributedLockProperties distributedLockProperties;

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            ZookeeperClientConfiguration clientConfiguration
                    = YamlParseUtils.parse(distributedLockProperties.getZookeeperConfigurationLocation()
                    , ZookeeperClientConfiguration.class);
            ZookeeperClientProperties clientProperties = clientConfiguration.getZookeeperClientProperties();
            Assert.notNull(clientProperties, "Zookeeper client properties must not be null!!!");
            this.baseLockPath = clientProperties.getBaseLockPath();
            Assert.hasText(baseLockPath, "distributed lock baseLockPath must not be blank!!!");
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(clientProperties.getBaseSleepTimeMs(), clientProperties.getMaxRetries());
            if (null == client) {
                client = CuratorFrameworkFactory.newClient(
                        clientProperties.getConnectString(),
                        clientProperties.getSessionTimeoutMs(),
                        clientProperties.getConnectionTimeoutMs(),
                        retryPolicy);
            }
            client.start();
        }catch (Exception e){
            log.warn("Initialize zookeeper client failed!!!!Zookeeper client yaml preperties file could not be found.",e);
        }
    }

    @Override
    public void destroy() throws Exception {
        if (null != client) {
            client.close();
        }
    }

    @Override
    public DistributedLock createDistributedLockByPath(String lockPath) {
        String targetPath = baseLockPath + "/" + lockPath;
        try {
            return new ZookeeperDistributedLock(new ZookeeperInterProcessMutex(client, targetPath));
        } catch (Exception e) {
            log.error("create zookeeper interProcessMutex failed,target path:" + targetPath, e);
            throw new LockException(e);
        }
    }

    public ZookeeperInterProcessMutex createZookeeperInterProcessMutex(String lockPath) {
        String targetPath = baseLockPath + "/" + lockPath;
        return new ZookeeperInterProcessMutex(client, targetPath);
    }
}
