package org.originit.hand.context;

import cn.hutool.core.util.StrUtil;
import org.originit.hand.event.SpringEvent;
import org.originit.hand.event.SpringEventListener;
import org.originit.hand.event.SpringEventMulticaster;
import org.originit.hand.event.SpringEventPublisher;
import org.originit.hand.event.impl.BeanFactoryCloseEvent;
import org.originit.hand.event.impl.SimpleSpringEventMulticaster;
import org.originit.hand.event.impl.SpringRefreshEvent;
import org.originit.hand.factory.impl.DefaultListableBeanFactory;
import org.originit.hand.factory.support.BeanDefinition;
import org.originit.hand.factory.support.BeanFactoryPostProcessor;
import org.originit.hand.factory.support.BeanPostProcessor;
import org.originit.hand.io.impl.DefaultResourceLoader;
import org.originit.hand.util.OrderedUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext,SpringEventPublisher{

    public static final String SPRING_EVENT_MULTICASTER = "SpringEventMulticaster";
    private List<Class<BeanPostProcessor>> beanPostProcessors = new ArrayList<>();

    private List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();

    private boolean isClose = true;

    public void registerBeanPostProcessor(Class<BeanPostProcessor> beanPostProcessorClass) {
        beanPostProcessors.add(beanPostProcessorClass);
    }

    public void registerBeanFactoryPostProcessor(BeanFactoryPostProcessor factoryPostProcessor) {
        beanFactoryPostProcessors.add(factoryPostProcessor);
    }

    private SpringEventMulticaster multicaster;

    @Override
    public synchronized void refresh() {
        registerShutdownHooks();

        refreshBeanFactory();

        invokeBeanFactoryPostProcessor();

        registerBeanPostProcessor();

        getBeanFactory().preInstantial();

        initEventMulticaster();

        finishRefresh();

    }

    private void finishRefresh() {
        publish(new SpringRefreshEvent());
        this.isClose = false;
    }

    @Override
    public void publish(SpringEvent event) {
        multicaster.multicastEvent(event);
    }

    private void initEventMulticaster() {
        multicaster = new SimpleSpringEventMulticaster();
        getBeanFactory().registerObject(SPRING_EVENT_MULTICASTER,multicaster);
        for (Object o : getBeanFactory().getBeansOfType(SpringEventListener.class)) {
            multicaster.addListener((SpringEventListener) o);
        }
    }


    protected void registerBeanPostProcessor() {
        for (Class<BeanPostProcessor> beanPostProcessor : beanPostProcessors) {
            final String beanName = StrUtil.lowerFirst(beanPostProcessor.getName());
            getBeanFactory().registerBeanDefinition(beanName, new BeanDefinition(beanPostProcessor,beanName));
        }
        for (String beanDefinitionName : getBeanFactory().getBeanDefinitionNames()) {
            if (BeanPostProcessor.class.isAssignableFrom(getBeanFactory().getBeanDefinition(beanDefinitionName).getBaseClass())) {
                getBeanFactory().getBean(beanDefinitionName);
                System.out.println(beanDefinitionName);
            }
        }

    }

    private void invokeBeanFactoryPostProcessor() {
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : OrderedUtil.sort(beanFactoryPostProcessors)) {
            beanFactoryPostProcessor.postProcessBeanFactory(getBeanFactory());
        }
    }

    protected abstract void refreshBeanFactory();

    protected abstract DefaultListableBeanFactory getBeanFactory();

    @Override
    public void close() {
        if (!isClose) {
            publish(new BeanFactoryCloseEvent());
            getBeanFactory().destorySingltons();
            this.isClose = true;
        }
    }

    @Override
    public void registerShutdownHooks() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }
}
