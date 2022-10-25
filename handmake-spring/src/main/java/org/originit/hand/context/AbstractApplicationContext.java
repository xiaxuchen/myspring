package org.originit.hand.context;

import cn.hutool.core.util.StrUtil;
import org.originit.hand.factory.impl.DefaultListableBeanFactory;
import org.originit.hand.factory.support.BeanDefinition;
import org.originit.hand.factory.support.BeanFactoryPostProcessor;
import org.originit.hand.factory.support.BeanPostProcessor;
import org.originit.hand.io.impl.DefaultResourceLoader;
import org.originit.hand.util.OrderedUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext{

    private List<Class<BeanPostProcessor>> beanPostProcessors = new ArrayList<>();

    private List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();


    public void registerBeanPostProcessor(Class<BeanPostProcessor> beanPostProcessorClass) {
        beanPostProcessors.add(beanPostProcessorClass);
    }

    public void registerBeanFactoryPostProcessor(BeanFactoryPostProcessor factoryPostProcessor) {
        beanFactoryPostProcessors.add(factoryPostProcessor);
    }

    @Override
    public void refresh() {
        registerShutdownHooks();

        refreshBeanFactory();

        invokeBeanFactoryPostProcessor();

        registerBeanPostProcessor();

        getBeanFactory().preInstantial();
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
        getBeanFactory().destorySingltons();
    }
}
