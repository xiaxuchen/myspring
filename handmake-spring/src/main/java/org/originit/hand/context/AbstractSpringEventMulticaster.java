package org.originit.hand.context;

import org.originit.hand.event.SpringEvent;
import org.originit.hand.event.SpringEventListener;
import org.originit.hand.event.SpringEventMulticaster;
import org.originit.hand.exception.BeansException;
import org.originit.hand.factory.support.impl.SimpleInstantiationStrategy;
import org.originit.hand.util.ClassUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractSpringEventMulticaster implements SpringEventMulticaster {

    Set<SpringEventListener> listeners = new HashSet<>();

    @Override
    public void addListener(SpringEventListener eventListener) {
        listeners.add(eventListener);
    }

    @Override
    public void removeListener(SpringEventListener eventListener) {
        listeners.remove(eventListener);
    }

    protected List<SpringEventListener> getListeners(SpringEvent event) {
        List<SpringEventListener> list = new ArrayList<>();
        for (SpringEventListener listener : listeners) {
            if (supportsEvent(listener,event)) {
                list.add(listener);
            }
        }
        return list;
    }

    @Override
    public void multicastEvent(SpringEvent event) {
        for (SpringEventListener listener : getListeners(event)) {
            listener.onEvent(event);
        }
    }

    private boolean supportsEvent(SpringEventListener listener, SpringEvent event) {
        Class<? extends SpringEventListener> listenerClass = listener.getClass();
        // 按照 CglibSubclassingInstantiationStrategy、
        // SimpleInstantiationStrategy 不同的实例化类型，需要判断后获取目标 class
        Class<?> targetClass = ClassUtils.isCglibProxyClass(listenerClass) ? listenerClass.getSuperclass()
                : listenerClass;
        Type genericInterface = targetClass.getGenericInterfaces()[0];
        Type actualTypeArgument = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
        String className = actualTypeArgument.getTypeName();
        Class<?> eventClass;
        try {
            eventClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new BeansException("wrong event class name: " + className);
        }
        // 判定此 eventClassName 对象所表示的类或接口与指定的 event.getClass() 参数所表
        // 示的类或接口是否相同，或是否是其超类或超接口。
        // isAssignableFrom 是用来判断子类和父类的关系的，或者接口的实现类和接口的关系的，
        // 默认所有的类的终极父类都是 Object。如果 A.isAssignableFrom(B)结果是 true，证明 B 可以转换成
        // 为 A,也就是 A 可以由 B 转换而来。
        return eventClass.isAssignableFrom(event.getClass());
    }
}
