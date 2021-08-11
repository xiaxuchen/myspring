package org.originit.dao.factory;

import org.originit.dao.HelloDao;
import org.originit.dao.impl.HelloDaoImpl;
import org.originit.dao.impl.HelloDaoOracleImpl;

// 通过工厂模式解耦，但是仍然需要更改代码，这样不如就改成配置
public class DaoFactory {

    public static HelloDao getHelloDao () {
        return new HelloDaoImpl();
    }
}
