package com.myth.mythrpc.fault.retry;

import com.myth.mythrpc.model.RpcResponse;

import java.util.concurrent.Callable;

/**
 * 重试策略
 *
 * @author Ethan
 * @version 1.0
 */
public interface RetryStrategy {
    /**
     * 重试
     * @param callable
     * @return
     * @throws Exception
     */
    RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception;
}
