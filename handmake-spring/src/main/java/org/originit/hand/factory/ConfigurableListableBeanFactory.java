package org.originit.hand.factory;

import org.originit.hand.factory.support.BeanDefinition;

public interface ConfigurableListableBeanFactory extends HierarchicalBeanFactory,ConfigurableBeanFactory,ListableBeanFactory {


    /**
     * 通过beanname获取bean定义
     * @param beanName bean名
     * @return bean
     */
    BeanDefinition getBeanDefinition(String beanName);
}
