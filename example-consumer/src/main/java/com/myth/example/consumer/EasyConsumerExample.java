package com.myth.example.consumer;

import com.myth.example.common.model.User;
import com.myth.example.common.service.UserServcie;
import com.myth.mythrpc.proxy.ServiceProxyFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * 简易服务消费者示例
 *
 * @author Ethan
 * @version 1.0
 */
@Slf4j
public class EasyConsumerExample {
    public static void main(String[] args) {
        // 静态代理
//        UserService userService = new UserServiceProxy();

        ServiceProxyFactory serviceProxyFactory = new ServiceProxyFactory();

        // 动态代理
        UserServcie userService = serviceProxyFactory.getProxy(UserServcie.class);
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
