package com.myth.mythrpc.fault.tolerant;

import com.myth.mythrpc.RpcApplication;
import com.myth.mythrpc.config.RpcConfig;
import com.myth.mythrpc.model.RpcResponse;

import java.util.Map;

/**
 * 容错策略：降级到其他服务
 *
 * @author Ethan
 * @version 1.0
 */
public class FailBackTolerantStrategy implements TolerantStrategy {

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        // 可自行扩展，获取降级的服务并调用
        // 从注册中心获取服务提供者请求地址
        RpcConfig rpcConfig = RpcApplication.getRpcConfg();
        MockService mockService = MockServiceFactory.getInstance(rpcConfig.getMockService());
        Object mock = mockService.mock();
        return RpcResponse.builder().data(mock).message("ok").build();
    }
}
