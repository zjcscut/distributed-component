package org.throwable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.throwable.lock.annotation.EnableDistributedLock;

/**
 * @author throwable
 * @version v1.0
 * @function
 * @since 2017/5/16 18:47
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@EnableDistributedLock
public class DistributedLockTest {


	@Test
    public void testCurator()throws Exception{

    }


    @Test
    public void testAutowriedAspectJProxyFactory()throws Exception{
    }

}