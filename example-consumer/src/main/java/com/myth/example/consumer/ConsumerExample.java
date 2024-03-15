package com.myth.example.consumer;

import com.myth.example.common.model.User;
import com.myth.example.common.service.UserServcie;
import com.myth.mythrpc.bootstrap.ConsumerBootstrap;
import com.myth.mythrpc.proxy.ServiceProxyFactory;

/**
 * 服务消费者示例
 *
 * @author Ethan
 * @version 1.0
 */
public class ConsumerExample {
    public static void main(String[] args) {

        // 服务消费者初始化
        ConsumerBootstrap.init();

        // 获取代理
        UserServcie userService = ServiceProxyFactory.getProxy(UserServcie.class);
        User user = new User();
        user.setName("myth");
        // 调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
    }
}
