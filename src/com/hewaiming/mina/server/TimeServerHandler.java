package com.hewaiming.mina.server;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
/**
 * ��������ҵ���߼�
 */
public class TimeServerHandler implements IoHandler {

	@Override
	public void exceptionCaught(IoSession arg0, Throwable cause) throws Exception {
		 cause.printStackTrace();

	}

	@Override
	public void inputClosed(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub

	}
	 /**
     * ��Ϣ�����¼�
     */
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		 String strMsg = message.toString();
	        if (strMsg.trim().equalsIgnoreCase("quit")) {
	            session.close(true);
	            return;
	        }
	        // ������Ϣ�ַ���
	        session.write("Hi Client!");
	        // ��ӡ�ͻ��˴�������Ϣ����
	        System.out.println("Message written : " + strMsg);

	}

	@Override
	public void messageSent(IoSession arg0, Object arg1) throws Exception {
		

	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub

	}
	 /**
     * ���Ӵ����¼�
     */
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		   // ��ʾ�ͻ��˵�ip�Ͷ˿�
        System.out.println(session.getRemoteAddress().toString());
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		  System.out.println("IDLE" + session.getIdleCount(status));

	}

	@Override
	public void sessionOpened(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub

	}

}
