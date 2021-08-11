package org.originit.service.factory;

import org.originit.service.HelloService;
import org.originit.service.impl.HelloServiceImpl;

public class ServiceFactory {

    public static HelloService getHelloService() {
        return new HelloServiceImpl();
    }
}
