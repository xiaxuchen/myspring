package org.originit.hand.bean.aware;

import org.originit.hand.factory.BeanFactory;
import org.originit.hand.factory.support.BeanPostProcessor;

public class BeanFactoryPostProcessor implements BeanPostProcessor {

    BeanFactory beanFactory;

    public BeanFactoryPostProcessor(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if (bean instanceof  BeanFactoryAware) {
            ((BeanFactoryAware) bean).setBeanFactory(beanFactory);
        }
        return bean;
    }
}
