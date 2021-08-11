package org.originit.controller;


import org.originit.dao.HelloDao;
import org.originit.factory.BeanFactory;
import org.originit.service.HelloService;
import org.originit.service.factory.ServiceFactory;
import org.originit.service.impl.HelloServiceImpl;

public class HelloController {

    // 上层依赖于抽象而不依赖于具体的实现，这样实现可以动态改变而上层无需变化
    private HelloService helloService;

    public void initFields (BeanFactory beanFactory) {
        helloService = (HelloService) beanFactory.getBean("helloService");
    }

    public String sayHello() {
        return helloService.getHelloMsg();
    }
}
