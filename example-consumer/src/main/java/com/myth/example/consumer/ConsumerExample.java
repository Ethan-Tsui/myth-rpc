package com.myth.example.consumer;

import com.myth.example.common.model.User;
import com.myth.example.common.service.UserServcie;
import com.myth.mythrpc.config.RpcConfig;
import com.myth.mythrpc.proxy.ServiceProxyFactory;
import com.myth.mythrpc.utils.ConfigUtils;

/**
 * 简易服务消费者示例
 *
 * @author Ethan
 * @version 1.0
 */
public class ConsumerExample {
    public static void main(String[] args) {
//        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
//        System.out.println(rpc);

        // 获取代理
        UserServcie userServcie = ServiceProxyFactory.getProxy(UserServcie.class);
        User user = new User();
        user.setName("myth");
        // 调用
        User newUser = userServcie.getUser(user);
        if(newUser != null){
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
        long number = userServcie.getNumber();
        System.out.println(number);
    }
}
