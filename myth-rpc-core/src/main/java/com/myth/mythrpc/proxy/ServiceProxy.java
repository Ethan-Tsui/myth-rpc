package com.myth.mythrpc.proxy;

import cn.hutool.core.collection.CollUtil;
import com.myth.mythrpc.RpcApplication;
import com.myth.mythrpc.config.RpcConfig;
import com.myth.mythrpc.constant.RpcConstant;
import com.myth.mythrpc.fault.retry.RetryStrategy;
import com.myth.mythrpc.fault.retry.RetryStrategyFactory;
import com.myth.mythrpc.fault.tolerant.TolerantStrategy;
import com.myth.mythrpc.fault.tolerant.TolerantStrategyFactory;
import com.myth.mythrpc.loadbalancer.LoadBalancer;
import com.myth.mythrpc.loadbalancer.LoadBalancerFactory;
import com.myth.mythrpc.model.RpcRequest;
import com.myth.mythrpc.model.RpcResponse;
import com.myth.mythrpc.model.ServiceMetaInfo;
import com.myth.mythrpc.registry.Registry;
import com.myth.mythrpc.registry.RegistryFactory;
import com.myth.mythrpc.server.tcp.VertxTcpClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务代理 (JDK动态代理)
 *
 * @author Ethan
 * @version 1.0
 */
public class ServiceProxy implements InvocationHandler {

    /**
     * 调用代理
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // 构造请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        // 从注册中心获取服务提供者请求地址
        RpcConfig rpcConfig = RpcApplication.getRpcConfg();
        Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
        List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
//        if (CollUtil.isEmpty(serviceMetaInfoList)) {
//            throw new RuntimeException("暂无服务地址");
//        }
        // 负载均衡
        LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(rpcConfig.getLoadBalancer());
        // 将调用方法名（请求路径）作为负载均衡参数
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("methodName", rpcRequest.getMethodName());
        ServiceMetaInfo selectedServiceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);
        // 发送 rpc 请求
        // 使用重试机制
        RpcResponse rpcResponse;
        try {
            RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
            rpcResponse = retryStrategy.doRetry(() ->
                    VertxTcpClient.doRequest(rpcRequest, selectedServiceMetaInfo)
            );
        } catch (Exception e) {
            // 容错机制
            HashMap<String, Object> map = new HashMap<>();
            map.put("serviceList", serviceMetaInfoList);
            //排查在外的服务
            map.put("errorService", selectedServiceMetaInfo);
            //传递rpcRequest
            map.put("rpcRequest", rpcRequest);
            TolerantStrategy tolerantStrategy = TolerantStrategyFactory.getInstance(rpcConfig.getTolerantStrategy());
            rpcResponse = tolerantStrategy.doTolerant(map, e);
        }

        return rpcResponse.getData();
    }
}
