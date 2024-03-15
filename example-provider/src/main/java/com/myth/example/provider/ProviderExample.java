package com.myth.example.provider;

import com.myth.example.common.service.UserServcie;
import com.myth.mythrpc.bootstrap.ProviderBootstrap;
import com.myth.mythrpc.model.ServiceRegisterInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务提供者示例
 *
 * @author Ethan
 * @version 1.0
 */
public class ProviderExample {
    public static void main(String[] args) {

        // 要注册的服务
        List<ServiceRegisterInfo<?>> serviceRegisterInfoList = new ArrayList<>();
        ServiceRegisterInfo serviceRegisterInfo = new ServiceRegisterInfo(UserServcie.class.getName(), UserServiceImpl.class);
        serviceRegisterInfoList.add(serviceRegisterInfo);

        // 服务提供者初始化
        ProviderBootstrap.init(serviceRegisterInfoList);
    }
}
