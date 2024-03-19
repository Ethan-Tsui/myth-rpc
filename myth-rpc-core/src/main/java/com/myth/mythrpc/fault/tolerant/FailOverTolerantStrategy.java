package com.myth.mythrpc.fault.tolerant;

import cn.hutool.core.collection.CollUtil;
import com.myth.mythrpc.loadbalancer.LoadBalancer;
import com.myth.mythrpc.loadbalancer.LoadBalancerFactory;
import com.myth.mythrpc.loadbalancer.LoadBalancerKeys;
import com.myth.mythrpc.model.RpcRequest;
import com.myth.mythrpc.model.RpcResponse;
import com.myth.mythrpc.model.ServiceMetaInfo;
import com.myth.mythrpc.server.tcp.VertxTcpClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 容错策略：转移到其他服务节点
 *
 * @author Ethan
 * @version 1.0
 */
public class FailOverTolerantStrategy implements TolerantStrategy{
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) throws ExecutionException, InterruptedException {

        //可自行扩展，获取其他服务节点并调用
        List<ServiceMetaInfo> serviceMetaInfoList = (List<ServiceMetaInfo>) context.get("serviceList");
        ServiceMetaInfo errorService = (ServiceMetaInfo) context.get("errorService");
        RpcRequest rpcRequest = (RpcRequest) context.get("rpcRequest");
        // 从服务列表中移除错误服务
        serviceMetaInfoList.remove(errorService);
        // 重新调用其他服务
        if (CollUtil.isNotEmpty(serviceMetaInfoList)) {
            // 重新调用其他服务
            // 负载均衡
            // 将调用方法名（请求路径）作为负载均衡参数
            Map<String, Object> requestParams = new HashMap<>();
            requestParams.put("methodName", rpcRequest.getMethodName());
            // 负载均衡
            LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(LoadBalancerKeys.ROUND_ROBIN);
            ServiceMetaInfo selectedServiceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);
            return VertxTcpClient.doRequest(rpcRequest, selectedServiceMetaInfo);
        }
        return null;    }
}
