package com.zzj.tank.cor;

import java.util.LinkedList;
import java.util.List;

import com.zzj.tank.GameObject;

//用责任链将每个Collider串起来，这样当进行碰撞检测时，先检测是否满足第一个collider，不满足就检测链上的第二个collider。
//ColliderChain也实现Collider接口的话，两个chain可以并成一个chain，即chain1.add(chain2);
public class ColliderChain implements Collider{
	//用链表来存储每个Collider
	private List<Collider> colliders = new LinkedList<>();
	public ColliderChain(){
		add(new BulletTankCollider());
		add(new TankTankCollider());
		add(new BulletWallCollider());
		add(new TankWallCollider());
	}
	
	
	public void add(Collider c){
		colliders.add(c);
	}

	//判断使用哪个Collider
	public boolean collide(GameObject o1, GameObject o2) {
		// TODO Auto-generated method stub
		for(int i = 0; i < colliders.size(); i++){
			if(!colliders.get(i).collide(o1, o2)){
				return false;
			}
		}
		return true;
	}
}
