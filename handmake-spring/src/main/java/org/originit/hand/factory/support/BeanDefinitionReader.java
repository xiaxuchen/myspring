package org.originit.hand.factory.support;

import org.originit.hand.exception.BeansException;
import org.originit.hand.io.Resource;
import org.originit.hand.io.ResourceLoader;

/**
 * @author xxc
 */
public interface BeanDefinitionReader {

    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(Resource resource) throws BeansException;

    void loadBeanDefinitions(Resource... resources) throws BeansException;

    void loadBeanDefinitions(String location) throws BeansException;
}