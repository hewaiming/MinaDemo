package com.hewaiming.mina.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaTimeServer {
	// ��������˿�
	private static final int PORT = 6488;

	public static void main(String[] args) {
		// ��������˼���߳�
		IoAcceptor acceptor = new NioSocketAcceptor();
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		// ������־��¼��
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		// ���ñ��������
		acceptor.getFilterChain().addLast("codec",
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
		// ָ��ҵ���߼�������
		acceptor.setHandler(new TimeServerHandler());
		try {
			// ���ö˿ں�
			acceptor.bind(new InetSocketAddress(PORT));
			// ���������߳�
			acceptor.bind();
		} catch (IOException e) {		
			e.printStackTrace();
		}

	}

}
