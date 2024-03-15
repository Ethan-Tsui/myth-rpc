package com.myth.mythrpc.fault.tolerant;

import com.myth.mythrpc.model.RpcResponse;

import java.util.Map;

/**
 * 容错策略：快速失败
 * 立刻通知外层调用方
 *
 * @author Ethan
 * @version 1.0
 */
public class FailFastTolerantStrategy implements TolerantStrategy {
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        throw new RuntimeException("服务异常", e);
    }
}
