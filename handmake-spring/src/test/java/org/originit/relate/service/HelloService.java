package org.originit.relate.service;

import org.originit.hand.bean.DisposableBean;
import org.originit.hand.bean.aware.ApplicationContextAware;
import org.originit.hand.bean.aware.BeanNameAware;
import org.originit.hand.context.ApplicationContext;
import org.originit.relate.dao.HelloDao;

public class HelloService implements DisposableBean, ApplicationContextAware, BeanNameAware {

    private String name;

    private HelloDao helloDao;

    public HelloService() {
    }

    public HelloService(String name, HelloDao helloDao) {
        this.name = name;
        this.helloDao = helloDao;
    }

    public void sayHello() {
        System.out.println(helloDao.hello() + "," + name);
    }

    private void init() {
        System.out.println("init");
    }

    @Override
    public void destory() {
        System.out.println("dest");
    }

    @Override
    public void setApplicationContext(ApplicationContext context) {
        System.out.println(context);
    }

    @Override
    public void setBeanName(String name) {
        System.out.println(name);
    }
}
