package org.originit.hand.factory;

import org.originit.hand.exception.BeansException;
import org.originit.hand.factory.support.BeanDefinition;
import org.originit.hand.factory.support.FactoryBean;
import org.originit.hand.factory.support.FactoryBeanSupportRegistry;
import org.originit.hand.factory.support.ObjectRegisterBeanFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xxc
 */
public abstract class AbstractBeanFactory extends FactoryBeanSupportRegistry implements ObjectRegisterBeanFactory,ConfigurableListableBeanFactory {

    public static final String FACTORY_BEAN_SUFFIX = "#FactoryBean";

    protected Map<String,Object> registerObjectMap = new ConcurrentHashMap<>();

    @Override
    public <T> T getBean(String name) {
        final BeanDefinition beanDefinition = getBeanDefinition(name);
        if (FactoryBean.class.isAssignableFrom(beanDefinition.getBaseClass())) {
            return (T)doGetFactoryBean(name,beanDefinition);
        }
        if (beanDefinition.isSinglton()) {
            return doGetSinglton(name, beanDefinition);
        } else {
            return doGetPrototype(name,beanDefinition);
        }

    }

    private Object doGetFactoryBean(String name, BeanDefinition beanDefinition) {
        final Object bean = getSingleton(name);
        if (bean == null) {
            final String factoryBeanName = beanDefinition.getBeanName() + FACTORY_BEAN_SUFFIX;
            Object factoryBean = getSingleton(factoryBeanName);
            if (factoryBean == null) {
                factoryBean = createBean(factoryBeanName, beanDefinition);
                addSingleton(factoryBeanName,factoryBean);
            }
            return getObjectFromFactoryBean((FactoryBean) factoryBean,name);
        }
        return bean;
    }

    private <T> T doGetPrototype(String name, BeanDefinition beanDefinition) {
        synchronized (beanDefinition) {
            return createBean(name,beanDefinition);
        }
    }

    private <T> T doGetSinglton(String name, BeanDefinition beanDefinition) {
        final Object singleton = getSingleton(name);
        if (singleton == null) {
            final Object bean = createBean(name, beanDefinition);
            addSingleton(name, bean);
            return (T) bean;
        }
        return (T) singleton;
    }


    /**
     * 创建Bean
     * @param beanName bean名称
     * @param beanDefinition bean定义
     * @return bean
     * @param <T>
     */
    protected abstract <T> T createBean(String beanName, BeanDefinition beanDefinition);

    @Override
    public void registerObject(String beanName, Object bean) {
        if (containsBeanDefiniton(beanName)) {
            throw new BeansException("exist bean named " + beanName);
        }
        registerObjectMap.put(beanName,bean);
    }

    @Override
    public <T> T getSingleton(String beanName) {
        if (super.getSingleton(beanName) == null) {
            return (T) registerObjectMap.get(beanName);
        }
        return super.getSingleton(beanName);
    }


}
