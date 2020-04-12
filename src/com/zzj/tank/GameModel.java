package com.zzj.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class GameModel {
	
	Tank myTank = new Tank(400,400,Dir.UP, Group.GOOD, this);
	List<Bullet> bullets = new ArrayList<>();
	//����з�̹��
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
		g.drawString("�ӵ�������" + bullets.size(), 10, 60);
		g.drawString("���˵�����" + tanks.size(), 10, 80);
		g.drawString("��ը������" + explodes.size(), 10, 100);
		g.setColor(c);
		
		myTank.paint(g);
		//b.paint(g);
//		for(Bullet b : bullets){���ø÷�������Ϊ�����˵�����������ʱ�ⲿ���ܶԼ��Ͻ���ɾ�����������Ըñ������С�
//			b.paint(g);
//		}
		for(int i = 0; i < bullets.size(); i++){
			bullets.get(i).paint(g);
		}
		//��̹��
		for(int i = 0; i < tanks.size(); i++){
			tanks.get(i).paint(g);
		}
		
		//����ը
		for(int i = 0; i < explodes.size(); i++){
			explodes.get(i).paint(g);
		}
		
		//�ж��ӵ���̹���Ƿ���ײ
		for(int i = 0; i < bullets.size(); i++){
			for(int j = 0; j < tanks.size(); j++){
				bullets.get(i).collideWith(tanks.get(j));
			}
		}
		
		if(tanks.size() == 0){
			c = g.getColor();
			g.setColor(Color.RED);
			g.drawString("��Ӯ��", GAME_WIDTH / 2, GAME_HEIGHT / 6);
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
