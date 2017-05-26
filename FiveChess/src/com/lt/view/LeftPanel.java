package com.lt.view;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.lt.client.Client;
import com.lt.client.Tools;
import com.lt.music.AudioPlayer;

public class LeftPanel extends JPanel {
	static boolean music = true;
	Point P3 = new Point(0, 0);  //指向提示坐标点
	// 用来进行悔棋
	boolean huiqi = true;
	//判断是否可以下棋
	static boolean canDo = true;
	//如果为真下黑棋  如果为假下白棋
	static boolean stoneFlag = true;
	// 获取棋子图片
	ImageIcon backGround = new ImageIcon(getClass().getResource("/img/五子棋棋盘.jpg"));
	ImageIcon iiWhite = new ImageIcon(getClass().getResource("/img/stone_w2.png"));
	ImageIcon iiBlack = new ImageIcon(getClass().getResource("/img/stone_b1.png"));
	// 棋子链表，用来存放棋子坐标
	static ArrayList<Point> whiteList = new ArrayList<Point>();
	static ArrayList<Point> blackList = new ArrayList<Point>();
	Dimension size = new Dimension(480, 460);
	Graphics2D g1;

	/***
	 * 构造方法
	 */
	public LeftPanel() {
		setPreferredSize(size);
		// 添加鼠标监听
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				super.mouseMoved(e);
				 P3.x = e.getX();
				 P3.y = e.getY();
				 repaint();
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if(canDo == true) {
				Point p1; // 存放鼠标点击位置点
				p1 = e.getPoint();
				playChess(p1);
				}else{
					JOptionPane.showMessageDialog(null, "请等待对方落子");
				}
			}
		});
	}
	//获取收到的棋子
	public void getChess(String color, Point point) {
		if(color.equals("1")){
			whiteList.add(point);
			stoneFlag = true;
			this.getParent().repaint();
			//判断输赢
			whiteWin(point);
		}
		else if(color.equals("0")){
			blackList.add(point);
			stoneFlag = false;
			this.getParent().repaint();
			//判断输赢
			blackWin(point);
		}
		canDo = true;
	}
	// 重写paint方法 每次下棋子都重绘
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g1 = (Graphics2D) g;
		g1.setStroke(new BasicStroke(2));
		boolean flag = true;
		//画背景图
		g1.drawImage(backGround.getImage(), 0, 0, size.width-20, size.height, null);
		// 画棋格
		for (int i = 1; i < 20; i++) {
			g.drawLine(23 * i, 23, 23 * i, 437);
			g.drawLine(23, 23 * i, 437, 23 * i);
		}
		//画提示
		drawTip(P3, g1);
		// 画棋子
		for (Point p1 : blackList) {
			g1.drawImage(iiBlack.getImage(), p1.x - 10, p1.y - 10, 20, 20, null);
			flag = !flag;
		}
		for (Point p1 : whiteList) {
			g1.drawImage(iiWhite.getImage(), p1.x - 10, p1.y - 10, 20, 20, null);
			flag = !flag;
		}
		huiqi = flag;
	}

	/***
	 * 判断棋子精确落点位置 retrun 返回一个点
	 */
	private Point check(Point p) {
		// 用来返回的点
		Point rePoint = new Point(0, 0);
		// 如果点击超过 格子的一半 就加你
		rePoint.x = p.x % 23 >= 11 ? (p.x / 23) + 1 : (p.x / 23);
		// 如果点击超过 格子的一半 就加你
		rePoint.y = p.y % 23 >= 11 ? (p.y / 23) + 1 : (p.y / 23);
		// 获取具体的像素点
		rePoint.x *= 23;
		rePoint.y *= 23;
		return rePoint;
	}
	/***
	 * 判断输赢方法
	 * @param point 中心点
	 * @param stone 棋子列表
	 * @return 输赢
	 */
	private boolean determineWinners(Point point, ArrayList<Point> stone){
		Point originalPoint = point; //保存原始的点
		Point p2 = new Point(point.x, point.y);
		int count = 1; //计数器，用来标识是否五子连珠
		//数组保存中心点的   8个方位的坐标点                                 左		右 		上		下		左上		右下		左下		右上
		int[][] direction = new int[][]{{-23,0},{23,0},{0,-23},{0,23},{-23,-23},{23,23},{-23,23},{23,-23}};
		for(int i=0; i<8; i++)
		{
			//将点初始化为中心点
			p2.x = originalPoint.x;
			p2.y = originalPoint.y;
			// 循环两次走完一条线
			if(i % 2 == 0)
			{
				count = 1;
			}
				//坐标点x,y分别加上相应的数值
				p2.x += direction[i][0];
				p2.y += direction[i][1];
				//如果包含该点 继续往原来的方向继续搜索
				while(stone.contains(p2)) {
					count++;
					//坐标点x,y分别加上相应的数值
					p2.x += direction[i][0];
					p2.y += direction[i][1];
				}
				//五子连珠则胜利
			if(count >= 5) {
				return true;
			}
		}
		return false;
	}
	/***
	 * 画提示线
	 * @param p3 当前为其棋盘的坐标
	 */
	private void drawTip(Point p3, Graphics g1) {
		p3 = check(P3);
		int length = 5; //直线的长度
		int dist = 3;  //距离棋盘网线的距离
		//控制8条直线
		int[][] disting = new int[][]{{-dist,-dist,-dist,-dist-length},{-dist,-dist,-dist-length,-dist},
									{dist,dist,dist,dist+length},{dist,dist,dist+length,dist},
									{-dist,dist,-dist-length,dist},{-dist,dist,-dist,dist+length},
									{dist,-dist,dist,-dist-length},{dist,-dist,dist+length,-dist}};
		for(int i=0; i<8; i++){
			Point p1 = new Point(p3.x, p3.y);
			Point p2 = new Point(p3.x, p3.y);
			p1.x += disting[i][0];
			p1.y += disting[i][1];
			p2.x += disting[i][2];
			p2.y += disting[i][3];
			g1.drawLine(p1.x, p1.y, p2.x, p2.y);
		}
	}
	private void playChess(Point p1) {
		if (p1.x < 23 || p1.y < 23 || p1.x > 437 || p1.y > 437) {
		} else {
			// 规范化坐标位置
			p1 = check(p1);
			// 如果为真就画黑棋
			if (stoneFlag) {
				// 如果此列表中不包含该坐标点，将棋子坐标加入到链表中
				if (!blackList.contains(p1) && !whiteList.contains(p1)) {
					blackList.add(p1);
					//把棋子发送过去
					Client.getClient().senData(Tools.pointToString(0,p1));
					canDo = false;  //设置为不能下棋
					if (music) {
						// 落子音效
						InputStream file1 = getClass().getResourceAsStream("/music/1.mp3");
						AudioPlayer audioPlayer = new AudioPlayer(file1);
						audioPlayer.start();
					}

					// 重绘
					repaint();
					//判断黑方是否赢
					blackWin(p1);
				}
			} else {
				// 如果此列表中不包含该坐标点，将棋子坐标加入到链表中
				if (!blackList.contains(p1) && !whiteList.contains(p1)) {
					whiteList.add(p1);
					
					//把棋子发送过去
					Client.getClient().senData(Tools.pointToString(1,p1));
					canDo = false;
					stoneFlag = !stoneFlag;
					if (music) {
						// 落子音效
						InputStream file1 = getClass().getResourceAsStream("/music/1.mp3");
						AudioPlayer audioPlayer = new AudioPlayer(file1);
						audioPlayer.start();
					}
					// 重绘
					repaint();
					whiteWin(p1);
				}
			}
		}
	}
	private void whiteWin(Point p1) {
		if (determineWinners(p1, whiteList) == true) {
			if (music) {
				// 胜利音效
				InputStream file2 = getClass().getResourceAsStream("/music/whiteWin.mp3");
				AudioPlayer audioPlayer2 = new AudioPlayer(file2);
				audioPlayer2.start();
			}
			JOptionPane.showMessageDialog(null, "白方获胜！");
			blackList.clear();
			whiteList.clear();
			// 重绘
			repaint();

		}
	}
	private void blackWin(Point p1) {
		if (determineWinners(p1, blackList) == true) {
			if (music) {
				// 胜利音效
				InputStream file3 = getClass().getResourceAsStream("/music/blackWin.mp3");
				AudioPlayer audioPlayer3 = new AudioPlayer(file3);
				audioPlayer3.start();
			}
			JOptionPane.showMessageDialog(null, "黑方获胜！");
			blackList.clear();
			whiteList.clear();
			// 重绘
			repaint();

		}
	}
}
