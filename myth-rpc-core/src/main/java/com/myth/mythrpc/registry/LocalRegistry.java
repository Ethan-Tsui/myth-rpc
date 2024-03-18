package com.myth.mythrpc.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地注册中心
 *
 * @author Ethan
 * @version 1.0
 */
public class LocalRegistry {

    /**
     * 注册信息存储
     * key 为服务名称
     * value 为服务的实现类
     * 之后就可以根据要调用的服务名称获取到对应的实现类，然后通过反射进行方法调用了。
     */
    private static final Map<String, Class<?>> map = new ConcurrentHashMap<>();

    /**
     * 注册服务
     *
     * @param serviceName
     * @param implClass
     */
    public static void register(String serviceName, Class<?> implClass) {
        map.put(serviceName, implClass);
    }

    /**
     * 获取服务
     *
     * @param serviceName
     * @return
     */
    public static Class<?> get(String serviceName) {
        return map.get(serviceName);
    }

    /**
     * 删除服务
     *
     * @param serviceName
     */
    public static void remove(String serviceName) {
        map.remove(serviceName);
    }

}
