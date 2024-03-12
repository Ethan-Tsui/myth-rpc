package com.myth.example.provider;

import com.myth.example.common.service.UserServcie;
import com.myth.mythrpc.RpcApplication;
import com.myth.mythrpc.config.RegistryConfig;
import com.myth.mythrpc.config.RpcConfig;
import com.myth.mythrpc.model.ServiceMetaInfo;
import com.myth.mythrpc.registry.LocalRegistry;
import com.myth.mythrpc.registry.Registry;
import com.myth.mythrpc.registry.RegistryFactory;
import com.myth.mythrpc.server.VertxHttpServer;
import com.myth.mythrpc.server.http.HttpServer;
import com.myth.mythrpc.server.tcp.VertxTcpServer;

/**
 * 简易服务提供者示例
 *
 * @author Ethan
 * @version 1.0
 */
public class ProviderExampleOld {
    public static void main(String[] args) {
        // RPC 框架初始化
        RpcApplication.init();

        // 注册服务
        String serviceName = UserServcie.class.getName();
        LocalRegistry.register(serviceName, UserServiceImpl.class);

        // 注册服务到注册中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfg();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());

        try {
            registry.register(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 启动 web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfg().getServerPort());
    }
}
