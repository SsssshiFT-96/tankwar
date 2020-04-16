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
	//用了单例后，new主坦克需要先对INSTANCE初始化,修复了一个小bug，在P143视频4min。
	static{
		INSTANCE.init();
	}
	Tank myTank; /*= new Tank(400,400,Dir.UP, Group.GOOD, this);*/
//	List<Bullet> bullets = new ArrayList<>();
	//加入敌方坦克
//	List<Tank> tanks = new ArrayList<>();
//	List<Explode> explodes = new ArrayList<>();
	//将游戏物体扔进下面的集合即可。
	private List<GameObject> objects = new ArrayList<>();
	//创建ColliderChain对象,使用责任链模式
	ColliderChain chain = new ColliderChain();
//	Collider collider = new BulletTankCollider();
//	Collider collider2 = new TankTankCollider();
	static final int GAME_WIDTH = 1080, GAME_HEIGHT = 720;
	//使用单例设计模式
	public static GameModel getInstance() {
		// TODO Auto-generated method stub
		return INSTANCE;
	}
	private GameModel(){
//		//初始化主战坦克
//		myTank = new Tank(400,400,Dir.UP, Group.GOOD);
//		int initTankCount = Integer.parseInt
//				((String)PropertyMgr.get("initTankCount"));
//		
//		for(int i = 0; i < initTankCount; i++){
////			add(new Tank(50 + i*100, 200, 
////					Dir.DOWN, Group.BAD, this));
//			add(new Tank(50 + i*100, 200, Dir.DOWN, Group.BAD));
//		}
//		//初始化墙
//		add(new Wall(150, 150, 200, 50));
//		add(new Wall(550, 150, 200, 50));
//		add(new Wall(300, 300, 50, 200));
//		add(new Wall(550, 300, 50, 200));
	}
	public void init(){
		// 初始化主战坦克
		myTank = new Tank(400, 400, Dir.UP, Group.GOOD);
		int initTankCount = Integer.parseInt((String) PropertyMgr.get("initTankCount"));

		for (int i = 0; i < initTankCount; i++) {
			// add(new Tank(50 + i*100, 200,
			// Dir.DOWN, Group.BAD, this));
			new Tank(50 + i * 100, 200, Dir.DOWN, Group.BAD);
		}
		// 初始化墙
		add(new Wall(150, 150, 200, 50));
		add(new Wall(550, 150, 200, 50));
		add(new Wall(300, 300, 50, 200));
		add(new Wall(550, 300, 50, 200));
	}
	public void add(GameObject go){
		//再加物体调用该方法即可
		this.objects.add(go);
	}
	public void remove(GameObject go){
		//再删物体调用该方法即可
		this.objects.remove(go);
	}
	
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		Color c = g.getColor();
		g.setColor(Color.WHITE);
//		g.drawString("子弹的数量" + bullets.size(), 10, 60);
//		g.drawString("敌人的数量" + tanks.size(), 10, 80);
//		g.drawString("爆炸的数量" + explodes.size(), 10, 100);
		g.setColor(c);
		
		myTank.paint(g);
		//b.paint(g);
//		for(Bullet b : bullets){若用该方法，因为是用了迭代器，迭代时外部不能对集合进行删除操作，所以该遍历不行。
//			b.paint(g);
//		}
		//有了GameObject的list，这样所有的物体都放在这个list中。再调用出来用一次即可。
		for(int i = 0; i < objects.size(); i++){
			objects.get(i).paint(g);
		}
		
		
//		for(int i = 0; i < bullets.size(); i++){
//			bullets.get(i).paint(g);
//		}
		//画坦克
//		for(int i = 0; i < tanks.size(); i++){
//			tanks.get(i).paint(g);
//		}
		
		//画爆炸
//		for(int i = 0; i < explodes.size(); i++){
//			explodes.get(i).paint(g);
//		}
		
		//判断子弹和坦克是否相撞
//		for(int i = 0; i < bullets.size(); i++){
//			for(int j = 0; j < tanks.size(); j++){
//				bullets.get(i).collideWith(tanks.get(j));
//			}
//		}
		//在运用了调停者设计模式下的互相碰撞
		for(int i = 0; i < objects.size(); i++){
			for(int j = i + 1; j < objects.size(); j++){
				GameObject o1 = objects.get(i);
				GameObject o2 = objects.get(j);
//				collider.collide(o1, o2);
//				collider2.collide(o1, o2);
				//怎么去撞，链条自己知道，这是面向对象的体现
				chain.collide(o1, o2);
			}
		}
		
//		if(tanks.size() == 0){
//			c = g.getColor();
//			g.setColor(Color.RED);
//			g.drawString("你赢了", GAME_WIDTH / 2, GAME_HEIGHT / 6);
//			g.setColor(c);
//		}
	}
	public Tank getMainTank() {
		// TODO Auto-generated method stub
		return myTank;
	}


	

}
