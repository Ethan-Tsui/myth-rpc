package com.myth.example.consumer;

import com.myth.example.common.model.User;
import com.myth.example.common.service.UserServcie;
import com.myth.mythrpc.proxy.ServiceProxyFactory;

/**
 * 简易服务消费者示例
 *
 * @author Ethan
 * @version 1.0
 */
public class EasyConsumerExample {
    public static void main(String[] args) {
        // 静态代理
//        UserService userService = new UserServiceProxy();
        // 动态代理
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
