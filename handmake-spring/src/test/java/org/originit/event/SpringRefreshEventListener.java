package org.originit.event;

import org.originit.hand.event.SpringEventListener;
import org.originit.hand.event.impl.BeanFactoryCloseEvent;
import org.originit.hand.event.impl.SpringRefreshEvent;

public class SpringRefreshEventListener implements SpringEventListener<BeanFactoryCloseEvent> {
    @Override
    public void onEvent(BeanFactoryCloseEvent event) {
        System.out.println("spring refreshed" + event);
    }

}
