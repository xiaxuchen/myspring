package org.originit.hand.event;

public interface SpringEventMulticaster {

    void addListener(SpringEventListener eventListener);

    void removeListener(SpringEventListener eventListener);

    void multicastEvent(SpringEvent event);
}
