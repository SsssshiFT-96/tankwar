package com.zzj.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
//̹�˱�ը��
public class Explode {
	private int x,y;//�ӵ�λ��
	//�ӵ��߶ȿ���
	public static int WIDTH = ResourceMgr.explodes[0].getWidth();
	public static int HEIGHT = ResourceMgr.explodes[0].getHeight();
	//private boolean living = true;
	TankFrame tf = null;
	private int step = 0;//��¼�����ڼ�����ըͼ
	
	public Explode(int x, int y, TankFrame tf){
		this.x = x;
		this.y = y;
		this.tf = tf;
	}
	public void paint(Graphics g){
		g.drawImage(ResourceMgr.explodes[step++], x, y, null);
		if(step >= ResourceMgr.explodes.length)
			//��ը�������Ƴ����Ͻ�����ը
			tf.explodes.remove(this);
		
	}	
}
