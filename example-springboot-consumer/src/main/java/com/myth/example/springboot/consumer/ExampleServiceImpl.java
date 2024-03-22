package com.myth.example.springboot.consumer;

import com.myth.example.common.model.User;
import com.myth.example.common.service.UserServcie;
import com.myth.mythrpc.springboot.starter.annotation.RpcReference;
import org.springframework.stereotype.Service;

/**
 * 服务消费者
 *
 * @author Ethan
 * @version 1.0
 */
@Service
public class ExampleServiceImpl {
    @RpcReference
    private UserServcie userServcie;

    public void test() {
        User user = new User();
        user.setName("myth");
        User resultUser = userServcie.getUser(user);
        System.out.println(resultUser.getName());
    }
}
