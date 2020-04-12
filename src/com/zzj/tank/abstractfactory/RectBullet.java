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
	private int x,y;//子弹位置
	private Dir dir;//子弹方向
	//子弹高度宽度
	public static int WIDTH = ResourceMgr.bulletD.getWidth();
	public static int HEIGHT = ResourceMgr.bulletD.getHeight();
	private boolean living = true;
	TankFrame tf = null;
	private Group group = Group.BAD;
	Rectangle rect = new Rectangle();//一开始就产生rect来记录，这样后面就不用总是new了。
	
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
		//一开始new出来就加入集合
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
		//上子弹图片
		Color c = g.getColor();//获得原来的颜色
		g.setColor(Color.YELLOW);
		g.fillOval(x, y, 6, 6);
		g.setColor(c);//设置完后将原来的颜色还回去
		
		
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
	public void collideWith(BaseTank tank) {
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
			//tf.explodes.add(new Explode(eX, eY, tf));
			//改用工厂生产
			tf.explodes.add(tf.gf.createExplode(eX, eY, tf));
		}
	}

	private void die() {
		// TODO Auto-generated method stub
		this.living = false;
	}
}
