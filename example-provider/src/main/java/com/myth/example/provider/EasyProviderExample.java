package com.myth.example.provider;

import com.myth.example.common.service.UserServcie;
import com.myth.mythrpc.RpcApplication;
import com.myth.mythrpc.registry.LocalRegistry;
import com.myth.mythrpc.server.http.VertxHttpServer;
import com.myth.mythrpc.server.HttpServer;


/**
 * 简易服务提供者示例
 */
public class EasyProviderExample {

    public static void main(String[] args) {

        // RPC 框架初始化
        RpcApplication.init();

        // 注册服务：服务启动时，将服务注册到本地服务中心
        LocalRegistry.register(UserServcie.class.getName(), UserServiceImpl.class);

        // 启动 web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfg().getServerPort());
    }
}
