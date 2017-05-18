package org.throwable.lock.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.throwable.Application;
import org.throwable.lock.annotation.EnableDistributedLock;

import static org.junit.Assert.*;

/**
 * @author throwable
 * @version v1.0
 * @function
 * @since 2017/5/18 17:55
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@EnableDistributedLock
public class DistributedLockPropertiesTest {

    @Test
    public void test()throws Exception{
        System.out.println("test");
    }

}