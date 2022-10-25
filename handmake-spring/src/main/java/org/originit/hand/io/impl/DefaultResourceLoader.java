package org.originit.hand.io.impl;

import cn.hutool.core.lang.Assert;
import org.originit.hand.io.Resource;
import org.originit.hand.io.ResourceLoader;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author xxc
 */
public class DefaultResourceLoader implements ResourceLoader {
    @Override
    public Resource getResource(String location) {
        Assert.notNull(location, "Location must not be null");
        if (location.startsWith(CLASS_PATH_PREFIX)) {
            return new ClassPathResource(location.substring(CLASS_PATH_PREFIX.length()));
        } else {
            try {
                URL url = new URL(location);
                return new UrlResource(url);
            } catch (MalformedURLException e) {
                return new SystemFileResource(location);
            }
        }
    }
}