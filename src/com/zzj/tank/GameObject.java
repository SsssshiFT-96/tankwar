package com.zzj.tank;

import java.awt.Graphics;

//���￪ʼʹ�õ�ͣ��ģʽ
//����һ����Ϸ�����࣬������Ϊ������Ϸ����ĸ��ࡣ�������ж����������ʱ���̳и���
public abstract class GameObject {
	public int x;//λ��
	public int y;
	
	public abstract void paint(Graphics g);//paint����������,��Ϊ������Ϊ����ÿ�����嶼���Լ���paint����
	public abstract int getWidth();
	public abstract int getHeight();
	
}
