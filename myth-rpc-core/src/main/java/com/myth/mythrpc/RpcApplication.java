package com.myth.mythrpc;

import com.myth.mythrpc.config.RegistryConfig;
import com.myth.mythrpc.config.RpcConfig;
import com.myth.mythrpc.constant.RpcConstant;
import com.myth.mythrpc.registry.Registry;
import com.myth.mythrpc.registry.RegistryFactor;
import com.myth.mythrpc.utils.ConfigUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * PRC 框架应用
 * 相当于 holder，存放了项目全局用到的变量。双检查锁单例模式实现。
 *
 * @author Ethan
 * @version 1.0
 */
@Slf4j
public class RpcApplication {
    private static volatile RpcConfig rpcConfig;

    /**
     * 初始化
     */
    public static void init() {
        RpcConfig newRpcConfig;
        try {
            newRpcConfig = ConfigUtils.loadConfig(RpcConfig.class, RpcConstant.DEFAULT_CONFIG_PREFIX);
        } catch (Exception e) {
            newRpcConfig = new RpcConfig();
        }
        init(newRpcConfig);
    }

    /**
     * 获取配置
     *
     * @return
     */
    public static RpcConfig getRpcConfg() {
        if (rpcConfig == null) {
            synchronized (RpcApplication.class) {
                if (rpcConfig == null) {
                    init();
                }
            }
        }
        return rpcConfig;
    }

    /**
     * 框架初始化，支持传入自定义配置
     *
     * @param newRpcConfig
     */
    public static void init(RpcConfig newRpcConfig) {
        rpcConfig = newRpcConfig;
        log.info("rpc init, config = {}", newRpcConfig.toString());
        // 注册中心初始化
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactor.getInstance(registryConfig.getRegistry());
        registry.init(registryConfig);
        log.info("registry init, config = {}", registryConfig);

        // 创建并注册 Shutdown Hook，JVM退出时执行操作
        Runtime.getRuntime().addShutdownHook(new Thread(registry::destroy));
    }
}
