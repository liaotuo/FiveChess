package com.lt.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.lt.client.Client;

public class RightPanel extends JPanel {
	ImageIcon background = new ImageIcon(getClass().getResource("/img/右边背景.jpg"));
	Color baColor = new Color(251, 225, 164); 
	JButton huiqi = new JButton("我要悔棋");
	JButton peace = new JButton("我要和棋");
	JButton reStart = new JButton("重新开始");
	JButton closeMusic = new JButton("关闭音效");
	JButton exit = new JButton("退出游戏");
	JLabel label1 = new JLabel("");
	public RightPanel() {
		setPreferredSize(new Dimension(140, 0));
		setLayout(new FlowLayout(FlowLayout.CENTER, 40, 40));
		huiqi.setBackground(baColor);
		peace.setBackground(baColor);
		reStart.setBackground(baColor);

		exit.setBackground(baColor);
		closeMusic.setBackground(baColor);
		
		add(huiqi);
		huiqi.addActionListener(new huiqiAction());
		add(peace);
		peace.addActionListener(new peacAction());
		add(reStart);
		reStart.addActionListener(new reStartAction());

		add(closeMusic);
		closeMusic.addActionListener(new closeMusicAction());
		add(exit);
		exit.addActionListener(new exitAction());
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background.getImage(), 0, 0,140,489, null);
	}
	
	//和棋操作
	public void doPeace() {
		JOptionPane.showMessageDialog(null, "双方和棋!");
		LeftPanel.blackList.clear();
		LeftPanel.whiteList.clear();
		LeftPanel.stoneFlag = true;
		getParent().repaint();
	}

	//重新开始
	public void reStart() {
		LeftPanel.blackList.clear();
		LeftPanel.whiteList.clear();
		LeftPanel.stoneFlag = true;
		getParent().repaint();
	}

	//悔棋
	public void doHuiqi() {
		ArrayList<Point> points;
		//如果为真 points = LeftPanel.whiteList 否则 = LeftPanel.blackList
		points = LeftPanel.stoneFlag ? LeftPanel.whiteList : LeftPanel.blackList;
		if(points.size() > 0){points.remove(points.remove(points.size()-1));}
		getParent().repaint();
		LeftPanel.stoneFlag = !LeftPanel.stoneFlag;
	}


	/***
	 * 关闭音效按钮监听
	 */
	private class closeMusicAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(LeftPanel.music){
				LeftPanel.music = false;
				closeMusic.setText("开启音效");
			}else
			{
				LeftPanel.music = true;
				closeMusic.setText("关闭音效");
			}

		}
	}
	/***
	 * 悔棋按钮监听
	 * @author lt
	 *
	 */
	private class huiqiAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Client.getClient().senData("2"); //发送悔棋指令
		}
	}
	/***
	 * 退出按钮监听
	 * @author lt
	 *
	 */
	private class exitAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
	/***
	 * 重新开始监听
	 * @author lt
	 *
	 */
	private class reStartAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Client.getClient().senData("5"); //发送重新开始指令
		}
	}
	/***
	 * 和棋按钮监听
	 * @author lt
	 *
	 */
	private class peacAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Client.getClient().senData("4"); //发送和棋指令
		}
	}
}
