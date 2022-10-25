package org.originit.relate.service;

import org.originit.hand.bean.DisposableBean;
import org.originit.relate.dao.HelloDao;

public class HelloService implements DisposableBean {

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
}
