package com.myth.mythrpc.server;

/**
 * HTTP 服务器接口
 *
 * @author Ethan
 * @version 1.0
 */
public interface HttpServer {
    /**
     * 启动服务器
     * @param port
     */
    void doStart(int port);
}
