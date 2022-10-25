package org.originit.hand.context;

import org.originit.hand.factory.impl.DefaultListableBeanFactory;
import org.originit.hand.factory.support.impl.XmlBeanDefinitionReader;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext{

    protected List<String> configLocations;

    public AbstractXmlApplicationContext(List<String> configLocations) {
        if (configLocations == null || configLocations.isEmpty()) {
            this.configLocations = Collections.EMPTY_LIST;
        } else {
            this.configLocations = configLocations;
        }
        refresh();
    }

    public AbstractXmlApplicationContext(String...configLocations) {
        this.configLocations = Arrays.asList(configLocations);
        refresh();
    }

    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        for (String configLocation : configLocations) {
            xmlBeanDefinitionReader.loadBeanDefinitions(getResource(configLocation));
        }
    }
}
