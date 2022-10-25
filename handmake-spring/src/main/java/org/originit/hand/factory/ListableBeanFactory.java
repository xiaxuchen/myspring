package org.originit.hand.factory;

import java.util.List;
import java.util.Set;

/**
 * @author xxc
 */
public interface ListableBeanFactory extends BeanFactory{

    List<Object> getBeansOfType(Class<?> clazz);

    Set<String> getBeanDefinitionNames();
}
