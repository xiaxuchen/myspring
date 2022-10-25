package org.originit.relate.service;

public class HelloServiceDelegate extends HelloService{

    private HelloService service;

    public HelloServiceDelegate(HelloService service) {
        this.service = service;
    }

    @Override
    public void sayHello() {
        System.out.println("我劫持了");
        service.sayHello();
    }
}
