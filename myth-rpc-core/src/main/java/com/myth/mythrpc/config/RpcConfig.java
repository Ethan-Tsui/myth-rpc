package com.myth.mythrpc.config;

import com.myth.mythrpc.fault.retry.RetryStrategyKeys;
import com.myth.mythrpc.loadbalancer.LoadBalancerKeys;
import com.myth.mythrpc.serializer.SerializerKeys;
import lombok.Data;

/**
 * RPC 框架配置
 * 保存配置信息
 *
 * @author Ethan
 * @version 1.0
 */
@Data
public class RpcConfig {

    /**
     * 模拟调用
     */
    private boolean mock = false;

    /**
     * 名称
     */
    private String name = "myth-rpc";

    /**
     * 版本号
     */
    private String version = "1.0";

    /**
     * 服务器主机名
     */
    private String serverHost = "localhost";

    /**
     * 服务器端口号
     */
    private Integer serverPort = 8080;

    /**
     * 序列化器
     */
    private String serializer = SerializerKeys.JDK;

    /**
     * 负载均衡器
     */
    private String loadBalancer = LoadBalancerKeys.ROUND_ROBIN;

    /**
     * 注册表配置
     */
    private RegistryConfig registryConfig = new RegistryConfig();

    /**
     * 重试策略
     */
    private String retryStrategy = RetryStrategyKeys.NO;
}
