package org.originit;

import org.originit.dao.HelloDao;
import org.originit.dao.impl.HelloDaoImpl;
import org.originit.hand.bean.BeanDefinition;
import org.originit.hand.factory.BeanFactory;

public class HandMain {

    public static void main(String[] args) {
        BeanFactory beanFactory = new BeanFactory();
        beanFactory.registeBeanDefinition("helloDao", new BeanDefinition(new HelloDaoImpl()));
        final HelloDao helloDao = (HelloDao) beanFactory.getBean("helloDao");
        System.out.println(helloDao.selectHello());
    }
}
