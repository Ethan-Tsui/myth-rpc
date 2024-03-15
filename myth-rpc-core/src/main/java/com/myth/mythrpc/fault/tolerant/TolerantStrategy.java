package com.myth.mythrpc.fault.tolerant;

import com.myth.mythrpc.model.RpcResponse;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 容错策略
 *
 * @author Ethan
 * @version 1.0
 */
public interface TolerantStrategy {

    /**
     * 容错
     *
     * @param context 上下文，用于传输数据
     * @param e       异常
     * @return
     */
    RpcResponse doTolerant(Map<String, Object> context, Exception e) throws ExecutionException, InterruptedException;
}
