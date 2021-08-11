package org.originit.factory;

import java.util.HashMap;
import java.util.Map;

public class AbstractBeanFactory implements BeanFactory {

    protected Map<String,Object> beanByName = new HashMap<>();

    protected Map<Class,Object> beanByType = new HashMap<>();


    @Override
    public Object getBean(Class type) {
        return beanByType.get(type);
    }

    @Override
    public Object getBean(String name) {
        return beanByName.get(name);
    }

}
