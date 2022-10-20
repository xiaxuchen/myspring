package org.originit.hand.factory.support.impl;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import org.originit.hand.factory.support.BeanDefinition;
import org.originit.hand.factory.support.InstantiationStrategy;

import java.lang.reflect.Constructor;

/**
 * Cglib实例化策略
 */
public class CglibInstantiationStrategy implements InstantiationStrategy {
    @Override
    public <T> T instantial(String name, BeanDefinition beanDefinition, Constructor<T> ctor, Object... args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanDefinition.getBaseClass());
        enhancer.setCallback(new NoOp() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });
        if (null == ctor) return (T) enhancer.create();
        return (T) enhancer.create(ctor.getParameterTypes(), args);
    }
}
