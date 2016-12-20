package com.hewaiming.message;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <b>function:</b> �ͻ�����Ϣ������
 * @author hoojo
 * @createDate 2012-6-29 ����07:24:22
 * @file ClientMessageHandlerAdapter.java
 * @package com.hoo.mina.client.message
 * @project ApacheMiNa
 * @blog http://blog.csdn.net/IBM_hoojo
 * @email hoojo_@126.com
 * @version 1.0
 */
public class ClientMessageHandlerAdapter extends IoHandlerAdapter {
 
    private final static Logger log = LoggerFactory.getLogger(ClientMessageHandlerAdapter.class);
    
    public void messageReceived(IoSession session, Object message) throws Exception {
        String content = message.toString();
        log.info("client receive a message is : " + content);
    }
    
    public void messageSent(IoSession session , Object message) throws Exception{
        log.info("messageSent �ͻ��˷�����Ϣ��" + message);
    }
    
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        log.info("�����������쳣�� {}", cause.getMessage());
    }
}