package com.myth.mythrpc.registry;

import com.myth.mythrpc.spi.SpiLoader;

/**
 * 注册中心工厂 (用于获取注册中心对象)
 *
 * @author Ethan
 * @version 1.0
 */
public class RegistryFactor {

    static {
        SpiLoader.load(Registry.class);
    }

    /**
     * 默认注册中心
     */
    private static final Registry DEFAULT_REGISTRY = new EtcdRegistry();

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