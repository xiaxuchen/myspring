package org.originit.hand.context;

/**
 * @author xxc
 */
public interface ConfigurableApplicationContext extends ApplicationContext{

    /**
     * 刷新
     */
    void refresh();

    /**
     * 关闭应用
     */
    void close();

    /**
     * 注册关闭钩子
     */
    void registerShutdownHooks();
}
