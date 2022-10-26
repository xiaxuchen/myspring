package org.originit.hand.bean.aware;

import org.originit.hand.factory.BeanFactory;

public interface BeanFactoryAware extends Aware{

    void setBeanFactory(BeanFactory beanFactory);
}
