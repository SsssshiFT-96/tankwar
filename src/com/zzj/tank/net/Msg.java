package com.zzj.tank.net;
//创建msg抽象类，将所有消息都继承于此，这样每种消息都有自己处理的方法
public abstract class Msg {
	public abstract void handle();
	public abstract byte[] toBytes();
}
