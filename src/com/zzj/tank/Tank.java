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
	private static final int SPEED = 3;//����̹�˵��ٶ� 
	//̹�˸߶ȿ��
	public static int WIDTH = ResourceMgr.goodTankU.getWidth();
	public static int HEIGHT = ResourceMgr.goodTankU.getHeight();
	public int x;//λ��
	public int y;
	private Dir dir = Dir.DOWN;
	private boolean moving = false;
	private TankFrame tf = null;
	private boolean living = true;
	private Random random = new Random();//�������������,�����ӵ�
	private Group group = Group.BAD;//Ĭ��new������̹���ǵз�̹��
	Rectangle rect = new Rectangle();//����ͬ�ӵ��е�rect
	UUID id  = UUID.randomUUID();//���������UUID
	
	
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
		//����Ϊ0����֤�ÿ�
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
		//��̹�˶������TankFrame��������ã������õ�����֮����tf�����Ͻ����޸ġ�
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
		
		//���Ƶз�̹�˷����ӵ�
		if(random.nextInt(10) > 8 && group == Group.BAD){
			this.fire();
			
		}
		//�õз�̹����һ������������ı䷽��
		if(random.nextInt(100) > 95 && group == Group.BAD){
			randomDir();
		}
		//�߽���
		boundsCheck();
		
		//�ƶ�ʱ����rectֵ
		rect.x = this.x;
		rect.y = this.y;
		
		
	}
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		//Graphics������ڴ��������⻭��
		//ÿ���µ��ô��ڣ��÷������ᱻʹ�ã�x��yֵ�ı���󷽿���ƶ�
//		Color c = g.getColor();//���ԭ������ɫ
//		g.setColor(Color.GREEN);
//		g.fillRect(x, y, 50, 50);//��һ�����飬��������������ʹ�С
//		g.setColor(c);
		//��idд��̹������
		Color c = g.getColor();
		g.setColor(Color.YELLOW);
		g.drawString(id.toString(), this.x, this.y - 10);//��һ�����飬��������������ʹ�С
		g.setColor(c);
		
		//��̹��ͼƬ����ͬ�����ͼƬ��ͬ��
		if(!living){
			moving = false;
			Color cc = g.getColor();
			g.setColor(Color.WHITE);
			g.drawRect(x, y, WIDTH, HEIGHT);
			g.setColor(cc);
			return;
//			tf.tanks.remove(this);//û���ž��Ƴ�
		}
		//�ж��Ƿ��ǵз�̹������ȡ��ӦͼƬ
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
		//ö�������������4�����±꣬Ȼ��ͨ��values�������Ԫ��
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
