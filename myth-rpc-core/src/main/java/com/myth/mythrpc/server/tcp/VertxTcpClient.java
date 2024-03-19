package com.myth.mythrpc.server.tcp;

import cn.hutool.core.util.IdUtil;
import com.myth.mythrpc.RpcApplication;
import com.myth.mythrpc.constant.ProtocolConstant;
import com.myth.mythrpc.model.RpcRequest;
import com.myth.mythrpc.model.RpcResponse;
import com.myth.mythrpc.model.ServiceMetaInfo;
import com.myth.mythrpc.protocol.*;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Vertx TCP 请求客户端
 *
 * @author Ethan
 * @version 1.0
 */
public class VertxTcpClient {

    /**
     * 发送 TCP 请求
     *
     * @param rpcRequest
     * @param serviceMetaInfo
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public static RpcResponse doRequest(RpcRequest rpcRequest, ServiceMetaInfo serviceMetaInfo) throws InterruptedException, ExecutionException {

        // 1. 创建 Vertx 实例和 NetClient 客户端，用于建立 TCP 连接
        Vertx vertx = Vertx.vertx();
        NetClient netClient = vertx.createNetClient();
        CompletableFuture<RpcResponse> responseFuture = new CompletableFuture<>();

        // 2.使用 netClient.connect 方法尝试连接到服务端。连接成功后，通过回调函数获取NetSocket实例，用于数据的发送和接收。
        netClient.connect(serviceMetaInfo.getServicePort(), serviceMetaInfo.getServiceHost(),
                result -> {
                    if (!result.succeeded()) {
                        System.err.println("Failed to connect to TCP server");
                        return;
                    }
                    NetSocket socket = result.result();
                    // 3. 构造 RPC 请求的协议消息 ProtocolMessage<RpcRequest>，包括请求头和请求体，并通过 ProtocolMessageEncoder.encode方法将其编码为字节流（Buffer）。
                    ProtocolMessage<RpcRequest> protocolMessage = new ProtocolMessage<>();
                    ProtocolMessage.Header header = new ProtocolMessage.Header();
                    header.setMagic(ProtocolConstant.PROTOCOL_MAGIC);
                    header.setVersion(ProtocolConstant.PROTOCOL_VERSION);
                    header.setSerializer((byte) ProtocolMessageSerializerEnum.getEnumByValue(RpcApplication.getRpcConfg().getSerializer()).getKey());
                    header.setType((byte) ProtocolMessageTypeEnum.REQUEST.getKey());
                    // 生成全局请求 ID
                    header.setRequestId(IdUtil.getSnowflakeNextId());
                    protocolMessage.setHeader(header);
                    protocolMessage.setBody(rpcRequest);
                    // 编码请求
                    try {
                        Buffer encodeBuffer = ProtocolMessageEncoder.encode(protocolMessage);
                        // 4. 通过 NetSocket 将编码后的请求发送给客户端
                        socket.write(encodeBuffer);
                    } catch (IOException e) {
                        throw new RuntimeException("协议消息编码错误");
                    }

                    // 5. 设置NetSocket的处理器（handler），以异步方式接收服务端的响应。当接收到数据时，使用ProtocolMessageDecoder.decode方法解码服务端的响应，并将解码后的响应结果设置到CompletableFuture<RpcResponse>中。
                    TcpBufferHandlerWrapper bufferHandlerWrapper = new TcpBufferHandlerWrapper(
                            buffer -> {
                                try {
                                    ProtocolMessage<RpcResponse> rpcResponseProtocolMessage =
                                            (ProtocolMessage<RpcResponse>) ProtocolMessageDecoder.decode(buffer);
                                    responseFuture.complete(rpcResponseProtocolMessage.getBody());
                                } catch (IOException e) {
                                    throw new RuntimeException("协议消息解码错误");
                                }
                            }
                    );
                    socket.handler(bufferHandlerWrapper);
                });
        // 6. 通过CompletableFuture.get方法同步等待并获取RPC响应结果。
        RpcResponse rpcResponse = responseFuture.get();
        // 7. 关闭 NetClient 客户端
        netClient.close();
        return rpcResponse;
    }
}
