package com.codingapi.sdk.okx.websocket.sender;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.codingapi.springboot.framework.trigger.Trigger;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
public class TriggerMessage implements Trigger {

    /**
     * 消息内容
     */
    @Getter
    private final JSONObject msg;

    @Getter
    private final long ts;

    public TriggerMessage(JSONObject msg) {
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
     * 操作判断
     * @param op 操作类型
     * @return  是否存在
     */
    public boolean isOp(String op){
        if(containsKey("op")) {
            return msg.getString("op").equals(op);
        }
        return false;
    }


    /**
     * id判断
     * @param id 操作唯一id
     * @return  是否存在
     */
    public boolean isId(String id){
        if(containsKey("id")) {
            return msg.getString("id").equals(id);
        }
        return false;
    }

    /**
     * id与op同时判断
     * @param op 操作类型
     * @param id 操作唯一id
     * @return 是否存在
     */
    public boolean isOpWithId(String op,String id){
        return isOp(op) && isId(id);
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
