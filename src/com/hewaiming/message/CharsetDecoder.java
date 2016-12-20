package com.hewaiming.message;

import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * <b>function:</b> 字符解码
 * 
 * @author hoojo
 * @createDate 2012-6-26 上午11:14:18
 * @file CharsetDecoder.java
 * @package com.hoo.mina.code
 * @project ApacheMiNa
 * @blog http://blog.csdn.net/IBM_hoojo
 * @email hoojo_@126.com
 * @version 1.0
 */
public class CharsetDecoder implements ProtocolDecoder {

	private final static Logger log = Logger.getLogger(CharsetDecoder.class);

	private final static Charset charset = Charset.forName("UTF-8");
	// 可变的IoBuffer数据缓冲区
	private IoBuffer buff = IoBuffer.allocate(100).setAutoExpand(true);

	@Override
	public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		log.info("#########decode#########");

		// 如果有消息
		while (in.hasRemaining()) {
			// 判断消息是否是结束符，不同平台的结束符也不一样；
			// windows换行符（\r\n）就认为是一个完整消息的结束符了； UNIX 是\n；MAC 是\r
			byte b = in.get();
			if (b == '\n') {
				buff.flip();
				byte[] bytes = new byte[buff.limit()];
				buff.get(bytes);
				String message = new String(bytes, charset);

				buff = IoBuffer.allocate(100).setAutoExpand(true);

				// 如果结束了，就写入转码后的数据
				out.write(message);
				// log.info("message: " + message);
			} else {
				buff.put(b);
			}
		}
	}

	@Override
	public void dispose(IoSession session) throws Exception {
		log.info("#########dispose#########");
		log.info(session.getCurrentWriteMessage());
	}

	@Override
	public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {
		log.info("#########完成解码#########");
	}
}