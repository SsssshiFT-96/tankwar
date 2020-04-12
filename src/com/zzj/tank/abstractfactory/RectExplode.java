package com.zzj.tank.abstractfactory;

import java.awt.Color;
import java.awt.Graphics;

import com.zzj.tank.ResourceMgr;
import com.zzj.tank.TankFrame;

public class RectExplode extends BaseExplode {
	private int x,y;//子弹位置
	//子弹高度宽度
	public static int WIDTH = ResourceMgr.explodes[0].getWidth();
	public static int HEIGHT = ResourceMgr.explodes[0].getHeight();
	//private boolean living = true;
	TankFrame tf = null;
	private int step = 0;//记录画到第几幅爆炸图
	
	public RectExplode(int x, int y, TankFrame tf){
		this.x = x;
		this.y = y;
		this.tf = tf;
	}
	@Override
	public void paint(Graphics g){
		//g.drawImage(ResourceMgr.explodes[step++], x, y, null);
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillRect(x, y, 10 * step, 10 * step);
		step++;
		g.setColor(c);
		if(step >= ResourceMgr.explodes.length)
			//爆炸结束则移除集合结束爆炸
			tf.explodes.remove(this);
		
	}

}
