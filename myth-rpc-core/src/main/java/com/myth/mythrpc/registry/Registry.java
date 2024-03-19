package com.myth.mythrpc.registry;

import com.myth.mythrpc.config.RegistryConfig;
import com.myth.mythrpc.model.ServiceMetaInfo;

import java.util.List;

/**
 * 注册中心接口
 * 后续可以实现多种不同的注册中心，并且和序列化器一样，可以使用 SPI 机制动态加载
 *
 * @author Ethan
 * @version 1.0
 */
public interface Registry {

    /**
     * 初始化
     *
     * @param registryConfig
     */
    void init(RegistryConfig registryConfig);

    /**
     * 注册服务（服务端）
     *
     * @param serviceMetaInfo
     */
    void register(ServiceMetaInfo serviceMetaInfo) throws Exception;

    /**
     * 注销服务（服务端）
     *
     * @param serviceMetaInfo
     */
    void unRegister(ServiceMetaInfo serviceMetaInfo);

    /**
     * 服务发现（获取某服务的所有节点，消费端）
     *
     * @param serviceKey 服务键名
     * @return
     */
    List<ServiceMetaInfo> serviceDiscovery(String serviceKey);

    /**
     * 服务销毁
     */
    void destroy();

    /**
     * 心跳检测 (服务端)
     */
    void heartBeat();

    /**
     * 监听 (消费端)
     *
     * @param serviceNodeKey
     */
    void watch(String serviceNodeKey);
}
