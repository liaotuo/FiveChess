package com.lt.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.lt.client.Client;

public class ChatPanel extends JPanel {
	private Dimension size = new Dimension(615, 200);
	private static JTextArea jTextArea;
	private JTextField jTextField;
	private JPanel southPanel;
	private JButton jButton;
	private JScrollPane jScrollPane;
	private JComboBox<String> fastMessage;

	public ChatPanel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		initFastMessage(); //初始化快捷消息
		southPanel = new JPanel();
		this.setPreferredSize(size);

		this.setLayout(new BorderLayout());
		southPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 4, 4));

		// 显示聊天消息
		jTextArea = new JTextArea(10, 20);
		jScrollPane = new JScrollPane(jTextArea);

		// 聊天消息输入框
		jTextField = new JTextField(60);
		jButton = new JButton("发送");

		jButton.addActionListener(new sendAction());
		fastMessage.addActionListener(new fastMessageAction());

		southPanel.add(jTextField);
		southPanel.add(jButton);
		southPanel.add(fastMessage);
		this.add(jScrollPane, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
	}

	public static JTextArea getjTextArea() {
		return jTextArea;
	}
	
	private void initFastMessage() {
		String[] msgs = {"点击发送快捷消息","你好，很高兴认识你!","和你下棋真愉快!","不要走，决战到天亮!","不好意思，我要离开一会!","你的棋下得挺好的啊!"};
		fastMessage = new JComboBox<String>(msgs);
	}
	
	//发送消息监听
	private class sendAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String text = jTextField.getText();

			// 发送消息
			Client.getClient().senData("3-" + text);
			jTextArea.append("我   : " + text + "\n");
			jTextField.setText(""); // 清空
		}
	}
	private class fastMessageAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String text = fastMessage.getSelectedItem().toString();
			// 发送消息
			Client.getClient().senData("3-" + text);
			jTextArea.append("我   : " + text + "\n");
		}
		
	}
}
