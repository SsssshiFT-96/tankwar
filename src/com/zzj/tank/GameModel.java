package com.zzj.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class GameModel {
	
	Tank myTank = new Tank(400,400,Dir.UP, Group.GOOD, this);
	List<Bullet> bullets = new ArrayList<>();
	//加入敌方坦克
	List<Tank> tanks = new ArrayList<>();
	List<Explode> explodes = new ArrayList<>();
	static final int GAME_WIDTH = 1080, GAME_HEIGHT = 720;
	public GameModel(){
		int initTankCount = Integer.parseInt
				((String)PropertyMgr.get("initTankCount"));
		
		for(int i = 0; i < initTankCount; i++){
			tanks.add(new Tank(50 + i*100, 200, 
					Dir.DOWN, Group.BAD, this));
		}
	}
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		Color c = g.getColor();
		g.setColor(Color.WHITE);
		g.drawString("子弹的数量" + bullets.size(), 10, 60);
		g.drawString("敌人的数量" + tanks.size(), 10, 80);
		g.drawString("爆炸的数量" + explodes.size(), 10, 100);
		g.setColor(c);
		
		myTank.paint(g);
		//b.paint(g);
//		for(Bullet b : bullets){若用该方法，因为是用了迭代器，迭代时外部不能对集合进行删除操作，所以该遍历不行。
//			b.paint(g);
//		}
		for(int i = 0; i < bullets.size(); i++){
			bullets.get(i).paint(g);
		}
		//画坦克
		for(int i = 0; i < tanks.size(); i++){
			tanks.get(i).paint(g);
		}
		
		//画爆炸
		for(int i = 0; i < explodes.size(); i++){
			explodes.get(i).paint(g);
		}
		
		//判断子弹和坦克是否相撞
		for(int i = 0; i < bullets.size(); i++){
			for(int j = 0; j < tanks.size(); j++){
				bullets.get(i).collideWith(tanks.get(j));
			}
		}
		
		if(tanks.size() == 0){
			c = g.getColor();
			g.setColor(Color.RED);
			g.drawString("你赢了", GAME_WIDTH / 2, GAME_HEIGHT / 6);
			g.setColor(c);
		}
		

		
//		x += 10;
//		y += 10;
	}
	public Tank getMainTank() {
		// TODO Auto-generated method stub
		return myTank;
	}

}
