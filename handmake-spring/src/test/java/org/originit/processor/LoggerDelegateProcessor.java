package org.originit.processor;

import org.originit.hand.factory.support.BeanPostProcessor;
import org.originit.relate.service.HelloService;
import org.originit.relate.service.HelloServiceDelegate;

public class LoggerDelegateProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (bean instanceof HelloService) {
            return new HelloServiceDelegate((HelloService) bean);
        }
        return bean;
    }

    void des(){
        System.out.println("des");
    }
}
