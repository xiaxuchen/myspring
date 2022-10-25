package org.originit.hand.util;

import org.originit.hand.helper.annotation.Order;

import java.util.List;

public class OrderedUtil {

    /**
     * 对实现了Ordered接口或者注解了Order注解的类排序
     */
    public static <T> List<T> sort(List<T> list) {
        list.sort((o1, o2) -> {
            final boolean o1Ordered = o1.getClass().isAnnotationPresent(Order.class);
            final boolean o2Ordered = o2.getClass().isAnnotationPresent(Order.class);
            Integer o1Order = 0, o2Order = 0;
            if (o1Ordered) {
                o1Order = o1.getClass().getAnnotation(Order.class).value();
            }
            if (o2Ordered) {
                o2Order = o2.getClass().getAnnotation(Order.class).value();
            }
            return -o1Order.compareTo(o2Order);
        });
        return list;
    }

}
