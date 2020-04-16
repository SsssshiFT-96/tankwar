package com.zzj.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
//坦克爆炸类
public class Explode extends GameObject{
//	private int x,y;
	//子弹高度宽度
	public static int WIDTH = ResourceMgr.explodes[0].getWidth();
	public static int HEIGHT = ResourceMgr.explodes[0].getHeight();
	//private boolean living = true;
	//TankFrame tf = null;
//	GameModel gm = null;
	private int step = 0;//记录画到第几幅爆炸图
	
	public Explode(int x, int y/*, GameModel gmTankFrame tf*/){
		this.x = x;
		this.y = y;
//		this.tf = tf;
//		this.gm = gm;
		GameModel.getInstance().add(this);
	}
	public void paint(Graphics g){
		g.drawImage(ResourceMgr.explodes[step++], x, y, null);
		if(step >= ResourceMgr.explodes.length)
			//爆炸结束则移除集合结束爆炸
//			gm.remove(this);
			GameModel.getInstance().remove(this);
		
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
