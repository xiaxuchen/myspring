package org.originit.hand.factory.impl;

import org.originit.hand.exception.BeansException;
import org.originit.hand.factory.AbstractAutowireBeanFactory;
import org.originit.hand.factory.support.BeanDefinition;
import org.originit.hand.factory.support.BeanDefinitionRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultListableBeanFactory extends AbstractAutowireBeanFactory implements BeanDefinitionRegistry {

    Map<String,BeanDefinition> definitionMap = new ConcurrentHashMap<>();

    @Override
    protected BeanDefinition getBeanDefinition(String beanName) {
        if (!definitionMap.containsKey(beanName)) {
            throw new BeansException("No beanDefinition named " + beanName + "!");
        }
        return definitionMap.get(beanName);
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        definitionMap.put(beanName, beanDefinition);
    }


}
