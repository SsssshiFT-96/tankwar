package com.zzj.tank.decorator;

import java.awt.Graphics;

import com.zzj.tank.GameObject;
//����װ�����ģʽ����Ϸ������װ�Ρ�
public abstract class GODecorator extends GameObject {
	GameObject go;
	public GODecorator(GameObject go){
		this.go = go;
	}
	@Override
	public abstract void paint(Graphics g);


}
