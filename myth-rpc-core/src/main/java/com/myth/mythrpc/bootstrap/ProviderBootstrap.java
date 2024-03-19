package com.myth.mythrpc.bootstrap;

import com.myth.mythrpc.RpcApplication;
import com.myth.mythrpc.config.RegistryConfig;
import com.myth.mythrpc.config.RpcConfig;
import com.myth.mythrpc.model.ServiceMetaInfo;
import com.myth.mythrpc.model.ServiceRegisterInfo;
import com.myth.mythrpc.registry.LocalRegistry;
import com.myth.mythrpc.registry.Registry;
import com.myth.mythrpc.registry.RegistryFactory;
import com.myth.mythrpc.server.tcp.VertxTcpServer;

import java.util.List;

/**
 * 服务提供者初始化
 *
 * @author Ethan
 * @version 1.0
 */
public class ProviderBootstrap {

    /**
     * 初始化
     */
    public static void init(List<ServiceRegisterInfo<?>> serviceRegisterInfoList) {

        // RPC 框架初始化（配置和注册中心）
        RpcApplication.init();
        // 全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfg();

        // 注册服务
        for (ServiceRegisterInfo<?> serviceRegisterInfo : serviceRegisterInfoList) {
            String serviceName = serviceRegisterInfo.getServiceName();
            // 本地注册
            LocalRegistry.register(serviceName, serviceRegisterInfo.getImplClass());

            // 注册服务到注册中心
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
            serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
            try {
                registry.register(serviceMetaInfo);
            } catch (Exception e) {
                throw new RuntimeException(serviceName + " 服务注册失败", e);
            }
        }

        // 启动服务器
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(rpcConfig.getServerPort());
    }
}
