package com.myth.mythrpc.fault.tolerant;

import com.myth.mythrpc.spi.SpiLoader;

/**
 * 模拟服务工厂
 *
 * @author Ethan
 * @version 1.0
 */
public class MockServiceFactory {

    static {
        SpiLoader.load(MockService.class);
    }


    /**
     * 获取实例
     *
     * @param key 钥匙
     * @return {@link MockService}
     */
    public static MockService getInstance(String key) {
        return SpiLoader.getInstance(MockService.class, key);
    }
}
