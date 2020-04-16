package com.zzj.tank.decorator;

import java.awt.Color;
import java.awt.Graphics;

import com.zzj.tank.GameObject;

public class RectDecorator extends GODecorator {

	public RectDecorator(GameObject go) {
		super(go);
	}

	@Override
	public void paint(Graphics g) {	
		//保证子弹位置变化时，装饰的位置也变化
		this.x = go.x;
		this.y = go.y;
		//先调用父类的paint，这样就能先画出父类中的物体，再画自己想要的装饰
//		super.paint(g);
		go.paint(g);
		Color c = g.getColor();
		g.setColor(Color.YELLOW);
		g.drawRect(go.x, go.y, go.getWidth(), 
				go.getHeight());//本类继承GODecorator，GODecorator又继承GameObject，所以x，y可以由GO确定
		g.setColor(c);
		
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return super.go.getWidth();
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return super.go.getHeight();
	}
	
}
