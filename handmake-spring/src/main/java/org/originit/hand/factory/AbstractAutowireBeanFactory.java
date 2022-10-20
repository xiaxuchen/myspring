package org.originit.hand.factory;

import org.originit.hand.exception.BeansException;
import org.originit.hand.factory.support.*;
import org.originit.hand.factory.support.impl.CglibInstantiationStrategy;
import org.originit.hand.factory.support.impl.CglibReferenceDelegateCreator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractAutowireBeanFactory extends AbstractBeanFactory{

    protected InstantiationStrategy instantiationStrategy = new CglibInstantiationStrategy();

    protected ReferenceDelegateCreator referenceDelegateCreator = new CglibReferenceDelegateCreator();

    @Override
    protected <T> T createBean(String beanName, BeanDefinition beanDefinition) {
        Constructor<?> destCtor = null;
        int paramCount = beanDefinition.getConstructProperties() == null?0 : beanDefinition.getConstructProperties().getPropertyValues().size();
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
                Map<String,Object> fillParams = new HashMap<>(paramCount);
                for (PropertyValue propertyValue : beanDefinition.getPropertyValues().getPropertyValues()) {
                    fillParams.put(propertyValue.getName(),propertyValue.getName());
                }
                int i = 0;
                for (Parameter parameter : declaredConstructor.getParameters()) {
                    if (!fillParams.containsKey(parameter.getName())) {
                        matchFail = true;
                        break;
                    }
                    Object o = fillParams.get(parameter.getName());
                    // 如果是BeanReference，那么就使用代理
                    if (o instanceof BeanReference) {
                        final String referName = ((BeanReference) o).getReferName();
                        final Class<?> baseClass = getBeanDefinition(referName).getBaseClass();
                        o = referenceDelegateCreator.getDelegate(referName,this,baseClass);
                    }
                    if (!parameter.getType().isAssignableFrom(o.getClass())) {
                        matchFail = true;
                        break;
                    }
                    args[i++] = o;
                }
                if (!matchFail) {
                    destCtor = declaredConstructor;
                }
            }
        }
        if (destCtor == null) {
            throw new BeansException("No matches bean constructor for bean named" + beanName);
        }
        Object o = instantiationStrategy.instantial(beanName,beanDefinition,destCtor,args);
        applyPropertyValues(beanName,o,beanDefinition);
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

    };
}
