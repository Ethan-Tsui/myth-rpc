package com.myth.example.consumer;

import com.myth.mythrpc.config.RpcConfig;
import com.myth.mythrpc.utils.ConfigUtils;

/**
 * 简易服务消费者示例
 *
 * @author Ethan
 * @version 1.0
 */
public class ConsumerExample {
    public static void main(String[] args) {
        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
        System.out.println(rpc);
    }
}
