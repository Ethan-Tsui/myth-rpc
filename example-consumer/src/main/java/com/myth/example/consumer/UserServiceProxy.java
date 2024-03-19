package com.myth.example.consumer;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.myth.example.common.model.User;
import com.myth.example.common.service.UserServcie;
import com.myth.mythrpc.model.RpcRequest;
import com.myth.mythrpc.model.RpcResponse;
import com.myth.mythrpc.serializer.JdkSerializer;
import com.myth.mythrpc.serializer.Serializer;

import java.io.IOException;

/**
 * 用户服务静态代理
 * 构造 Http 请求去掉用服务提供者
 * 缺点：我们需要给每个服务接口都写一个实现类，是非常麻烦的。
 *
 * @author Ethan
 * @version 1.0
 */
public class UserServiceProxy implements UserServcie {

    @Override
    public User getUser(User user) {

        // 指定序列化器
        final Serializer serializer = new JdkSerializer();

        // 发送请求
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(UserServcie.class.getName())
                .methodName("getUser")
                .parameterTypes(new Class[]{User.class})
                .args(new Object[]{user})
                .build();
        try {
            // 序列化 (Java对象 => 字节数组)
            byte[] bodyBytes = serializer.serialize(rpcRequest);

            // 发送请求
            try (HttpResponse httpResponse = HttpRequest.post("http://localhot:8086")
                    .body(bodyBytes)
                    .execute()) {
                byte[] result = httpResponse.bodyBytes();
                // 反序列化 (字节数组 => Java对象)
                RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
                return (User) rpcResponse.getData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
