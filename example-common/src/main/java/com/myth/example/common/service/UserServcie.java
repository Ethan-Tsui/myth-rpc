package com.myth.example.common.service;

import com.myth.example.common.model.User;

/**
 * 用户服务
 *
 * @author Ethan
 * @version 1.0
 */
public interface UserServcie {
    /**
     * 获取用户
     *
     * @param user
     * @return
     */
    User getUser(User user);

    /**
     * 新方法：获取数字
     * @return
     */
    default short getNumber() {
        return 1;
    }
}
