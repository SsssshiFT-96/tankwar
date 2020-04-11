package com.zzj.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Bullet {
	private static final int SPEED = 10;
	private int x,y;//子弹位置
	private Dir dir;//子弹方向
	//子弹高度宽度
	public static int WIDTH = ResourceMgr.bulletD.getWidth();
	public static int HEIGHT = ResourceMgr.bulletD.getHeight();
	private boolean living = true;
	TankFrame tf = null;
	private Group group = Group.BAD;
	Rectangle rect = new Rectangle();//一开始就产生rect来记录，这样后面就不用总是new了。
	
	public Bullet(int x, int y, Dir dir, Group group, TankFrame tf){
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.tf = tf;
		this.group = group;
	
		rect.x = this.x;
		rect.y = this.y;
		rect.width = WIDTH;
		rect.height = HEIGHT;
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
		
//		Color c = g.getColor();//获得原来的颜色
//		g.setColor(Color.RED);
//		g.fillOval(x, y, 6, 6);
//		g.setColor(c);//设置完后将原来的颜色还回去
		//上子弹图片
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
		//移动时更新rect值
		rect.x = this.x;
		rect.y = this.y;
		//设置当子弹飞出窗口时，去掉list中的子弹。若不去掉会造成内存泄露。
		if(x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT)
			living = false;
	}
	//判断子弹和坦克是否相撞
	public void collideWith(Tank tank) {
		// TODO Auto-generated method stub
		//判断子弹与坦克是否为同一方
		if(this.group == tank.getGroup()) return;
		//获取子弹和坦克相同大小的矩形和其位置
		//TODO:用一个rect来记录子弹的位置
//		Rectangle rect1 = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
//		Rectangle rect2 = new Rectangle(tank.getX(), tank.getY(), Tank.WIDTH, Tank.HEIGHT);
		//判断是否相交，相交即消失,消失产生爆炸
		if(rect.intersects(tank.rect)){
			tank.die();
			this.die();
			int eX = tank.getX() + Tank.WIDTH / 2 - Explode.WIDTH / 2;
			int eY = tank.getY() + Tank.HEIGHT / 2 - Explode.HEIGHT / 2;
			tf.explodes.add(new Explode(eX, eY, tf));
		}
	}

	private void die() {
		// TODO Auto-generated method stub
		this.living = false;
	}
}
