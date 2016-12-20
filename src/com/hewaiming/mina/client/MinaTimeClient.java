package com.hewaiming.mina.client;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MinaTimeClient {
    
    public static void main(String[] args){
        // �����ͻ���������.
        NioSocketConnector connector = new NioSocketConnector();
        connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.getFilterChain().addLast("codec", 
                new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
        
        // �������ӳ�ʱ���ʱ��
        connector.setConnectTimeoutCheckInterval(30);
        connector.setHandler(new TimeClientHandler());
        
        // ��������
        ConnectFuture cf = connector.connect(new InetSocketAddress("192.168.8.188", 6488));
        // �ȴ����Ӵ������
        cf.awaitUninterruptibly();
        
        cf.getSession().write("Hi Server!");
        cf.getSession().write("quit");
        
        // �ȴ����ӶϿ�
        cf.getSession().getCloseFuture().awaitUninterruptibly();
        // �ͷ�����
        connector.dispose();
    }
}