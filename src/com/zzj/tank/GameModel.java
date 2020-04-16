package com.zzj.tank;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.zzj.tank.cor.BulletTankCollider;
import com.zzj.tank.cor.Collider;
import com.zzj.tank.cor.ColliderChain;
import com.zzj.tank.cor.TankTankCollider;

public class GameModel {
	private static final GameModel INSTANCE = new GameModel();
	//���˵�����new��̹����Ҫ�ȶ�INSTANCE��ʼ��,�޸���һ��Сbug����P143��Ƶ4min��
	static{
		INSTANCE.init();
	}
	Tank myTank; /*= new Tank(400,400,Dir.UP, Group.GOOD, this);*/
//	List<Bullet> bullets = new ArrayList<>();
	//����з�̹��
//	List<Tank> tanks = new ArrayList<>();
//	List<Explode> explodes = new ArrayList<>();
	//����Ϸ�����ӽ�����ļ��ϼ��ɡ�
	private List<GameObject> objects = new ArrayList<>();
	//����ColliderChain����,ʹ��������ģʽ
	ColliderChain chain = new ColliderChain();
//	Collider collider = new BulletTankCollider();
//	Collider collider2 = new TankTankCollider();
	static final int GAME_WIDTH = 1080, GAME_HEIGHT = 720;
	//ʹ�õ������ģʽ
	public static GameModel getInstance() {
		// TODO Auto-generated method stub
		return INSTANCE;
	}
	private GameModel(){
//		//��ʼ����ս̹��
//		myTank = new Tank(400,400,Dir.UP, Group.GOOD);
//		int initTankCount = Integer.parseInt
//				((String)PropertyMgr.get("initTankCount"));
//		
//		for(int i = 0; i < initTankCount; i++){
////			add(new Tank(50 + i*100, 200, 
////					Dir.DOWN, Group.BAD, this));
//			add(new Tank(50 + i*100, 200, Dir.DOWN, Group.BAD));
//		}
//		//��ʼ��ǽ
//		add(new Wall(150, 150, 200, 50));
//		add(new Wall(550, 150, 200, 50));
//		add(new Wall(300, 300, 50, 200));
//		add(new Wall(550, 300, 50, 200));
	}
	public void init(){
		// ��ʼ����ս̹��
		myTank = new Tank(400, 400, Dir.UP, Group.GOOD);
		int initTankCount = Integer.parseInt((String) PropertyMgr.get("initTankCount"));

		for (int i = 0; i < initTankCount; i++) {
			// add(new Tank(50 + i*100, 200,
			// Dir.DOWN, Group.BAD, this));
			new Tank(50 + i * 100, 200, Dir.DOWN, Group.BAD);
		}
		// ��ʼ��ǽ
		add(new Wall(150, 150, 200, 50));
		add(new Wall(550, 150, 200, 50));
		add(new Wall(300, 300, 50, 200));
		add(new Wall(550, 300, 50, 200));
	}
	public void add(GameObject go){
		//�ټ�������ø÷�������
		this.objects.add(go);
	}
	public void remove(GameObject go){
		//��ɾ������ø÷�������
		this.objects.remove(go);
	}
	
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		Color c = g.getColor();
		g.setColor(Color.WHITE);
//		g.drawString("�ӵ�������" + bullets.size(), 10, 60);
//		g.drawString("���˵�����" + tanks.size(), 10, 80);
//		g.drawString("��ը������" + explodes.size(), 10, 100);
		g.setColor(c);
		
		myTank.paint(g);
		//b.paint(g);
//		for(Bullet b : bullets){���ø÷�������Ϊ�����˵�����������ʱ�ⲿ���ܶԼ��Ͻ���ɾ�����������Ըñ������С�
//			b.paint(g);
//		}
		//����GameObject��list���������е����嶼�������list�С��ٵ��ó�����һ�μ��ɡ�
		for(int i = 0; i < objects.size(); i++){
			objects.get(i).paint(g);
		}
		
		
//		for(int i = 0; i < bullets.size(); i++){
//			bullets.get(i).paint(g);
//		}
		//��̹��
//		for(int i = 0; i < tanks.size(); i++){
//			tanks.get(i).paint(g);
//		}
		
		//����ը
//		for(int i = 0; i < explodes.size(); i++){
//			explodes.get(i).paint(g);
//		}
		
		//�ж��ӵ���̹���Ƿ���ײ
//		for(int i = 0; i < bullets.size(); i++){
//			for(int j = 0; j < tanks.size(); j++){
//				bullets.get(i).collideWith(tanks.get(j));
//			}
//		}
		//�������˵�ͣ�����ģʽ�µĻ�����ײ
		for(int i = 0; i < objects.size(); i++){
			for(int j = i + 1; j < objects.size(); j++){
				GameObject o1 = objects.get(i);
				GameObject o2 = objects.get(j);
//				collider.collide(o1, o2);
//				collider2.collide(o1, o2);
				//��ôȥײ�������Լ�֪��������������������
				chain.collide(o1, o2);
			}
		}
		
//		if(tanks.size() == 0){
//			c = g.getColor();
//			g.setColor(Color.RED);
//			g.drawString("��Ӯ��", GAME_WIDTH / 2, GAME_HEIGHT / 6);
//			g.setColor(c);
//		}
	}
	public Tank getMainTank() {
		// TODO Auto-generated method stub
		return myTank;
	}


	

}
