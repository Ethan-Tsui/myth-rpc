package com.myth.mythrpc.fault.retry;

import com.myth.mythrpc.spi.SpiLoader;

/**
 * 重试策略工厂 (用于获取重试器对象)
 *
 * @author Ethan
 * @version 1.0
 */
public class RetryStrategyFactory {

    private RetryStrategyFactory() {
        throw new IllegalStateException("Utility class");
    }

    static {
        SpiLoader.load(RetryStrategy.class);
    }

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static RetryStrategy getInstance(String key) {
        return SpiLoader.getInstance(RetryStrategy.class, key);
    }
}
