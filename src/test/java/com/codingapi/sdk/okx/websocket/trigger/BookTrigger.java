package com.codingapi.sdk.okx.websocket.trigger;

import com.codingapi.sdk.okx.websocket.sender.TriggerMessage;
import com.codingapi.springboot.framework.trigger.TriggerHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BookTrigger implements TriggerHandler<TriggerMessage> {

    @Override
    public boolean preTrigger(TriggerMessage trigger) {
        //触发器进入的条件判断
        return trigger.isAction("channel","books");
    }

    @Override
    public void trigger(TriggerMessage trigger) {
        //触发以后的执行逻辑
        log.info("books:{}",trigger.getMsg());
    }

    @Override
    public boolean remove(TriggerMessage trigger, boolean canTrigger) {
        //仅当执行过程Trigger才会被移除
        return canTrigger;
    }

}
