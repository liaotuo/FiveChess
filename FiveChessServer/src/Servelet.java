import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Servelet {
	private final static Logger log = LoggerFactory
			.getLogger(Servelet.class);
	private static int BUFFSIZE = 2048;
    private static int PORT = 7080; //�˿ں�
    private static IoAcceptor acceptor = null;
    
    public static void main(String[] args) throws IOException {
		acceptor = new NioSocketAcceptor();
		//���ñ��������
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(
                new TextLineCodecFactory(Charset.forName("UTF-8"),
                        LineDelimiter.WINDOWS.getValue(),
                        LineDelimiter.WINDOWS.getValue())));
		//���ö���������С
		acceptor.getSessionConfig().setReadBufferSize(BUFFSIZE);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE,10);
		
		acceptor.setHandler(new MyHandle());
		//�󶨶˿ں�
		acceptor.bind(new InetSocketAddress(PORT));
		log.info("Server start at prot: "+PORT);
		
	}
    
    
}
