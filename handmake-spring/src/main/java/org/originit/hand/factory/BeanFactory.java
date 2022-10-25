package org.originit.hand.factory;


import org.originit.hand.exception.BeansException;

/**
 * Bean工厂
 * @author xxc
 */
public interface BeanFactory {

    /**
     * 创建bean时传入参数
     * @param name bean名称
     * @param args bean创建参数
     * @return bean
     * @param <T>
     */
    <T> T getBean(String name);

    void preInstantial() throws BeansException;

}
