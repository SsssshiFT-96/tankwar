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
		//��֤�ӵ�λ�ñ仯ʱ��װ�ε�λ��Ҳ�仯
		this.x = go.x;
		this.y = go.y;
		//�ȵ��ø����paint�����������Ȼ��������е����壬�ٻ��Լ���Ҫ��װ��
//		super.paint(g);
		go.paint(g);
		Color c = g.getColor();
		g.setColor(Color.YELLOW);
		g.drawRect(go.x, go.y, go.getWidth(), 
				go.getHeight());//����̳�GODecorator��GODecorator�ּ̳�GameObject������x��y������GOȷ��
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
