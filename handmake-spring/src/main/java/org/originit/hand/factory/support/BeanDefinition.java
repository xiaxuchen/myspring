package org.originit.hand.factory.support;

/**
 * Bean定义
 * @author xxc
 */
public class BeanDefinition {
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

    public String getInitMethod() {
        return initMethod;
    }

    public void setInitMethod(String initMethod) {
        this.initMethod = initMethod;
    }

    private PropertyValues propertyValues = new PropertyValues();

    private ConstructArgs constructArgs;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
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
