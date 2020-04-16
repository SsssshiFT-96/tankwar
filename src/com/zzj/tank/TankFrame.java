package com.zzj.tank;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class TankFrame extends Frame{
//	Tank myTank = new Tank(400,400,Dir.UP, Group.GOOD, this);
//	List<Bullet> bullets = new ArrayList<>();
//	//Bullet b = new Bullet(300,300,Dir.DOWN);
//	//加入敌方坦克
//	List<Tank> tanks = new ArrayList<>();
//	List<Explode> explodes = new ArrayList<>();
//	Explode e = new Explode(100, 100, this);
	//使用门面设计模式
//	GameModel gm = new GameModel();
	GameModel gm = GameModel.getInstance();
	
	
	static final int GAME_WIDTH = 1080, GAME_HEIGHT = 720;
	
	public TankFrame(){
		setSize(GAME_WIDTH,GAME_HEIGHT);//设置窗口大小
		setResizable(false);//无法改变窗口大小
		setTitle("tank war");//设置窗口名称
		setVisible(true);//显示创建的窗口类
		
		//窗口事件监听
		this.addKeyListener(new MyKeyListener());//监听键盘事件
		
		addWindowListener(new WindowAdapter(){//添加window监听器
			@Override//监听windowClosing动作，监听到就退出。
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
	}
	//消除闪烁现象
	//将内存的内容复制到显存上。
	//repaint方法会优先执行update方法。
	Image offScreenImage = null;//定义一张图片
	@Override
	public void update(Graphics g){
		if(offScreenImage == null){
			//设置图片大小
			offScreenImage = this.createImage(GAME_WIDTH,GAME_HEIGHT);
		}
		//拿到图片的画笔，将背景画一遍
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.BLACK);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);//将这个图片的画笔传到paint中，这样坦克子弹均画在该图片上
		//系统画笔将这幅画画上。
		g.drawImage(offScreenImage,0,0,null);
		
	}
	@Override
	public void paint(Graphics g){//窗口重新调用或绘制时会调用该方法
		gm.paint(g);
		
//		Color c = g.getColor();
//		g.setColor(Color.WHITE);
//		g.drawString("子弹的数量" + bullets.size(), 10, 60);
//		g.drawString("敌人的数量" + tanks.size(), 10, 80);
//		g.drawString("爆炸的数量" + explodes.size(), 10, 100);
//		g.setColor(c);
//		
//		myTank.paint(g);
//		//b.paint(g);
////		for(Bullet b : bullets){若用该方法，因为是用了迭代器，迭代时外部不能对集合进行删除操作，所以该遍历不行。
////			b.paint(g);
////		}
//		for(int i = 0; i < bullets.size(); i++){
//			bullets.get(i).paint(g);
//		}
//		//画坦克
//		for(int i = 0; i < tanks.size(); i++){
//			tanks.get(i).paint(g);
//		}
//		
//		//画爆炸
//		for(int i = 0; i < explodes.size(); i++){
//			explodes.get(i).paint(g);
//		}
//		
//		//判断子弹和坦克是否相撞
//		for(int i = 0; i < bullets.size(); i++){
//			for(int j = 0; j < tanks.size(); j++){
//				bullets.get(i).collideWith(tanks.get(j));
//			}
//		}
//		
//		if(tanks.size() == 0){
//			c = g.getColor();
//			g.setColor(Color.RED);
//			g.drawString("你赢了", GAME_WIDTH / 2, GAME_HEIGHT / 6);
//			g.setColor(c);
//		}
//		
//
//		
////		x += 10;
////		y += 10;
	}
	class MyKeyListener extends KeyAdapter{
		boolean bL = false;
		boolean bR = false;
		boolean bU = false;
		boolean bD = false;
		@Override
		public void keyPressed(KeyEvent e) {//e是事件
			//会在键按下时调用
			int key = e.getKeyCode();//获得被按下键的代码
			switch(key){
			case KeyEvent.VK_LEFT:
				bL = true;
				break;
			case KeyEvent.VK_RIGHT:
				bR = true;
				break;
			case KeyEvent.VK_UP:
				bU = true;
				break;
			case KeyEvent.VK_DOWN:
				bD = true;
				break;
				
			default:
				break;
			}
			setMainTankDir();
//			x += 30;
//			repaint();//会默认调用paint，不能直接调用是因为无法直接得到系统的画笔。
		}

		@Override
		public void keyReleased(KeyEvent e) {
			//会在键释放时调用
			int key = e.getKeyCode();//获得被按下键的代码
			switch(key){
			case KeyEvent.VK_LEFT://按下向左的键
				bL = false;
				break;
			case KeyEvent.VK_RIGHT:
				bR = false;
				break;
			case KeyEvent.VK_UP:
				bU = false;
				break;
			case KeyEvent.VK_DOWN:
				bD = false;
				break;
				
			case KeyEvent.VK_CONTROL://松开ctrl键后发射子弹。
				//myTank.fire();
				gm.getMainTank().fire();
			default:
				break;
			}
			setMainTankDir();
		}

		private void setMainTankDir() {//设置事件发生后坦克的方向
			// TODO Auto-generated method stub
			//将myTank换成从gm中获取
			Tank myTank = gm.getMainTank();
			if(!bL && !bR && !bU && !bD) //让坦克停止的情况
				myTank.setMoving(false);
			else {
				myTank.setMoving(true);//按下键后设置坦克移动
				if(bL) myTank.setDir(Dir.LEFT);
				if(bR) myTank.setDir(Dir.RIGHT);
				if(bU) myTank.setDir(Dir.UP);
				if(bD) myTank.setDir(Dir.DOWN);
			
			}
		}
		
	}
}













