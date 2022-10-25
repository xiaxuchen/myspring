package org.originit.hand.io;

/**
 * @author xxc
 */
public interface ResourceLoader {

    String CLASS_PATH_PREFIX = "classpath:";

    /**
     * 根据资源位置获取资源资源
     * @param location 资源位置
     * @return 资源
     */
    Resource getResource(String location);
}
