package com.codingapi.sdk.okx.websocket.event;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.codingapi.springboot.framework.event.IEvent;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * 服务消息事件
 */
@ToString
public class MessageEvent implements IEvent {

    /**
     * 消息内容
     */
    @Getter
    private final JSONObject msg;

    @Getter
    private final long ts;

    public MessageEvent(JSONObject msg) {
        this.msg = msg;
        this.ts = System.currentTimeMillis();
    }

    public boolean containsKey(String key) {
        return msg.containsKey(key);
    }

    public JSONObject getObject(String key) {
        return msg.getJSONObject(key);
    }

    public String getString(String key) {
        return msg.getString(key);
    }

    public JSONArray getArrays(String key) {
        return msg.getJSONArray(key);
    }


    /**
     * 动作判断
     * @param key 关键字段
     * @param value 数据内容
     * @return 是否存在
     */
    public boolean isAction(String key, String value){
        if(containsKey("arg")) {
            String result = msg.getJSONObject("arg").getString(key);
            return value.equals(result);
        }
        return false;
    }

    /**
     * 数据转换
     * @param clazz 类型
     * @return 数据列表
     */
    public <T> List<T> getData(Class<T> clazz) {
        if(msg.containsKey("data")) {
            return msg.getJSONArray("data").toJavaList(clazz);
        }
        return null;
    }
}
