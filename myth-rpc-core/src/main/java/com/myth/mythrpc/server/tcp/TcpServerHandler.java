package com.myth.mythrpc.server.tcp;

import com.myth.mythrpc.model.RpcRequest;
import com.myth.mythrpc.model.RpcResponse;
import com.myth.mythrpc.protocol.ProtocolMessage;
import com.myth.mythrpc.protocol.ProtocolMessageDecoder;
import com.myth.mythrpc.protocol.ProtocolMessageEncoder;
import com.myth.mythrpc.protocol.ProtocolMessageTypeEnum;
import com.myth.mythrpc.registry.LocalRegistry;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * TCP 请求处理器(服务提供者)
 *
 * @author Ethan
 * @version 1.0
 */
public class TcpServerHandler implements Handler<NetSocket> {

    @Override
    public void handle(NetSocket netSocket) {

        // 1.服务端在接收到客户端的连接请求后，为每个连接的NetSocket实例设置一个处理器（handler）
        TcpBufferHandlerWrapper bufferHandlerWrapper = new TcpBufferHandlerWrapper(buffer -> {
            // 处理请求代码
            ProtocolMessage<RpcRequest> protocolMessage;
            try {
                // 3.使用 ProtocolMessageDecoder.decode 方法将接收到的字节流（Buffer）解码成 RPC 请求的协议消息 ProtocolMessage<RpcRequest>
                protocolMessage = (ProtocolMessage<RpcRequest>) ProtocolMessageDecoder.decode(buffer);
            } catch (IOException e) {
                throw new RuntimeException("协议消息解码错误");
            }

            // 4. 根据解码后的请求信息，通过反射找到对应的服务实现类和方法，执行方法调用，并构造 RPC 响应结果 RpcResponse
            RpcRequest rpcRequest = protocolMessage.getBody();
            RpcResponse rpcResponse = new RpcResponse();
            try {
                // 获取要调用的服务实现类，通过反射调用
                Class<?> implClass = LocalRegistry.get(rpcRequest.getServiceName());
                Method method = implClass.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
                Object result = method.invoke(implClass.newInstance(), rpcRequest.getArgs());
                // 封装返回结果
                rpcResponse.setData(result);
                rpcResponse.setDataType(method.getReturnType());
                rpcResponse.setMessage("ok");
            } catch (Exception e) {
                e.printStackTrace();
                rpcResponse.setMessage(e.getMessage());
                rpcResponse.setException(e);
            }

            // 5.将RPC响应结果封装到协议消息ProtocolMessage<RpcResponse>中，并通过ProtocolMessageEncoder.encode方法编码为字节流
            ProtocolMessage.Header header = protocolMessage.getHeader();
            header.setType((byte) ProtocolMessageTypeEnum.RESPONSE.getKey());
            ProtocolMessage<RpcResponse> responseProtocolMessage = new ProtocolMessage<>(header, rpcResponse);
            try {
                Buffer encode = ProtocolMessageEncoder.encode(responseProtocolMessage);
                netSocket.write(encode);
            } catch (IOException e) {
                throw new RuntimeException("协议消息编码错误");
            }
        });
        netSocket.handler(bufferHandlerWrapper);
    }
}
