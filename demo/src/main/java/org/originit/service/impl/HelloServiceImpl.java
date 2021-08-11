package org.originit.service.impl;

import org.originit.dao.HelloDao;
import org.originit.dao.factory.DaoFactory;
import org.originit.dao.impl.HelloDaoImpl;
import org.originit.dao.impl.HelloDaoOracleImpl;
import org.originit.factory.BeanFactory;
import org.originit.service.HelloService;

public class HelloServiceImpl implements HelloService {

    // dao层变化，需要使用oracle
    // 1. 直接修改dao实现，这里就可以不改变，但这是一个非常大的需求，非常大的变动，如果直接去改造成oracle相适配，但是后面又要求改回来或者是相互兼容，就需要重新写或者退版本，那就可以采用方案2
    // 2. 编写一个oracle的实现，然后在所有用到HelloDao的地方都替换掉实现类，但这样也有很大的问题，项目如果比较大的话，很容易遗漏
    // 3. 可以使用工厂实现，这样所有地方都依赖于工厂，具体实现可以进行修改
    // 4. 依赖注入，
    private HelloDao helloDao = null;

    public void initFields (BeanFactory beanFactory) {
        helloDao = (HelloDao) beanFactory.getBean("helloDao");
    }

    public String getHelloMsg() {
        return helloDao.selectHello();
    }
}
