package org.originit.hand.factory.impl;

import junit.framework.TestCase;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.originit.hand.exception.BeansException;
import org.originit.hand.context.impl.ClassPathXmlApplicationContext;
import org.originit.hand.factory.ConfigurableListableBeanFactory;
import org.originit.hand.factory.support.*;
import org.originit.hand.factory.support.impl.XmlBeanDefinitionReader;
import org.originit.relate.dao.HelloDao;
import org.originit.relate.service.HelloService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultListableBeanFactoryTest extends TestCase {


    List<BeanDefinition> getBeanDefinition() {
        final BeanDefinition helloServiceDefinition = new BeanDefinition(HelloService.class, "helloService");
        helloServiceDefinition.setScope(ConfigurableListableBeanFactory.SCOPE_SINGLETON);
        final BeanDefinition helloDaoDefinition = new BeanDefinition(HelloDao.class, "helloDao");
        helloDaoDefinition.setScope(ConfigurableListableBeanFactory.SCOPE_SINGLETON);
        final ArrayList<Object> serviceConst = new ArrayList<>();
        serviceConst.add("xxc");
        serviceConst.add(BeanReference.refer("helloDao"));
        helloServiceDefinition.setConstructArgs(new ConstructArgs(serviceConst));
        return Arrays.asList(helloServiceDefinition,helloDaoDefinition);
    }

    @Test
    public void testBean() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        for (BeanDefinition beanDefinition : getBeanDefinition()) {
            beanFactory.registerBeanDefinition(beanDefinition.getBeanName(),beanDefinition);
        }
        beanFactory.preInstantial();
        assertThrows(BeansException.class,()-> beanFactory.getBeanDefinition("none"));
        assertThrows(BeansException.class,() -> beanFactory.getBean("none"));
        assertNotNull(beanFactory.getBeanDefinition("helloService"));
        assertSame(beanFactory.getBean("helloService"), beanFactory.getBean("helloService"));
        assertEquals(beanFactory.getBeansOfType(Object.class).size(),2);
        final HelloService helloService = beanFactory.getBean("helloService");
        helloService.sayHello();
    }

    @Test
    public void testXml() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        final XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions("classpath:spring.xml");
        assertThrows(BeansException.class,()-> beanFactory.getBeanDefinition("none"));
        assertThrows(BeansException.class,() -> beanFactory.getBean("none"));
        assertNotNull(beanFactory.getBeanDefinition("helloService"));
        assertSame(beanFactory.getBean("helloService"), beanFactory.getBean("helloService"));
        final HelloService helloService = beanFactory.getBean("helloService");
        helloService.sayHello();
    }

    @Test
    public void testApplicationContext() {
        ClassPathXmlApplicationContext xmlApplicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        final DefaultListableBeanFactory beanFactory = xmlApplicationContext.getBeanFactory();
        assertThrows(BeansException.class,()-> beanFactory.getBeanDefinition("none"));
        assertThrows(BeansException.class,() -> beanFactory.getBean("none"));
        assertNotNull(beanFactory.getBeanDefinition("helloService"));
        assertNotSame(beanFactory.getBean("hsf"), beanFactory.getBean("helloService"));
        assertNotSame(beanFactory.getBean("helloService"), beanFactory.getBean("helloService"));
        final HelloService helloService = beanFactory.getBean("hsf");
        helloService.sayHello();
    }
}