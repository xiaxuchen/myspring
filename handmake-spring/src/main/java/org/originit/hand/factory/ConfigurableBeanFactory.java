package org.originit.hand.factory;

/**
 * @author xxc
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory{

    String SCOPE_SINGLETON = "singlton";

    String SCOPE_PROTOTYPE = "prototype";
}
