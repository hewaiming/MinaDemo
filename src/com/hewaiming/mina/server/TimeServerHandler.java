package com.hewaiming.mina.server;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
/**
 * 服务器端业务逻辑
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
     * 消息接收事件
     */
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		 String strMsg = message.toString();
	        if (strMsg.trim().equalsIgnoreCase("quit")) {
	            session.close(true);
	            return;
	        }
	        // 返回消息字符串
	        session.write("Hi Client!");
	        // 打印客户端传来的消息内容
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
     * 连接创建事件
     */
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		   // 显示客户端的ip和端口
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
