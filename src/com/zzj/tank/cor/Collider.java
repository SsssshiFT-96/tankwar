package com.zzj.tank.cor;

import com.zzj.tank.GameObject;

//碰撞器
public interface Collider {
	//加上boolean返回值是为了若这次碰撞器判断对了，就不必再进行后面的碰撞器判断。
	//若判断成功，则返回false
	boolean collide(GameObject o1, GameObject o2);//负责o1和o2两个物体碰撞
}
