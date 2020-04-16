package com.zzj.tank.cor;

import java.util.LinkedList;
import java.util.List;

import com.zzj.tank.GameObject;

//����������ÿ��Collider��������������������ײ���ʱ���ȼ���Ƿ������һ��collider��������ͼ�����ϵĵڶ���collider��
//ColliderChainҲʵ��Collider�ӿڵĻ�������chain���Բ���һ��chain����chain1.add(chain2);
public class ColliderChain implements Collider{
	//���������洢ÿ��Collider
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

	//�ж�ʹ���ĸ�Collider
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
