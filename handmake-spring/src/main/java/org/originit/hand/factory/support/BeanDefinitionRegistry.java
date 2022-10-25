package org.originit.hand.factory.support;

public interface BeanDefinitionRegistry {

    /**
     * 注册bean定义
     * @param beanName bean名称
     * @param beanDefinition bean定义
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    boolean containsBeanDefinition(String beanName);
}
