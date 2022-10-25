package org.originit.hand.context.impl;

import org.originit.hand.context.AbstractXmlApplicationContext;

import java.util.List;

/**
 * @author xxc
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

    public ClassPathXmlApplicationContext(List<String> configLocations) {
        super(configLocations);
        initConfigLocations();
    }

    public ClassPathXmlApplicationContext(String... configLocations) {
        super(configLocations);
        initConfigLocations();
    }

    private void initConfigLocations() {
        for (int i = 0; i < this.configLocations.size(); i++) {
            if (!this.configLocations.get(i).startsWith(CLASS_PATH_PREFIX)) {
                this.configLocations.set(i, CLASS_PATH_PREFIX + this.configLocations.get(i));
            }
        }
    }

}
