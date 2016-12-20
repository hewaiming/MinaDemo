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
 * <b>function:</b> mina�ͻ���
 * @author hoojo
 * @createDate 2012-6-29 ����07:28:45
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
 
        // ����һ��socket����
        connector = new NioSocketConnector();
        // �������ӳ�ʱʱ��
        connector.setConnectTimeoutMillis(3000);
        // ��ȡ��������
        DefaultIoFilterChainBuilder filterChain = connector.getFilterChain();
        // ��ӱ�������� �������롢��������
        filterChain.addLast("codec", new ProtocolCodecFilter(new CharsetCodecFactory()));
 
        /*
        // ��־
        LoggingFilter loggingFilter = new LoggingFilter();
        loggingFilter.setMessageReceivedLogLevel(LogLevel.INFO);
        loggingFilter.setMessageSentLogLevel(LogLevel.INFO);
        filterChain.addLast("loger", loggingFilter);*/
 
        // ��Ϣ���Ĵ�����
        connector.setHandler(new ClientMessageHandlerAdapter());
 
        // ���ӷ�������֪���˿ڡ���ַ
        future = connector.connect(new InetSocketAddress("192.168.8.188",3456));
        // �ȴ����Ӵ������
        future.awaitUninterruptibly();
        // ��ȡ��ǰsession
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