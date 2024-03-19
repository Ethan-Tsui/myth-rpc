package com.myth.mythrpc.proxy;

import com.myth.mythrpc.RpcApplication;

import java.lang.reflect.Proxy;

/**
 * 服务代理工厂
 * 根据指定类创建动态代理对象
 *
 * @author Ethan
 * @version 1.0
 */
public class ServiceProxyFactory {

    /**
     * 获取代理
     * 根据服务类获取代理对象
     *
     * @param serviceClass
     * @param <T>
     * @return
     */
    public static <T> T getProxy(Class<T> serviceClass) {
        if (RpcApplication.getRpcConfg().isMock()) {
            return getMockProxy(serviceClass);
        }
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new ServiceProxy());
    }

    /**
     * 获取模拟代理
     * 根据服务类获取 Mock 代理对象
     *
     * @param serviceClass
     * @param <T>
     * @return
     */
    public static <T> T getMockProxy(Class<T> serviceClass) {
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new MockServiceProxy());
    }
}
