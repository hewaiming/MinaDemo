package com.hewaiming.message;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * <b>function:</b> 服务器启动类
 * @author hoojo
 * @createDate 2012-6-29 下午07:11:00
 * @file MinaServer.java
 * @package com.hoo.mina.server
 * @project ApacheMiNa
 * @blog http://blog.csdn.net/IBM_hoojo
 * @email hoojo_@126.com
 * @version 1.0
 */
public class MinaServer {
    
    private SocketAcceptor acceptor;
    
    public MinaServer() {
        // 创建非阻塞的server端的Socket连接
        acceptor = new NioSocketAcceptor();
    }
    
    public boolean start() {
        DefaultIoFilterChainBuilder filterChain = acceptor.getFilterChain();
        // 添加编码过滤器 处理乱码、编码问题
        filterChain.addLast("codec", new ProtocolCodecFilter(new CharsetCodecFactory()));
        
        /*LoggingFilter loggingFilter = new LoggingFilter();
        loggingFilter.setMessageReceivedLogLevel(LogLevel.INFO);
        loggingFilter.setMessageSentLogLevel(LogLevel.INFO);
        // 添加日志过滤器
        filterChain.addLast("loger", loggingFilter);*/
        
        // 设置核心消息业务处理器
        acceptor.setHandler(new ServerMessageHandler());
        // 设置session配置，30秒内无操作进入空闲状态
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 30);
        
        try {
            // 绑定端口3456
            acceptor.bind(new InetSocketAddress(3456));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public static void main(String[] args) {
        MinaServer server = new MinaServer();
        server.start();
    }
}