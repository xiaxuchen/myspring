package org.originit.hand.factory;

import org.originit.hand.exception.BeansException;
import org.originit.hand.factory.support.BeanDefinition;
import org.originit.hand.factory.support.ObjectRegisterBeanFactory;
import org.originit.hand.factory.support.impl.DefaultSingletonBeanRegistry;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xxc
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ObjectRegisterBeanFactory,ConfigurableListableBeanFactory {

    protected Map<String,Object> registerObjectMap = new ConcurrentHashMap<>();

    @Override
    public <T> T getBean(String name) {
        final BeanDefinition beanDefinition = getBeanDefinition(name);
        final Object singleton = getSingleton(name);
        if (singleton == null) {
            final Object bean = createBean(name, beanDefinition);
            addSingleton(name, bean);
            return (T) bean;
        }
        return (T) singleton;
    }


    /**
     * 创建Bean
     * @param beanName bean名称
     * @param beanDefinition bean定义
     * @return bean
     * @param <T>
     */
    protected abstract <T> T createBean(String beanName, BeanDefinition beanDefinition);

    @Override
    public void registerObject(String beanName, Object bean) {
        if (containsBeanDefiniton(beanName)) {
            throw new BeansException("exist bean named " + beanName);
        }
        registerObjectMap.put(beanName,bean);
    }

    @Override
    public <T> T getSingleton(String beanName) {
        if (super.getSingleton(beanName) == null) {
            return (T) registerObjectMap.get(beanName);
        }
        return super.getSingleton(beanName);
    }


}
