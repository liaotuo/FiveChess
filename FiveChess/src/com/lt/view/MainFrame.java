package com.lt.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.InputStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.lt.client.Client;
import com.lt.music.AudioPlayer;

/***
 * 五子棋主界面
 * @author lt
 *
 */
public class MainFrame extends JFrame{

	Dimension mainFrameSize = new Dimension(615,700);
	static LeftPanel  leftPanel;
	static ChatPanel chatPanel;
	static RightPanel rightPanel;
	
	//显示界面
	public MainFrame(){
		Client.getClient().openConnect();
		setTitle("五子棋-廖拓");
		setDefaultCloseOperation(3);
		setSize(mainFrameSize);
		setLocationRelativeTo(null);
		//增加左边
		addLeftPanel();
		//增加右边
		addRightPanel();
		//增加聊天面板
		addChatPanel();
		setVisible(true);
		InputStream file1 = getClass().getResourceAsStream("/music/backMusic.mp3");
		AudioPlayer audioPlayer = new AudioPlayer(file1 );
		audioPlayer.start();
	}
	//添加右边面板
	private void addRightPanel() {
		rightPanel = new RightPanel();
		this.add(rightPanel, BorderLayout.EAST);
		
	}
	//添加左边面板
	private void addLeftPanel() {
		leftPanel = new LeftPanel();
		this.add(leftPanel);
	}
	
	//添加聊天面板
	private void addChatPanel() {
		chatPanel = new ChatPanel();
		this.add(chatPanel,BorderLayout.SOUTH);
	}
	
	//获取左边面板
	public static LeftPanel getLeftPanel() {
		return leftPanel;
	}
	
	
	public static ChatPanel getChatPanel() {
		return chatPanel;
	}
	
	
	public static RightPanel getRightPanel() {
		return rightPanel;
	}
	public static void main(String[] args) {
		MainFrame mainFrame = new MainFrame();
	}
	

}
