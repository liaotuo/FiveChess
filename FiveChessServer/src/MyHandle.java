import java.util.Collection;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyHandle implements IoHandler {
	Collection<IoSession> allSession;
	private final static Logger log = LoggerFactory
			.getLogger(MyHandle.class);
	@Override
	public void exceptionCaught(IoSession arg0, Throwable arg1) throws Exception {
		log.debug("Servelet exception");

	}

	@Override
	public void inputClosed(IoSession arg0) throws Exception {
		
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		allSession = session.getService().getManagedSessions().values();
		for(IoSession se : allSession){
			if(se != session){
				se.write(message);
			}
		}

	}

	@Override
	public void messageSent(IoSession arg0, Object arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		log.info(session.getRemoteAddress() +"is exit");
		log.info("current online : "+ session.getService().getManagedSessionCount());
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		log.info(session.getRemoteAddress() +"is enter");
		log.info("current online : "+ session.getService().getManagedSessionCount());
	}

	@Override
	public void sessionIdle(IoSession arg0, IdleStatus arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionOpened(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub

	}

}
