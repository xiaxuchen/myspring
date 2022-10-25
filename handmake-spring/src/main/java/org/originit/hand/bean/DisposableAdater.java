package org.originit.hand.bean;

import cn.hutool.core.util.StrUtil;
import org.originit.hand.exception.BeansException;
import org.originit.hand.factory.support.BeanDefinition;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DisposableAdater implements DisposableBean {

    private final BeanDefinition definition;
    private final Object bean;

    public DisposableAdater(Object bean, BeanDefinition definition) {
        this.bean = bean;
        this.definition = definition;
    }

    @Override
    public void destory() {
        if (this.bean instanceof DisposableBean) {
            ((DisposableBean) this.bean).destory();
        }
        if (StrUtil.isNotBlank(definition.getDestoryMethod())) {
            try {
                final Method initMethod = definition.getBaseClass().getDeclaredMethod(definition.getDestoryMethod());
                initMethod.setAccessible(true);
                initMethod.invoke(bean);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new BeansException("invoke destory method fail:" + e.getMessage(),e);
            }
        }
    }
}
