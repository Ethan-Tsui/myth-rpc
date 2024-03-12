package com.myth.mythrpc.server;

import com.myth.mythrpc.server.http.HttpServer;
import com.myth.mythrpc.server.http.HttpServerHandler;
import io.vertx.core.Vertx;

/**
 * 基于 Vert.x 实现的 web 服务器 VertxHttpServer
 * 能够监听指定端口并处理请求
 *
 * @author Ethan
 * @version 1.0
 */
public class VertxHttpServer implements HttpServer {

    /**
     * 启动服务器
     *
     * @param port
     */
    @Override
    public void doStart(int port) {
        // 创建 Vert.x 实例
        Vertx vertx = Vertx.vertx();

        // 创建 HTTP 服务器
        io.vertx.core.http.HttpServer server = vertx.createHttpServer();

        // 监听端口并处理请求
        server.requestHandler(new HttpServerHandler());

        // 启动 HTTP 服务器并监听指定端口
        server.listen(port, result -> {
            if (result.succeeded()) {
                System.out.println("Server is now listening on port " + port);
            } else {
                System.err.println("Failed to start server: " + result.cause());
            }
        });
    }
}
