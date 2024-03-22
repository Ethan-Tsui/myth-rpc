package com.myth.mythrpc.springboot.starter.annotation;

import com.myth.mythrpc.springboot.starter.bootstrap.RpcConsumerBootstrap;
import com.myth.mythrpc.springboot.starter.bootstrap.RpcInitBootstrap;
import com.myth.mythrpc.springboot.starter.bootstrap.RpcProviderBootstrap;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 引入RPC框架，执行初始化
 *
 * @author Ethan
 * @version 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({RpcInitBootstrap.class, RpcProviderBootstrap.class, RpcConsumerBootstrap.class})
public @interface EnableRpc {

    /**
     * 是否需要启动 server
     *
     * @return
     */
    boolean needServer() default true;
}
