package org.originit.hand.factory.support;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import org.originit.hand.factory.ConfigurableBeanFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

/**
 * Bean定义
 * @author xxc
 */
public class BeanDefinition {

    public static final String SCOPE_SINGLETON = ConfigurableBeanFactory.SCOPE_SINGLETON;
    public static final String SCOPE_PROTOTYPE = ConfigurableBeanFactory.SCOPE_PROTOTYPE;

    public static final Set<String> SCOPE_LIST = CollectionUtil.set(false,SCOPE_SINGLETON,SCOPE_PROTOTYPE);
    public BeanDefinition() {
    }

    public BeanDefinition(Class<?> baseClass, String beanName) {
        this.baseClass = baseClass;
        this.beanName = beanName;
    }

    private Class<?> baseClass;

    private String beanName;

    private String scope;

    private String initMethod;

    private String destoryMethod;

    public String getDestoryMethod() {
        return destoryMethod;
    }

    public void setDestoryMethod(String destoryMethod) {
        this.destoryMethod = destoryMethod;
    }

    public String getInitMethod() {
        return initMethod;
    }

    public void setInitMethod(String initMethod) {
        this.initMethod = initMethod;
    }

    private PropertyValues propertyValues = new PropertyValues();

    private ConstructArgs constructArgs;

    public boolean isSinglton() {
        return SCOPE_SINGLETON.equalsIgnoreCase(scope);
    }

    public boolean isPrototype() {
        return SCOPE_PROTOTYPE.equalsIgnoreCase(scope);
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        if (StrUtil.isBlank(scope)) {
            scope = SCOPE_SINGLETON;
        }
        Assert.isTrue(SCOPE_LIST.contains(scope.toLowerCase()),"scope must in set of " + SCOPE_LIST);
        this.scope = scope;
    }

    public ConstructArgs getConstructArgs() {
        return constructArgs;
    }

    public void setConstructArgs(ConstructArgs constructArgs) {
        this.constructArgs = constructArgs;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    public Class<?> getBaseClass() {
        return baseClass;
    }

    public void setBaseClass(Class<?> baseClass) {
        this.baseClass = baseClass;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
