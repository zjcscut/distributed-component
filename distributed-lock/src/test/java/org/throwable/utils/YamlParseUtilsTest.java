package org.throwable.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author throwable
 * @version v1.0
 * @function
 * @since 2017/5/18 17:37
 */
public class YamlParseUtilsTest {

    @Test
    public void parse() throws Exception {
        CustomerConfiguration customerConfiguration = YamlParseUtils.parse("test.yaml", CustomerConfiguration.class);
        System.out.println(customerConfiguration.getCustomer().toString());
    }

}