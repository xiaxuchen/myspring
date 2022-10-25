package org.originit.hand.factory;

import cn.hutool.core.util.StrUtil;
import org.originit.hand.exception.BeansException;
import org.originit.hand.factory.support.*;
import org.originit.hand.factory.support.impl.CglibInstantiationStrategy;
import org.originit.hand.factory.support.impl.CglibReferenceDelegateCreator;
import org.originit.hand.util.OrderedUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xxc
 */
public abstract class AbstractAutowireBeanFactory extends AbstractBeanFactory implements ConfigurableListableBeanFactory{

    protected InstantiationStrategy instantiationStrategy = new CglibInstantiationStrategy();

    protected ReferenceDelegateCreator referenceDelegateCreator = new CglibReferenceDelegateCreator();

    @Override
    protected <T> T createBean(String beanName, BeanDefinition beanDefinition) {
        Constructor<?> destCtor = null;
        int paramCount = beanDefinition.getConstructArgs() == null?0 : beanDefinition.getConstructArgs().getArgs().size();
        List<Object> contructArgs = beanDefinition.getConstructArgs() == null?new ArrayList<>():beanDefinition.getConstructArgs().getArgs();
        Object[] args = new Object[paramCount];
        for (Constructor<?> declaredConstructor : beanDefinition.getBaseClass().getDeclaredConstructors()) {
            if (declaredConstructor.getParameterCount() == paramCount) {
                // 无参构造方法
                if (paramCount == 0) {
                    destCtor = declaredConstructor;
                    break;
                }
                // 匹配
                boolean matchFail = false;
                for (int i = 0; i < declaredConstructor.getParameters().length; i++) {

                    if (contructArgs.get(i) instanceof BeanReference) {
                        final String referName = ((BeanReference) contructArgs.get(i)).getReferName();
                        final Class<?> baseClass = getBeanDefinition(referName).getBaseClass();
                        Object o = referenceDelegateCreator.getDelegate(referName,this,baseClass);
                        args[i] = o;

                        if (!declaredConstructor.getParameterTypes()[i].isAssignableFrom(baseClass)) {
                            matchFail = true;
                            break;
                        }
                    } else {
                        if (!declaredConstructor.getParameterTypes()[i].isAssignableFrom(contructArgs.get(i).getClass())) {
                            matchFail = true;
                            break;
                        }
                        args[i] = contructArgs.get(i);
                    }
                }
                if (!matchFail) {
                    destCtor = declaredConstructor;
                }
            }
        }
        if (destCtor == null) {
            throw new BeansException("No matches bean constructor for bean named " + beanName);
        }
        Object o = instantiationStrategy.instantial(beanName,beanDefinition,destCtor,args);
        applyPropertyValues(beanName,o,beanDefinition);
        o = initializeBean(beanName,o,beanDefinition);
        return (T) o;
    }

    /**
     * 填充属性
     * @param beanName bean名称
     * @param bean 实例
     * @param definition bean定义
     */
    protected void applyPropertyValues(String beanName, Object bean,BeanDefinition definition) {
        final PropertyValues propertyValues = definition.getPropertyValues();
        if (propertyValues == null) {
            return;
        }
        for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
            try {
                Field field = definition.getBaseClass().getDeclaredField(propertyValue.getName());
                field.setAccessible(true);
                if (propertyValue.getValue() instanceof BeanReference) {
                    final BeanReference reference = (BeanReference) propertyValue.getValue();
                    field.set(bean,getBean(reference.getReferName()));
                } else {
                    field.set(bean,propertyValue.getValue());
                }
            }catch (NoSuchFieldException e){
                throw new BeansException("applyProperty fail with bean named " + beanName + ",no field called " + propertyValue.getName(), e);
            } catch (IllegalAccessException e) {
                throw new BeansException("applyProperty fail with bean named " + beanName + ",can't set property called" + propertyValue.getName(), e);

            }
        }

    }

    protected Object initializeBean(String beanName,Object bean,BeanDefinition definition) {
        List<Object> beanPostProcessors = getBeansOfType(BeanPostProcessor.class);
        beanPostProcessors = OrderedUtil.sort(beanPostProcessors);
        for (Object item : beanPostProcessors) {
            BeanPostProcessor beanPostProcessor = (BeanPostProcessor) item;
            Object proxy = beanPostProcessor.postProcessBeforeInitialization(bean, beanName);
            if (proxy == null) {
                break;
            } else {
                bean = proxy;
            }
        }
        invokeInitMethod(beanName,bean,definition);
        for (Object item : beanPostProcessors) {
            BeanPostProcessor beanPostProcessor = (BeanPostProcessor) item;
            Object proxy = beanPostProcessor.postProcessAfterInitialization(bean, beanName);
            if (proxy == null) {
                break;
            } else {
                bean = proxy;
            }
        }
        return bean;
    }

    protected void invokeInitMethod(String beanName, Object bean, BeanDefinition definition) {
        if (StrUtil.isNotBlank(definition.getInitMethod())) {
            try {
                final Method initMethod = definition.getBaseClass().getDeclaredMethod(definition.getInitMethod());
                initMethod.setAccessible(true);
                initMethod.invoke(bean);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new BeansException("invoke init method fail:" + e.getMessage(),e);
            }
        }
    }
}
