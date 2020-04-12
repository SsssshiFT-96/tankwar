package com.zzj.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Tank {
	private static final int SPEED = 3;//����̹�˵��ٶ� 
	//̹�˸߶ȿ��
	public static int WIDTH = ResourceMgr.goodTankU.getWidth();
	public static int HEIGHT = ResourceMgr.goodTankU.getHeight();
	int x, y;//λ��
	Dir dir = Dir.DOWN;
	private boolean moving = true;
	TankFrame tf = null;
	private boolean living = true;
	private Random random = new Random();//�������������,�����ӵ�
	Group group = Group.BAD;//Ĭ��new������̹���ǵз�̹��
	Rectangle rect = new Rectangle();//����ͬ�ӵ��е�rect
	FireStrategy fs;//����ʹ���˲���ģʽ
	
	
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
		
		//��ʼ��������ԣ����ж��Ƿ�Ϊ�з�̹����ѡ�񿪻����
		//ʹ�������ļ����Ե�Ҫ�ı俪�����ʱ��ֻ���޸������ļ�����Ӧ����Ϣ���ɡ�
		if(group == Group.GOOD){
			String goodFSName = (String) PropertyMgr.get("goodFs");
			try {//�õ����ִ��������ص��ڴ棬��new���¶���
				fs = (FireStrategy)Class.forName(goodFSName).newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		else{
			fs = new DefaultFireStrategy();
		}
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	//���������ʱ��������fire���洫FireStrategy����ÿ��ʹ�ö���Ҫnew�����Խ�DefaultFireStrategy��Ϊ����
	//Ҳ���Խ�����Ϊ��Ա����
	public void fire() {
		//��̹�˶������TankFrame��������ã������õ�����֮����tf�����Ͻ����޸ġ�
		// TODO Auto-generated method stub
		//��fire��Ϊ����ģʽ
//		int bX = this.x + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
//		int bY = this.y + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;
//		tf.bullets.add(new Bullet(bX, bY, this.dir, this.group,this.tf));
		fs.fire(this);
	}
	public Dir getDir() {
		return dir;
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
	private void randomDir() {
		// TODO Auto-generated method stub
		//ö�������������4�����±꣬Ȼ��ͨ��values�������Ԫ��
		this.dir = Dir.values()[random.nextInt(4)];
	}
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		//Graphics������ڴ��������⻭��
		//ÿ���µ��ô��ڣ��÷������ᱻʹ�ã�x��yֵ�ı���󷽿���ƶ�
//		Color c = g.getColor();//���ԭ������ɫ
//		g.setColor(Color.GREEN);
//		g.fillRect(x, y, 50, 50);//��һ�����飬��������������ʹ�С
//		g.setColor(c);
		//��̹��ͼƬ����ͬ�����ͼƬ��ͬ��
		if(!living) tf.tanks.remove(this);//û���ž��Ƴ�
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
	public void setDir(Dir dir) {
		this.dir = dir;
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
	public void die() {
		// TODO Auto-generated method stub
		this.living = false;
	}
}
