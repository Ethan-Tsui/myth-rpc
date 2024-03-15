package com.myth.mythrpc.bootstrap;

import com.myth.mythrpc.RpcApplication;

/**
 * 服务消费者启动类(初始化)
 *
 * @author Ethan
 * @version 1.0
 */
public class ConsumerBootstrap {

    /**
     * 初始化
     */
    public static void init() {
        // RPC 框架初始化（配置和注册中心）
        RpcApplication.init();
    }
}
