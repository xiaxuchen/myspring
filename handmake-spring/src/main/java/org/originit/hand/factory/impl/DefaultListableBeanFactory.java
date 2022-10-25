package org.originit.hand.factory.impl;

import org.originit.hand.exception.BeansException;
import org.originit.hand.factory.AbstractAutowireBeanFactory;
import org.originit.hand.factory.support.BeanDefinition;
import org.originit.hand.factory.support.BeanDefinitionRegistry;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class DefaultListableBeanFactory extends AbstractAutowireBeanFactory implements BeanDefinitionRegistry {

    Map<String,BeanDefinition> definitionMap = new ConcurrentHashMap<>();

    TypeTree typeTree = new TypeTree(Object.class);

    public static class TypeTree {
       private Class<?> type;

       private List<Object> instances = new ArrayList<>();
       private Map<Class<?>,TypeTree> children = new HashMap<>();

        public TypeTree(Class<?> type) {
            this.type = type;
        }

        public Class<?> getType() {
            return type;
        }

        public void setType(Class<?> type) {
            this.type = type;
        }

        public void addNode(Class<?> curType,Object bean) {
            if (curType == getType()) {
                this.instances.add(bean);
                return;
            }
            Stack<Class<?>> parentsClasses = new Stack<>();
            Class<?> tempType = curType;
            while ((tempType = tempType.getSuperclass()) != type) {
                parentsClasses.push(tempType);
            }
            TypeTree curParent = this;
            while (!parentsClasses.empty()) {
                final Class<?> pop = parentsClasses.pop();
                if (curParent.children.containsKey(pop)) {
                    curParent = curParent.children.get(pop);
                } else {
                    final TypeTree tp = new TypeTree(pop);
                    curParent.children.put(pop,tp);
                    curParent = tp;
                }
            }
            curParent.instances.add(bean);
        }

        private List<Object> getAllInstances() {
            List<Object> objects = new ArrayList<>(instances);
            for (Class<?> aClass : children.keySet()) {
                objects.addAll(children.get(aClass).getAllInstances());
            }
            return objects;
        }

        public void clear() {
            this.instances.clear();
            for (Class<?> aClass : this.children.keySet()) {
                children.get(aClass).clear();
            }
            this.children.clear();
        }

        public List<Object> getIntancesByType(Class<?> curType) {
            if (curType == getType()) {
                return this.getAllInstances();
            }
            Stack<Class<?>> parentsClasses = new Stack<>();
            Class<?> tempType = curType;
            while ((tempType = tempType.getSuperclass()) != type) {
                parentsClasses.push(tempType);
            }
            TypeTree curParent = this;
            while (!parentsClasses.empty()) {
                final Class<?> pop = parentsClasses.pop();
                if (curParent.children.containsKey(pop)) {
                    curParent = curParent.children.get(pop);
                } else {
                    return Collections.EMPTY_LIST;
                }
            }
            return curParent.getAllInstances();
        }

    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        if (!definitionMap.containsKey(beanName)) {
            throw new BeansException("No beanDefinition named " + beanName + "!");
        }
        return definitionMap.get(beanName);
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        definitionMap.put(beanName, beanDefinition);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return definitionMap.containsKey(beanName);
    }


    @Override
    public void preInstantial() throws BeansException {
        for (String key : definitionMap.keySet()) {
            final BeanDefinition beanDefinition = getBeanDefinition(key);
            if (SCOPE_SINGLETON.equals(beanDefinition.getScope())) {
                getBean(key);
            }
        }
    }


    @Override
    public List<Object> getBeansOfType(Class<?> clazz) {
        return singletons.values().stream().filter(v -> clazz.isAssignableFrom(v.getClass())).collect(Collectors.toList());
    }

    @Override
    public Set<String> getBeanDefinitionNames() {
        if (definitionMap == null) {
            return Collections.EMPTY_SET;
        }
        return definitionMap.keySet();
    }
}
