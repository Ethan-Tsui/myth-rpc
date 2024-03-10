package com.myth.example.provider;

import com.myth.example.common.model.User;
import com.myth.example.common.service.UserServcie;

/**
 * 用户服务具体实现类
 */
public class UserServiceImpl implements UserServcie {
    public User getUser(User user) {
        System.out.println("用户名：" + user.getName());
        return user;
    }
}
