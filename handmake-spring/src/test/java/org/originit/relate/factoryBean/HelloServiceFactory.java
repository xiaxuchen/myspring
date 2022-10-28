package org.originit.relate.factoryBean;

import org.originit.hand.factory.support.FactoryBean;
import org.originit.relate.dao.HelloDao;
import org.originit.relate.service.HelloService;

public class HelloServiceFactory implements FactoryBean<HelloService> {

    private HelloDao helloDao;

    @Override
    public HelloService getObject() {
        return new HelloService("factoryBean",helloDao);
    }

    @Override
    public Class<HelloService> getType() {
        return HelloService.class;
    }

    @Override
    public boolean isSinglton() {
        return true;
    }
}
