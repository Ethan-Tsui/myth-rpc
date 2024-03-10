package com.myth.example.provider;

import com.myth.example.common.service.UserServcie;
import com.myth.mythrpc.registry.LocalRegistry;
import com.myth.mythrpc.server.VertxHttpServer;


/**
 * 简易服务提供者示例
 */
public class EasyProviderExample {

    public static void main(String[] args) {
        // 注册服务
        LocalRegistry.register(UserServcie.class.getName(), UserServiceImpl.class);

        // 启动 web 服务
        VertxHttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8080);
    }
}
