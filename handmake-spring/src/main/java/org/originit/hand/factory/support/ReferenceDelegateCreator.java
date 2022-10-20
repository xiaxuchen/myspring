package org.originit.hand.factory.support;

import org.originit.hand.factory.BeanFactory;

/**
 * 获取引用的Bean代理
 * @author xxc
 */
public interface ReferenceDelegateCreator {

    /**
     * 获取对应bean的代理
     * @param beanName bean名
     * @param superClass bean的类型
     * @param beanFactory bean工厂
     * @return bean的多苦
     * @param <T>
     */
    <T> T getDelegate(String beanName, BeanFactory beanFactory, Class<T> superClass);
}
