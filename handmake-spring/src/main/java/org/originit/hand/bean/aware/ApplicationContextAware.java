package org.originit.hand.bean.aware;

import org.originit.hand.context.ApplicationContext;

public interface ApplicationContextAware extends Aware{

    void setApplicationContext(ApplicationContext context);
}
