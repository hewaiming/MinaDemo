package com.hewaiming.message;

import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

/**
 * <b>function:</b> mina客户端
 * @author hoojo
 * @createDate 2012-6-29 下午07:28:45
 * @file MinaClient.java
 * @package com.hoo.mina.client.message
 * @project ApacheMiNa
 * @blog http://blog.csdn.net/IBM_hoojo
 * @email hoojo_@126.com
 * @version 1.0
 */
public class MinaClient {
 
    private SocketConnector connector;
    private ConnectFuture future;
    private IoSession session;
 
    public boolean connect() {
 
        // 创建一个socket连接
        connector = new NioSocketConnector();
        // 设置链接超时时间
        connector.setConnectTimeoutMillis(3000);
        // 获取过滤器链
        DefaultIoFilterChainBuilder filterChain = connector.getFilterChain();
        // 添加编码过滤器 处理乱码、编码问题
        filterChain.addLast("codec", new ProtocolCodecFilter(new CharsetCodecFactory()));
 
        /*
        // 日志
        LoggingFilter loggingFilter = new LoggingFilter();
        loggingFilter.setMessageReceivedLogLevel(LogLevel.INFO);
        loggingFilter.setMessageSentLogLevel(LogLevel.INFO);
        filterChain.addLast("loger", loggingFilter);*/
 
        // 消息核心处理器
        connector.setHandler(new ClientMessageHandlerAdapter());
 
        // 连接服务器，知道端口、地址
        future = connector.connect(new InetSocketAddress("192.168.8.188",3456));
        // 等待连接创建完成
        future.awaitUninterruptibly();
        // 获取当前session
        session = future.getSession();
        return true;
    }
 
    public void setAttribute(Object key, Object value) {
        session.setAttribute(key, value);
    }
 
    public void send(String message) {
        session.write(message);
    }
 
    public boolean close() {
        CloseFuture future = session.getCloseFuture();
        future.awaitUninterruptibly(1000);
        connector.dispose();
        return true;
    }
 
    public SocketConnector getConnector() {
        return connector;
    }
 
    public IoSession getSession() {
        return session;
    }
}