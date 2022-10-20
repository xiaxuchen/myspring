package org.originit.hand.factory.support;

import java.lang.reflect.Constructor;

/**
 * 实例化策略
 */
public interface InstantiationStrategy {

    <T> T  instantial(String name,BeanDefinition beanDefinition,Constructor<T> ctor,Object...args);
}
