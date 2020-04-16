package com.zzj.tank.decorator;

import java.awt.Color;
import java.awt.Graphics;

import com.zzj.tank.GameObject;

public class TailDecorator extends GODecorator {

	public TailDecorator(GameObject go) {
		super(go);
	}

	@Override
	public void paint(Graphics g) {	
		this.x = go.x;
		this.y = go.y;
		//�ȵ��ø����paint�����������Ȼ��������е����壬�ٻ��Լ���Ҫ��װ��
//		super.paint(g);
		go.paint(g);
		Color c = g.getColor();
		g.setColor(Color.YELLOW);
		g.drawLine(go.x, go.y, go.x + getWidth(), 
				go.y + getHeight());//����̳�GODecorator��GODecorator�ּ̳�GameObject������x��y������GOȷ��
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
