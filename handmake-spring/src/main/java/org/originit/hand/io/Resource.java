package org.originit.hand.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author xxc
 */
public interface Resource {

    /**
     * 获取资源的流
     */
    InputStream getInputStream() throws IOException;
}
