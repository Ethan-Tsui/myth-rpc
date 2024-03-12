package com.myth.mythrpc.serializer;

import java.io.IOException;

/**
 * 序列化器接口
 * 提供了序列化和反序列化两个方法
 *
 * @author Ethan
 * @version 1.0
 */
public interface Serializer {
    /**
     * 序列化
     *
     * @param object
     * @param <T>
     * @return
     * @throws IOException
     */
    <T> byte[] serialize(T object) throws IOException;

    /**
     * 反序列化
     *
     * @param bytes
     * @param type
     * @param <T>
     * @return
     * @throws IOException
     */
    <T> T deserialize(byte[] bytes, Class<T> type) throws IOException;
}
