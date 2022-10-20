package org.originit.hand.factory.impl;

import junit.framework.TestCase;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.originit.hand.exception.BeansException;
import org.originit.hand.factory.support.BeanDefinition;
import org.originit.hand.factory.support.BeanReference;
import org.originit.hand.factory.support.PropertyValue;
import org.originit.hand.factory.support.PropertyValues;
import org.originit.relate.dao.HelloDao;
import org.originit.relate.service.HelloService;

import java.util.Arrays;
import java.util.List;

public class DefaultListableBeanFactoryTest extends TestCase {

    private DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

    List<BeanDefinition> getBeanDefinition() {
        final BeanDefinition helloServiceDefinition = new BeanDefinition(HelloService.class, "helloService");
        final BeanDefinition helloDaoDefinition = new BeanDefinition(HelloDao.class, "helloDao");
        final PropertyValues helloServicePropertyValues = new PropertyValues();
        helloServicePropertyValues.addPropertyValue(PropertyValue.create("name","xxc"));
        helloServicePropertyValues.addPropertyValue(PropertyValue.create("helloDao", BeanReference.refer("helloDao")));
        helloServiceDefinition.setPropertyValues(helloServicePropertyValues);
        return Arrays.asList(helloServiceDefinition,helloDaoDefinition);
    }

    @Test
    public void testBean() {
        for (BeanDefinition beanDefinition : getBeanDefinition()) {
            beanFactory.registerBeanDefinition(beanDefinition.getBeanName(),beanDefinition);
        }
        assertThrows(BeansException.class,()-> beanFactory.getBeanDefinition("none"));
        assertThrows(BeansException.class,() -> beanFactory.getBean("none"));
        assertSame(beanFactory.getBean("helloService"), beanFactory.getBean("helloService"));
        final HelloService helloService = beanFactory.getBean("helloService");
        helloService.sayHello();
    }
}