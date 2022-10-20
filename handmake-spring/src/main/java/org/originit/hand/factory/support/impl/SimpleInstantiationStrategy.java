package org.originit.hand.factory.support.impl;

import org.originit.hand.exception.BeansException;
import org.originit.hand.factory.support.BeanDefinition;
import org.originit.hand.factory.support.InstantiationStrategy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SimpleInstantiationStrategy implements InstantiationStrategy {
    @Override
    public <T> T instantial(String name, BeanDefinition beanDefinition, Constructor<T> ctor, Object... args) {
        try {
            return ctor.newInstance(args);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new BeansException("create bean error with " + name,e);
        }
    }
}
