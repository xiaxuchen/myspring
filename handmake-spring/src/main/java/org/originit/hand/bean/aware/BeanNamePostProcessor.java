package org.originit.hand.bean.aware;

import org.originit.hand.factory.support.BeanPostProcessor;

public class BeanNamePostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if (bean instanceof BeanNameAware) {
            ((BeanNameAware) bean).setBeanName(beanName);
        }
        return bean;
    }
}
