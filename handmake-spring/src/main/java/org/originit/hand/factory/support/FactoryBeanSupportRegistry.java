package org.originit.hand.factory.support;

import org.originit.hand.exception.BeansException;
import org.originit.hand.factory.support.impl.DefaultSingletonBeanRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class FactoryBeanSupportRegistry extends DefaultSingletonBeanRegistry {

    /**
     * Cache of singleton objects created by FactoryBeans: FactoryBean name -
     -> object
     */

    protected Object getObjectFromFactoryBean(FactoryBean factory, String beanName)
    {
        if (factory.isSinglton()) {
            Object object = getSingleton(beanName);
            if (object == null) {
                object = doGetObjectFromFactoryBean(factory, beanName);
                addSingleton(beanName,object);
            }
            return (object != NULL_OBJECT ? object : null);
        } else {
            return doGetObjectFromFactoryBean(factory, beanName);
        }
    }
    private Object doGetObjectFromFactoryBean(final FactoryBean factory, final String beanName){
        try {
            synchronized (factory) {
                return factory.getObject();
            }
        } catch (Exception e) {
            throw new BeansException("FactoryBean threw exception on object[" + beanName + "] creation", e);
        }
    }
}
