package com.myth.example.springboot.provider;

import com.myth.example.common.model.User;
import com.myth.example.common.service.UserServcie;
import com.myth.mythrpc.springboot.starter.annotation.RpcService;
import org.springframework.stereotype.Service;

/**
 * @author Ethan
 * @version 1.0
 */
@Service
@RpcService
public class UserServiceImpl implements UserServcie {
    public User getUser(User user) {
        System.out.println("用户名：" + user.getName());
        return user;
    }
}
