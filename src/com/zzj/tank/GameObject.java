package com.zzj.tank;

import java.awt.Graphics;
import java.io.Serializable;

//���￪ʼʹ�õ�ͣ��ģʽ
//����һ����Ϸ�����࣬������Ϊ������Ϸ����ĸ��ࡣ�������ж����������ʱ���̳и���
//ʵ��Serializable�ӿڣ�ʹ����������ܹ������л�
public abstract class GameObject implements Serializable{
	public int x;//λ��
	public int y;
	
	public abstract void paint(Graphics g);//paint����������,��Ϊ������Ϊ����ÿ�����嶼���Լ���paint����
	public abstract int getWidth();
	public abstract int getHeight();
	
}
