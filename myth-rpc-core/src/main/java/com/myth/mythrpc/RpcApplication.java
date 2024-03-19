package com.myth.mythrpc;

import com.myth.mythrpc.config.RegistryConfig;
import com.myth.mythrpc.config.RpcConfig;
import com.myth.mythrpc.constant.RpcConstant;
import com.myth.mythrpc.registry.Registry;
import com.myth.mythrpc.registry.RegistryFactory;
import com.myth.mythrpc.utils.ConfigUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 维护的全局的配置对象
 * 相当于 holder，存放了项目全局用到的变量。双检查锁单例模式实现。
 * 在引入 RPC 框架的项目启动时，从配置文件中读取配置并创建对象实例，之后就可以集中地从这个对象中获取配置信息，而不用每次加载配置时再重新读取配置、并创建新的对象，减少了性能开销。
 * 单例模式
 *
 * @author Ethan
 * @version 1.0
 */
@Slf4j
public class RpcApplication {

    private static volatile RpcConfig rpcConfig;

    /**
     * 框架初始化
     * 支持传入自定义配置
     *
     * @param newRpcConfig
     */
    public static void init(RpcConfig newRpcConfig) {
        rpcConfig = newRpcConfig;
        log.info("rpc init, config = {}", newRpcConfig.toString());
        // 注册中心初始化
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        registry.init(registryConfig);
        log.info("registry init, config = {}", registryConfig);

        // 创建并注册 Shutdown Hook，JVM退出时执行操作
        Runtime.getRuntime().addShutdownHook(new Thread(registry::destroy));
    }

    /**
     * 初始化
     * 框架初始化，支持传入自定义配置
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
     * 获取 RPC 配置
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
}
