package com.zzj.tank.net;
//创建消息的类型，如坦克加入，坦克方向改变等等
public enum MsgType {
	TankJoin, TankDirChanged, TankStop, TankStartMoving, BulletNew, TankDie;
}
