package com.zzj.tank;

import java.awt.Graphics;

//这里开始使用调停者模式
//建立一个游戏物体类，该类作为所有游戏物体的父类。当后续有额外物体添加时，继承该类
public abstract class GameObject {
	public int x;//位置
	public int y;
	
	public abstract void paint(Graphics g);//paint方法必须有,且为抽象，因为后面每个物体都有自己的paint方法
	public abstract int getWidth();
	public abstract int getHeight();
	
}
