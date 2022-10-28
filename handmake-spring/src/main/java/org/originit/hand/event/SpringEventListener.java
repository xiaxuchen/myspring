package org.originit.hand.event;

public interface SpringEventListener<T extends SpringEvent> {

    /**
     * 事件触发
     * @param event 事件
     */
    void onEvent(T event);

}
