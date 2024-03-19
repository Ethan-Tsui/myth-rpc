package com.myth.mythrpc.fault.retry;

import com.myth.mythrpc.model.RpcResponse;

import java.util.concurrent.Callable;

/**
 * 重试策略 —— 不重试
 *
 * @author Ethan
 * @version 1.0
 */
public class NoRetryStrategy implements RetryStrategy {

    @Override
    public RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception {
        return callable.call();
    }
}
