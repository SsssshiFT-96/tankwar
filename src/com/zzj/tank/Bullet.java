package com.zzj.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Bullet extends GameObject{
	private static final int SPEED = 10;
//	private int x,y;//�ӵ�λ��
	private Dir dir;//�ӵ�����
	//�ӵ��߶ȿ��
	public static int WIDTH = ResourceMgr.bulletD.getWidth();
	public static int HEIGHT = ResourceMgr.bulletD.getHeight();
	private boolean living = true;
//	TankFrame tf = null;
//	GameModel gm = null;
	public Group group = Group.BAD;
	public Rectangle rect = new Rectangle();//һ��ʼ�Ͳ���rect����¼����������Ͳ�������new�ˡ�
	
	public Bullet(int x, int y, Dir dir, Group group/*, GameModel gmTankFrame tf*/){
		this.x = x;
		this.y = y;
		this.dir = dir;
		//this.tf = tf;
//		this.gm = gm;
		this.group = group;
	
		rect.x = this.x;
		rect.y = this.y;
		rect.width = WIDTH;
		rect.height = HEIGHT;
		//һ��ʼnew�����ͼ��뼯��
		//tf.bullets.add(this);
//		GameModel.getInstance().add(this);
//		gm.add(this);
		GameModel.getInstance().add(this);
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public void paint(Graphics g){
		if(!living){
//			gm.remove(this);
			GameModel.getInstance().remove(this);
		}
		
//		Color c = g.getColor();//���ԭ������ɫ
//		g.setColor(Color.RED);
//		g.fillOval(x, y, 6, 6);
//		g.setColor(c);//�������ԭ������ɫ����ȥ
		//���ӵ�ͼƬ
		switch(dir){
		case LEFT:
			g.drawImage(ResourceMgr.bulletL, x, y, null);
			break;
		case RIGHT:
			g.drawImage(ResourceMgr.bulletR, x, y, null);
			break;
		case UP:
			g.drawImage(ResourceMgr.bulletU, x, y, null);
			break;
		case DOWN:
			g.drawImage(ResourceMgr.bulletD, x, y, null);
			break;
	}
		move();
	}

	private void move() {
		// TODO Auto-generated method stub
		switch(dir){
		case LEFT:
			x -= SPEED;
			break;
		case RIGHT:
			x += SPEED;
			break;
		case UP:
			y -= SPEED;
			break;
		case DOWN:
			y += SPEED;
			break;
		}
		//�ƶ�ʱ����rectֵ
		rect.x = this.x;
		rect.y = this.y;
		//���õ��ӵ��ɳ�����ʱ��ȥ��list�е��ӵ�������ȥ��������ڴ�й¶��
		if(x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT)
			living = false;
	}
	//�ж��ӵ���̹���Ƿ���ײ
	//�÷���ת�Ƶ�Collider��
//	public boolean collideWith(Tank tank) {
//		// TODO Auto-generated method stub
//		//�ж��ӵ���̹���Ƿ�Ϊͬһ��
//		if(this.group == tank.getGroup()) return false;
//		//��ȡ�ӵ���̹����ͬ��С�ľ��κ���λ��
//		//TODO:��һ��rect����¼�ӵ���λ��
////		Rectangle rect1 = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
////		Rectangle rect2 = new Rectangle(tank.getX(), tank.getY(), Tank.WIDTH, Tank.HEIGHT);
//		//�ж��Ƿ��ཻ���ཻ����ʧ,��ʧ������ը
//		if(rect.intersects(tank.rect)){
//			tank.die();
//			this.die();
//			int eX = tank.getX() + Tank.WIDTH / 2 - Explode.WIDTH / 2;
//			int eY = tank.getY() + Tank.HEIGHT / 2 - Explode.HEIGHT / 2;
//			gm.add(new Explode(eX, eY, gm));
//			return true;
//		}
//		return false;
//	}

	public void die() {
		// TODO Auto-generated method stub
		this.living = false;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return WIDTH;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return HEIGHT;
	}
}
