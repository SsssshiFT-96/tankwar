package com.zzj.tank.decorator;

import java.awt.Graphics;

import com.zzj.tank.GameObject;
//利用装饰设计模式给游戏内物体装饰。
public abstract class GODecorator extends GameObject {
	GameObject go;
	public GODecorator(GameObject go){
		this.go = go;
	}
	@Override
	public abstract void paint(Graphics g);


}
