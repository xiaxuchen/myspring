package org.originit;

import org.originit.controller.HelloController;
import org.originit.factory.impl.ClasspathPropsBeanFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        HelloController helloController = (HelloController) new ClasspathPropsBeanFactory("beans.properties").getBean("helloController");
        System.out.println(helloController.sayHello());
    }
}
