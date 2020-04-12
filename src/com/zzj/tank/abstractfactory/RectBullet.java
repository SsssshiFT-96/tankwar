package com.zzj.tank.abstractfactory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.zzj.tank.Dir;
import com.zzj.tank.Explode;
import com.zzj.tank.Group;
import com.zzj.tank.ResourceMgr;
import com.zzj.tank.Tank;
import com.zzj.tank.TankFrame;
import com.zzj.tank.abstractfactory.BaseBullet;

public class RectBullet extends BaseBullet{
	private static final int SPEED = 10;
	private int x,y;//�ӵ�λ��
	private Dir dir;//�ӵ�����
	//�ӵ��߶ȿ��
	public static int WIDTH = ResourceMgr.bulletD.getWidth();
	public static int HEIGHT = ResourceMgr.bulletD.getHeight();
	private boolean living = true;
	TankFrame tf = null;
	private Group group = Group.BAD;
	Rectangle rect = new Rectangle();//һ��ʼ�Ͳ���rect����¼����������Ͳ�������new�ˡ�
	
	public RectBullet(int x, int y, Dir dir, Group group, TankFrame tf){
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.tf = tf;
		this.group = group;
	
		rect.x = this.x;
		rect.y = this.y;
		rect.width = WIDTH;
		rect.height = HEIGHT;
		//һ��ʼnew�����ͼ��뼯��
		tf.bullets.add(this);
	}
	
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public void paint(Graphics g){
		if(!living){
			tf.bullets.remove(this);
		}
		//���ӵ�ͼƬ
		Color c = g.getColor();//���ԭ������ɫ
		g.setColor(Color.YELLOW);
		g.fillOval(x, y, 6, 6);
		g.setColor(c);//�������ԭ������ɫ����ȥ
		
		
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
	public void collideWith(BaseTank tank) {
		// TODO Auto-generated method stub
		//�ж��ӵ���̹���Ƿ�Ϊͬһ��
		if(this.group == tank.getGroup()) return;
		//��ȡ�ӵ���̹����ͬ��С�ľ��κ���λ��
		//TODO:��һ��rect����¼�ӵ���λ��
//		Rectangle rect1 = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
//		Rectangle rect2 = new Rectangle(tank.getX(), tank.getY(), Tank.WIDTH, Tank.HEIGHT);
		//�ж��Ƿ��ཻ���ཻ����ʧ,��ʧ������ը
		if(rect.intersects(tank.rect)){
			tank.die();
			this.die();
			int eX = tank.getX() + Tank.WIDTH / 2 - Explode.WIDTH / 2;
			int eY = tank.getY() + Tank.HEIGHT / 2 - Explode.HEIGHT / 2;
			//tf.explodes.add(new Explode(eX, eY, tf));
			//���ù�������
			tf.explodes.add(tf.gf.createExplode(eX, eY, tf));
		}
	}

	private void die() {
		// TODO Auto-generated method stub
		this.living = false;
	}
}
