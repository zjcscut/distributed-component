package org.throwable.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author throwable
 * @version v1.0
 * @function
 * @since 2017/5/17 18:10
 */
public final class ArrayUtils {

    public static  <T> List<T> arrayToList(T[] t) {
        if (null == t || t.length == 0) {
            return null;
        }
        List<T> list = new ArrayList<>(t.length);
        Collections.addAll(list, t);
        return list;
    }
}
