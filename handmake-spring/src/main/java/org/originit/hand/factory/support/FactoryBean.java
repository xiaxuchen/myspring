package org.originit.hand.factory.support;

public interface FactoryBean<T> {

    T getObject();

    Class<T> getType();

    boolean isSinglton();
}
