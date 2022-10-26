package org.originit.hand.factory.impl;

import org.originit.hand.exception.BeansException;
import org.originit.hand.factory.AbstractAutowireBeanFactory;
import org.originit.hand.factory.support.BeanDefinition;
import org.originit.hand.factory.support.BeanDefinitionRegistry;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DefaultListableBeanFactory extends AbstractAutowireBeanFactory implements BeanDefinitionRegistry {

    Map<String,BeanDefinition> definitionMap = new ConcurrentHashMap<>();


    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        if (!definitionMap.containsKey(beanName)) {
            throw new BeansException("No beanDefinition named " + beanName + "!");
        }
        return definitionMap.get(beanName);
    }

    @Override
    public boolean containsBeanDefiniton(String beanName) {
        return definitionMap.containsKey(beanName);
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        definitionMap.put(beanName, beanDefinition);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return definitionMap.containsKey(beanName);
    }


    @Override
    public void preInstantial() throws BeansException {
        for (String key : definitionMap.keySet()) {
            final BeanDefinition beanDefinition = getBeanDefinition(key);
            if (SCOPE_SINGLETON.equals(beanDefinition.getScope())) {
                getBean(key);
            }
        }
    }


    @Override
    public List<Object> getBeansOfType(Class<?> clazz) {
        return Stream.concat(singletons.values().stream(),registerObjectMap.values().stream()).filter(v -> clazz.isAssignableFrom(v.getClass())).collect(Collectors.toList());
    }

    @Override
    public Set<String> getBeanDefinitionNames() {
        if (definitionMap == null) {
            return Collections.EMPTY_SET;
        }
        return definitionMap.keySet();
    }
}
