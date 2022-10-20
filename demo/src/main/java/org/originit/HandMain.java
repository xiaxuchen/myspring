package org.originit;


import org.originit.dao.HelloDao;
import org.originit.dao.impl.HelloDaoImpl;
import org.originit.hand.factory.impl.DefaultListableBeanFactory;
import org.originit.hand.factory.support.BeanDefinition;

public class HandMain {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        final BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setBeanName("xxc");
        beanDefinition.setBaseClass(HelloDaoImpl.class);
        beanFactory.registerBeanDefinition("xxc",beanDefinition);
        final HelloDao xxc = beanFactory.getBean("xxc");
        System.out.println(xxc.selectHello());
    }
}
