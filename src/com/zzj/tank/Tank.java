package com.zzj.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;
import java.util.UUID;

import com.zzj.tank.net.BulletNewMsg;
import com.zzj.tank.net.Client;
import com.zzj.tank.net.TankJoinMsg;

public class Tank {
	private static final int SPEED = 3;//设置坦克的速度 
	//坦克高度宽度
	public static int WIDTH = ResourceMgr.goodTankU.getWidth();
	public static int HEIGHT = ResourceMgr.goodTankU.getHeight();
	public int x;//位置
	public int y;
	private Dir dir = Dir.DOWN;
	private boolean moving = false;
	private TankFrame tf = null;
	private boolean living = true;
	private Random random = new Random();//用于随机换方向,发射子弹
	private Group group = Group.BAD;//默认new出来的坦克是敌方坦克
	Rectangle rect = new Rectangle();//道理同子弹中的rect
	UUID id  = UUID.randomUUID();//生成随机的UUID
	
	
	public Tank(int x, int y, Dir dir, Group group, TankFrame tf) {
		super();
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
	public Tank(TankJoinMsg msg) {
		this.x = msg.x;
		this.y = msg.y;
		this.dir = msg.dir;
		this.moving = msg.moving;
		this.group = msg.group;
		this.id = msg.id;
		
		rect.x = this.x;
		rect.y = this.y;
		rect.width = WIDTH;
		rect.height = HEIGHT;
	}
	private void boundsCheck() {
		// TODO Auto-generated method stub
		//不归为0，保证好看
		if(this.x < 2) x = 2;
		if(this.y < 28) y = 28;
		if(this.x > TankFrame.GAME_WIDTH - Tank.WIDTH - 2) 
			x = TankFrame.GAME_WIDTH - Tank.WIDTH - 2;
		if(this.y > TankFrame.GAME_HEIGHT - Tank.HEIGHT - 2) 
			y = TankFrame.GAME_HEIGHT - Tank.HEIGHT - 2;
	}
	public void die() {
		// TODO Auto-generated method stub
		this.living = false;
	}
	public void fire() {
		//将坦克对象持有TankFrame对象的引用，这样拿到引用之后在tf对象上进行修改。
		// TODO Auto-generated method stub
		int bX = this.x + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
		int bY = this.y + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;
//		tf.bullets.add(new Bullet(this.id, bX, bY, this.dir, this.group,this.tf));
		Bullet b = new Bullet(this.id, bX, bY, this.dir, this.group,this.tf);
		tf.bullets.add(b);
		Client.INSTANCE.send(new BulletNewMsg(b));
	}
	public Dir getDir() {
		return dir;
	}
	public Group getGroup() {
		return group;
	}
	public UUID getId() {
		return id;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public boolean isMoving() {
		return moving;
	}
	private void move() {
		// TODO Auto-generated method stub
		if(!moving) return;
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
		
		//控制敌方坦克发射子弹
		if(random.nextInt(10) > 8 && group == Group.BAD){
			this.fire();
			
		}
		//让敌方坦克在一定条件下随机改变方向
		if(random.nextInt(100) > 95 && group == Group.BAD){
			randomDir();
		}
		//边界检测
		boundsCheck();
		
		//移动时更新rect值
		rect.x = this.x;
		rect.y = this.y;
		
		
	}
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		//Graphics类可以在窗口中任意画画
		//每重新调用窗口，该方法均会被使用，x，y值改变完后方块的移动
//		Color c = g.getColor();//获得原来的颜色
//		g.setColor(Color.GREEN);
//		g.fillRect(x, y, 50, 50);//画一个方块，参数依次是坐标和大小
//		g.setColor(c);
		//将id写在坦克上面
		Color c = g.getColor();
		g.setColor(Color.YELLOW);
		g.drawString(id.toString(), this.x, this.y - 10);//画一个方块，参数依次是坐标和大小
		g.setColor(c);
		
		//上坦克图片，不同方向的图片不同。
		if(!living){
			moving = false;
			Color cc = g.getColor();
			g.setColor(Color.WHITE);
			g.drawRect(x, y, WIDTH, HEIGHT);
			g.setColor(cc);
			return;
//			tf.tanks.remove(this);//没活着就移除
		}
		//判断是否是敌方坦克来获取相应图片
		switch(dir){
			case LEFT:
				g.drawImage(this.group == Group.GOOD? 
						ResourceMgr.goodTankL : ResourceMgr.badTankL, 
						x, y, null);
				break;
			case RIGHT:
				g.drawImage(this.group == Group.GOOD? 
						ResourceMgr.goodTankR : ResourceMgr.badTankR, 
						x, y, null);
				break;
			case UP:
				g.drawImage(this.group == Group.GOOD? 
						ResourceMgr.goodTankU : ResourceMgr.badTankU, 
						x, y, null);
				break;
			case DOWN:
				g.drawImage(this.group == Group.GOOD? 
						ResourceMgr.goodTankD : ResourceMgr.badTankD, 
						x, y, null);
				break;
		}

		move();
		
	}
	private void randomDir() {
		// TODO Auto-generated method stub
		//枚举类中随机产生4以内下标，然后通过values方法获得元素
		this.dir = Dir.values()[random.nextInt(4)];
	}
	public void setDir(Dir dir) {
		this.dir = dir;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public boolean isLiving() {
		return living;
	}
}
