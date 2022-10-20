package org.originit.hand.bean;

/**
 * Bean定义
 * @author xxc
 */
public class BeanDefinition {
    public BeanDefinition(Object bean) {
        this.bean = bean;
    }

    private Object bean;

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }
}
