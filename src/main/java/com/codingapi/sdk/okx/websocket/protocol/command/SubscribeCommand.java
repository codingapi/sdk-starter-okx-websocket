package com.codingapi.sdk.okx.websocket.protocol.command;

import com.alibaba.fastjson.JSONObject;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ToString
public class SubscribeCommand extends HashMap<String,Object> implements ICommand {

    public SubscribeCommand(String channel, String instId) {
        this.put("channel",channel);
        this.put("instId",instId);
    }

    public SubscribeCommand addParameter(String key,Object value){
        this.put(key, value);
        return this;
    }

    @Override
    public String toCmd() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("op", "subscribe");
        List<SubscribeCommand> commands = new ArrayList<>();
        commands.add(this);
        jsonObject.put("args", commands);
        return jsonObject.toJSONString();
    }
}
