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
//	//����з�̹��
//	List<Tank> tanks = new ArrayList<>();
//	List<Explode> explodes = new ArrayList<>();
//	Explode e = new Explode(100, 100, this);
	//ʹ���������ģʽ
//	GameModel gm = new GameModel();
	GameModel gm = GameModel.getInstance();
	
	
	static final int GAME_WIDTH = 1080, GAME_HEIGHT = 720;
	
	public TankFrame(){
		setSize(GAME_WIDTH,GAME_HEIGHT);//���ô��ڴ�С
		setResizable(false);//�޷��ı䴰�ڴ�С
		setTitle("tank war");//���ô�������
		setVisible(true);//��ʾ�����Ĵ�����
		
		//�����¼�����
		this.addKeyListener(new MyKeyListener());//���������¼�
		
		addWindowListener(new WindowAdapter(){//���window������
			@Override//����windowClosing���������������˳���
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
	}
	//������˸����
	//���ڴ�����ݸ��Ƶ��Դ��ϡ�
	//repaint����������ִ��update������
	Image offScreenImage = null;//����һ��ͼƬ
	@Override
	public void update(Graphics g){
		if(offScreenImage == null){
			//����ͼƬ��С
			offScreenImage = this.createImage(GAME_WIDTH,GAME_HEIGHT);
		}
		//�õ�ͼƬ�Ļ��ʣ���������һ��
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.BLACK);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);//�����ͼƬ�Ļ��ʴ���paint�У�����̹���ӵ������ڸ�ͼƬ��
		//ϵͳ���ʽ���������ϡ�
		g.drawImage(offScreenImage,0,0,null);
		
	}
	@Override
	public void paint(Graphics g){//�������µ��û����ʱ����ø÷���
		gm.paint(g);
		
//		Color c = g.getColor();
//		g.setColor(Color.WHITE);
//		g.drawString("�ӵ�������" + bullets.size(), 10, 60);
//		g.drawString("���˵�����" + tanks.size(), 10, 80);
//		g.drawString("��ը������" + explodes.size(), 10, 100);
//		g.setColor(c);
//		
//		myTank.paint(g);
//		//b.paint(g);
////		for(Bullet b : bullets){���ø÷�������Ϊ�����˵�����������ʱ�ⲿ���ܶԼ��Ͻ���ɾ�����������Ըñ������С�
////			b.paint(g);
////		}
//		for(int i = 0; i < bullets.size(); i++){
//			bullets.get(i).paint(g);
//		}
//		//��̹��
//		for(int i = 0; i < tanks.size(); i++){
//			tanks.get(i).paint(g);
//		}
//		
//		//����ը
//		for(int i = 0; i < explodes.size(); i++){
//			explodes.get(i).paint(g);
//		}
//		
//		//�ж��ӵ���̹���Ƿ���ײ
//		for(int i = 0; i < bullets.size(); i++){
//			for(int j = 0; j < tanks.size(); j++){
//				bullets.get(i).collideWith(tanks.get(j));
//			}
//		}
//		
//		if(tanks.size() == 0){
//			c = g.getColor();
//			g.setColor(Color.RED);
//			g.drawString("��Ӯ��", GAME_WIDTH / 2, GAME_HEIGHT / 6);
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
		public void keyPressed(KeyEvent e) {//e���¼�
			//���ڼ�����ʱ����
			int key = e.getKeyCode();//��ñ����¼��Ĵ���
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
//			repaint();//��Ĭ�ϵ���paint������ֱ�ӵ�������Ϊ�޷�ֱ�ӵõ�ϵͳ�Ļ��ʡ�
		}

		@Override
		public void keyReleased(KeyEvent e) {
			//���ڼ��ͷ�ʱ����
			int key = e.getKeyCode();//��ñ����¼��Ĵ���
			switch(key){
			case KeyEvent.VK_LEFT://��������ļ�
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
				
			case KeyEvent.VK_CONTROL://�ɿ�ctrl�������ӵ���
				//myTank.fire();
				gm.getMainTank().fire();
			default:
				break;
			}
			setMainTankDir();
		}

		private void setMainTankDir() {//�����¼�������̹�˵ķ���
			// TODO Auto-generated method stub
			//��myTank���ɴ�gm�л�ȡ
			Tank myTank = gm.getMainTank();
			if(!bL && !bR && !bU && !bD) //��̹��ֹͣ�����
				myTank.setMoving(false);
			else {
				myTank.setMoving(true);//���¼�������̹���ƶ�
				if(bL) myTank.setDir(Dir.LEFT);
				if(bR) myTank.setDir(Dir.RIGHT);
				if(bU) myTank.setDir(Dir.UP);
				if(bD) myTank.setDir(Dir.DOWN);
			
			}
		}
		
	}
}













