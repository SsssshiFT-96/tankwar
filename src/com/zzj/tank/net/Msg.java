package com.zzj.tank.net;
//����msg�����࣬��������Ϣ���̳��ڴˣ�����ÿ����Ϣ�����Լ�����ķ���
public abstract class Msg {
	public abstract void handle();
	public abstract byte[] toBytes();
}
