package org.originit.hand.factory.support.impl;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.originit.hand.factory.BeanFactory;
import org.originit.hand.factory.support.ReferenceDelegateCreator;

import java.lang.reflect.Method;

/**
 * 通过Cglib代理bean
 */
public class CglibReferenceDelegateCreator implements ReferenceDelegateCreator {
    @Override
    public <T> T getDelegate(String beanName, BeanFactory beanFactory, Class<T> superClass) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(superClass);
        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
            final Object bean = beanFactory.getBean(beanName);
            return methodProxy.invoke(bean,objects);
        });
        return (T) enhancer.create();
    }
}
