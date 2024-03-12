package com.myth.mythrpc.registry;

import com.myth.mythrpc.spi.SpiLoader;

/**
 * 注册中心工厂 (用于获取注册中心对象)
 * 支持根据 key 从 SPI 获取注册中心对象实例
 *
 * @author Ethan
 * @version 1.0
 */
public class RegistryFactory {

    static {
        SpiLoader.load(Registry.class);
    }

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static Registry getInstance(String key) {
        return SpiLoader.getInstance(Registry.class, key);
    }

}
