package org.throwable.lock.utils;

import jodd.util.ArraysUtil;

/**
 * @author throwable
 * @version v1.0
 * @function
 * @since 2017/5/18 18:49
 */
public final class ClassUtils {

    private static final Class<?>[] primitives = {
            Integer.class, Float.class, Double.class, Long.class,
            Short.class, Byte.class, Boolean.class, Character.class, String.class,
            int.class, double.class, long.class, short.class, byte.class, boolean.class, char.class, float.class
    };

    public static boolean isPrimitive(Class<?> target) {
        return ArraysUtil.contains(primitives, target);
    }

}
