package com.hewaiming.message;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * <b>function:</b> �������������Ϣ
 * @author hoojo
 * @createDate 2012-6-26 ����01:12:34
 * @file ServerMessageHandler.java
 * @package com.hoo.mina.server.message
 * @project ApacheMiNa
 * @blog http://blog.csdn.net/IBM_hoojo
 * @email hoojo_@126.com
 * @version 1.0
 */
public class ServerMessageHandler implements IoHandler {
    
    private final static Logger log = (Logger) LoggerFactory.getLogger(ServerMessageHandler.class);
    
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        log.info("�����������쳣�� {}", cause.getMessage());
    }
 
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        log.info("���������յ����ݣ� {}", message);
        String content = message.toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String datetime = sdf.format(new Date());
        
        log.info("ת�� messageReceived: " + datetime + "\t" + content);
        
        // �õ����еĿͻ���Session
        Collection<IoSession> sessions = session.getService().getManagedSessions().values();
        // �����пͻ��˷�������
        for (IoSession sess : sessions) {
            sess.write(datetime + "\t" + content);
        }
    }
 
    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        log.info("������������Ϣ�� {}", message);
    }
 
    @Override
    public void sessionClosed(IoSession session) throws Exception {
        log.info("�رյ�ǰsession��{}#{}", session.getId(), session.getRemoteAddress());
        
        CloseFuture closeFuture = session.close(true);
        closeFuture.addListener(new IoFutureListener<IoFuture>() {
            public void operationComplete(IoFuture future) {
                if (future instanceof CloseFuture) {
                    ((CloseFuture) future).setClosed();
                    log.info("sessionClosed CloseFuture setClosed-->{},", future.getSession().getId());
                }
            }
        });
    }
 
    @Override
    public void sessionCreated(IoSession session) throws Exception {
        log.info("����һ�������ӣ�{}", session.getRemoteAddress());
        session.write("welcome to the chat room !");
    }
 
    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        log.info("��ǰ����{}���ڿ���״̬��{}", session.getRemoteAddress(), status);
    }
 
    @Override
    public void sessionOpened(IoSession session) throws Exception {
        log.info("��һ��session��{}#{}", session.getId(), session.getBothIdleCount());
    }

	@Override
	public void inputClosed(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
}