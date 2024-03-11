package com.myth.mythrpc.loadbalancer;

import com.myth.mythrpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;

/**
 * 负载均衡器 (消费段使用)
 *
 * @author Ethan
 * @version 1.0
 */
public interface LoadBalancer {

    /**
     * 选择服务调用
     *
     * @param requestParams       请求参数
     * @param serviceMetaInfoList 可用服务列表
     * @return
     */
    ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList);
}
