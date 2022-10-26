package org.originit.hand.context;

import org.originit.hand.bean.aware.ApplicationContextAware;
import org.originit.hand.bean.aware.ApplicationContextBeanPostProcessor;
import org.originit.hand.bean.aware.BeanFactoryPostProcessor;
import org.originit.hand.bean.aware.BeanNamePostProcessor;
import org.originit.hand.factory.impl.DefaultListableBeanFactory;

public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext{

    private DefaultListableBeanFactory beanFactory;

    @Override
    protected void refreshBeanFactory() {
        if (beanFactory == null) {
            beanFactory = new DefaultListableBeanFactory();
        }
        loadBeanDefinitions(beanFactory);
        beanFactory.registerObject(ApplicationContextBeanPostProcessor.class.getName(),new ApplicationContextBeanPostProcessor(this));
        beanFactory.registerObject(BeanFactoryPostProcessor.class.getName(),new BeanFactoryPostProcessor(beanFactory));
        beanFactory.registerObject(BeanNamePostProcessor.class.getName(),new BeanNamePostProcessor());
    }

    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);

    @Override
    public DefaultListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    public void registerShutdownHooks() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }
}
