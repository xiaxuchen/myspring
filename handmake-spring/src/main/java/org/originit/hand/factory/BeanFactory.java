package org.originit.hand.factory;

import org.originit.hand.bean.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Bean工厂
 * @author xxc
 */
public class BeanFactory {

    protected Map<String, BeanDefinition> definitionMap = new ConcurrentHashMap<>();

    public void registeBeanDefinition(String name, BeanDefinition beanDefinition) {
        if (definitionMap.containsKey(name)) {
            throw new IllegalArgumentException("There are more than one bean called 【" + name + "】,please check your configuration！");
        }
        definitionMap.put(name,beanDefinition);
    }

    public Object getBean(String name) {
        if (!definitionMap.containsKey(name)) {
            return null;
        }
        return definitionMap.get(name).getBean();
    }
}
