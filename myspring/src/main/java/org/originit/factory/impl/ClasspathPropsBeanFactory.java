package org.originit.factory.impl;

import org.originit.exception.FactoryException;
import org.originit.factory.AbstractBeanFactory;
import org.originit.factory.BeanFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class ClasspathPropsBeanFactory extends AbstractBeanFactory {

    private static final String CLASS_PATH_PREFIX = "classpath:";

    public ClasspathPropsBeanFactory (String path) {
        Properties props = getProps(path);
        init(props);
        performInit();
    }

    private void performInit() {
        List<Object> objList = beanByName.entrySet().stream().map(Map.Entry::getValue).distinct().collect(Collectors.toList());
        for (Object o : objList) {
            try {
                Method initFields = o.getClass().getDeclaredMethod("initFields",BeanFactory.class);
                initFields.invoke(o,this);
            } catch (NoSuchMethodException e) {

            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new FactoryException("无initFields方法访问权限",e);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                throw new FactoryException("initFields方法访问失败",e);
            }
        }
    }

    private void init(Properties props) {
        Enumeration<Object> keys = props.keys();
        while(keys.hasMoreElements()) {
            String key = keys.nextElement().toString();
            String className = props.getProperty(key);
            createBean(key,className);
        }
    }

    private Properties getProps(String path) {
        if (path.toLowerCase().startsWith(CLASS_PATH_PREFIX)) {
            path = path.substring(CLASS_PATH_PREFIX.length());
        }
        Properties properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
            throw new FactoryException("配置文件加载失败",e);
        }
        return properties;

    }

    protected void createBean(String key,String className) {
        try {
            if (beanByName.get(key) != null) {
                throw new FactoryException("存在相同名称的bean" + key);
            }
            Class<?> clazz = Class.forName(className);
            if (beanByType.get(clazz) != null) {
                throw new FactoryException("存在相同类型的bean" + clazz);
            }
            Object bean = clazz.newInstance();
            beanByName.put(key,bean);
            beanByType.put(clazz,bean);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new FactoryException("该class找不到:"+className,e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new FactoryException("该class无参构造方法无法访问:"+className,e);
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new FactoryException("该class实例化失败:"+className,e);
        }
    }


}
