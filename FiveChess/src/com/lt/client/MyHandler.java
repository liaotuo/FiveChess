package com.lt.client;

import java.awt.Point;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lt.view.MainFrame;

public class MyHandler implements IoHandler {
	private final static Logger log = LoggerFactory
			.getLogger(MyHandler.class);
	private Point chess;  //棋子
	
	

	public Point getChess() {
		return chess;
	}

	public void setChess(Point chess) {
		this.chess = chess;
	}

	@Override
	public void exceptionCaught(IoSession arg0, Throwable arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputClosed(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		String msg = (String) message;
		String cmd = msg.split("-",2)[0];
		
		switch (cmd) {
		//0 ,1是棋子消息
		case "0":
		case "1":
			chess = Tools.stringToPoint(msg);
			log.info("messageReceived: " + chess);
			MainFrame.getLeftPanel().getChess(cmd,chess);
			break;
		case "2":  //悔棋请求消息
			int n = JOptionPane.showConfirmDialog(null, "对方请求悔棋，是否同意?", "消息",JOptionPane.YES_NO_OPTION);
			if(n == 0) {
				MainFrame.getRightPanel().doHuiqi();
				Client.getClient().senData("6");  //发送同意悔棋指令
			}else {
				Client.getClient().senData("9");  //发送拒绝悔棋请求
			}
			break;
		case "3":  //聊天消息
			log.info("messageReceived: " + message);
			JTextArea jArea = MainFrame.getChatPanel().getjTextArea();
			String chatString = "对方 : " + msg.split("-", 2)[1] + "\n";
			jArea.append(chatString);
			jArea.setCaretPosition(jArea.getText().length());
			break;
		case "4":  //和棋请求消息
			int m = JOptionPane.showConfirmDialog(null, "对方请求和棋，是否同意?", "消息",JOptionPane.YES_NO_OPTION);
			if(m == 0) {
				MainFrame.getRightPanel().doPeace();
				Client.getClient().senData("7");  //发送同意和棋指令
			}else {
				Client.getClient().senData("10");  //发送拒绝和棋请求
			}
			break;
		case "5": //重新开始请求消息
			int m1 = JOptionPane.showConfirmDialog(null, "对方请求重新开始，是否同意?", "消息",JOptionPane.YES_NO_OPTION);
			if(m1 == 0) {
				MainFrame.getRightPanel().doPeace();
				Client.getClient().senData("8");  //发送同意重新开始指令
			}else {
				Client.getClient().senData("11");  //发送拒绝重新开始请求
			}
			break;
		case "6":  //同意悔棋
			MainFrame.getRightPanel().doHuiqi();
			break;
		case "7":  //同意和棋
			MainFrame.getRightPanel().doPeace();
			break;
		case "8":  //同意重新开始
			MainFrame.getRightPanel().reStart();
			break;
		case "9": //拒绝悔棋指令
			JOptionPane.showMessageDialog(null, "对方拒绝你的悔棋请求");
			break;
		case "10": //拒绝和棋
			JOptionPane.showMessageDialog(null, "对方拒绝你的和棋请求");
			break;
		case "11":  //拒绝重新开始
			JOptionPane.showMessageDialog(null, "对方拒绝你的重新开始请求");
			break;
		default:
			break;
		}
			
	}

	@Override
	public void messageSent(IoSession arg0, Object arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionClosed(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionCreated(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub

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
