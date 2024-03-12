package com.myth.mythrpc.serializer;

import com.alibaba.fastjson.JSON;

/**
 * 快速 JSON 序列化工厂
 *
 * @author Ethan
 * @version 1.0
 */
public class FastJsonSerializer implements Serializer {

    @Override
    public <T> byte[] serialize(T t) {
        String jsonStr = JSON.toJSONString(t);
        return jsonStr.getBytes();
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        return JSON.parseObject(new String(data), clazz);
    }

}
