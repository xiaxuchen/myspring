package org.originit.hand.context;

import org.originit.hand.factory.impl.DefaultListableBeanFactory;

public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext{

    private DefaultListableBeanFactory beanFactory;

    @Override
    protected void refreshBeanFactory() {
        if (beanFactory == null) {
            beanFactory = new DefaultListableBeanFactory();
        }
        loadBeanDefinitions(beanFactory);
    }

    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);

    @Override
    public DefaultListableBeanFactory getBeanFactory() {
        return beanFactory;
    }
}
