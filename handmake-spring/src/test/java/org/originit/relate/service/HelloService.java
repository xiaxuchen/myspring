package org.originit.relate.service;

import org.originit.relate.dao.HelloDao;

public class HelloService {

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
}
