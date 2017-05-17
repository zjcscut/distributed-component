package org.throwable;

import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author throwable
 * @version v1.0
 * @function
 * @since 2017/5/16 18:47
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class DistributedLockTest {

    public void testCurator()throws Exception{
        InterProcessMutex mutex = new InterProcessMutex(null,null);
        InterProcessReadWriteLock writeLock = new InterProcessReadWriteLock(null,null);
    }


    @Test
    public void testAutowriedAspectJProxyFactory()throws Exception{
    }

}