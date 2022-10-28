package org.originit.hand.util;

import org.originit.hand.event.SpringEvent;
import org.originit.hand.event.SpringEventListener;

public class ClassUtils {
    public static boolean isCglibProxyClass(Class<? extends SpringEventListener> clazz) {
        return clazz != null && clazz.getName().indexOf("$$") != -1;
    }
}
