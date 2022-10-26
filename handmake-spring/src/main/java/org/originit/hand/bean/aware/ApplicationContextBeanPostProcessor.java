package org.originit.hand.bean.aware;

import org.originit.hand.context.ApplicationContext;
import org.originit.hand.factory.support.BeanPostProcessor;
import org.originit.hand.helper.annotation.Order;

/**
 * @author xxc
 */
@Order(Integer.MAX_VALUE)
public class ApplicationContextBeanPostProcessor implements BeanPostProcessor {

    ApplicationContext applicationContext;

    public ApplicationContextBeanPostProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }
}
