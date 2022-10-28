package org.originit.hand.factory.support;

import org.originit.hand.factory.BeanFactory;
import org.originit.hand.factory.ConfigurableBeanFactory;

/**
 * @author xxc
 */
public interface BeanFactoryPostProcessor {

    void postProcessBeanFactory(ConfigurableBeanFactory beanFactory);
}
