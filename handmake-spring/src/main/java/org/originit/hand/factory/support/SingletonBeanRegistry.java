package org.originit.hand.factory.support;


public interface SingletonBeanRegistry {

    /**
     * 获取单例
     * @param beanName 单例名称
     * @return 单例类
     * @param <T> 类型
     */
    <T> T getSingleton(String beanName);

    /**
     * 销毁单例
     */
    void destorySingltons();

}
