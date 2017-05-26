package com.lt.client;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class Client {
	private static Client client = new Client();
	private final static Logger log = LoggerFactory
			.getLogger(Client.class);
	private static int BUFFSIZE = 2048;
	private static final String HOST = "119.29.190.111";  //119.29.190.111   127.0.0.1
	private static final int PORT = 7080;
	IoSession session;
	ConnectFuture connectFuture;
	
	public static Client getClient() {
		return client;
	}
	private Client() {
		
	}
	public void openConnect() {
		IoConnector connector = new NioSocketConnector();
		//设置编码过滤器
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(
						new TextLineCodecFactory(Charset.forName("UTF-8"),
								LineDelimiter.WINDOWS.getValue(),
								LineDelimiter.WINDOWS.getValue())));
		//设置读缓冲区大小
		connector.getSessionConfig().setReadBufferSize(BUFFSIZE);
		//设置进入空闲状态的时间 10毫秒
		connector.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		connector.setHandler(new MyHandler());
		connectFuture = connector.connect(new InetSocketAddress(HOST,PORT));
		connectFuture.addListener(new IoFutureListener<ConnectFuture>() {
			//操作完成
			@Override
			public void operationComplete(ConnectFuture futrue) {
				if(futrue.isConnected()){
					session = futrue.getSession();
				}
				
			}
			
		});
		log.info("Content to "+ HOST + " : " + PORT);
	}
	//发送数据
	public void senData(String msg) {

		System.out.println(session);
		session.write(msg);
		log.info("senData: " + msg);
	}
}
