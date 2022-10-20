package org.originit.hand.factory.support.impl;

import org.originit.hand.factory.support.SingletonBeanRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    Map<String,Object> singletons = new ConcurrentHashMap<>();

    /**
     * 注册单例类
     * @param beanName 单例名称
     * @param bean 单例bean
     */
    protected void addSingleton(String beanName, Object bean) {
        singletons.put(beanName, bean);
    }

    @Override
    public <T> T getSingleton(String beanName) {
        return (T) singletons.get(beanName);
    }
}
