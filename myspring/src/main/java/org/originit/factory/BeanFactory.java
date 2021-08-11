package org.originit.factory;

public interface BeanFactory {

    Object getBean(Class type);

    Object getBean(String name);

}
