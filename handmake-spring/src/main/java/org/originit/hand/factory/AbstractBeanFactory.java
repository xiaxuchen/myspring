package org.originit.hand.factory;

import org.originit.hand.factory.support.BeanDefinition;
import org.originit.hand.factory.support.impl.DefaultSingletonBeanRegistry;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

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
     * 通过beanname获取bean定义
     * @param beanName bean名
     * @return bean
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName);

    /**
     * 创建Bean
     * @param beanName bean名称
     * @param beanDefinition bean定义
     * @return bean
     * @param <T>
     */
    protected abstract <T> T createBean(String beanName, BeanDefinition beanDefinition);
}
