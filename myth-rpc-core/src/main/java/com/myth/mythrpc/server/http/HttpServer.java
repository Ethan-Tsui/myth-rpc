package com.myth.mythrpc.server.http;

/**
 * WEB 服务器接口
 * 定义统一的启动服务器方法，便于后续的扩展，比如实现多种不同的 web 服务器
 *
 * @author Ethan
 * @version 1.0
 */
public interface HttpServer {

    /**
     * 启动服务器
     *
     * @param port 端口
     */
    void doStart(int port);
}
