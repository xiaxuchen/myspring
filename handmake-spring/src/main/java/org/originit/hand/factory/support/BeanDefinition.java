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

    private PropertyValues propertyValues;

    private PropertyValues constructProperties;

    public PropertyValues getConstructProperties() {
        return constructProperties;
    }

    public void setConstructProperties(PropertyValues constructProperties) {
        this.constructProperties = constructProperties;
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
