package org.originit.hand.factory.support;

import org.originit.hand.factory.BeanFactory;

public interface ObjectRegisterBeanFactory extends BeanFactory {

    void registerObject(String beanName,Object bean);
}
