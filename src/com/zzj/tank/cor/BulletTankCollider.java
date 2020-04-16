package com.zzj.tank.cor;

import com.zzj.tank.Bullet;
import com.zzj.tank.Explode;
import com.zzj.tank.GameObject;
import com.zzj.tank.Tank;

public class BulletTankCollider implements Collider{
	@Override
	public boolean collide(GameObject o1, GameObject o2){
		if(o1 instanceof Bullet && o2 instanceof Tank){
			Bullet b = (Bullet) o1;
			Tank t = (Tank) o2;
			if(b.group == t.getGroup()) return false;
			//��ȡ�ӵ���̹����ͬ��С�ľ��κ���λ��
			//TODO:��һ��rect����¼�ӵ���λ��
			//�ж��Ƿ��ཻ���ཻ����ʧ,��ʧ������ը
			if(b.rect.intersects(t.rect)){
				t.die();
				b.die();
				int eX = t.getX() + Tank.WIDTH / 2 - Explode.WIDTH / 2;
				int eY = t.getY() + Tank.HEIGHT / 2 - Explode.HEIGHT / 2;
//				t.gm.add(new Explode(eX, eY, t.gm)); 
				new Explode(eX, eY);
				return false;
			}
			
		}else if(o1 instanceof Tank && o2 instanceof Bullet){
			return collide(o2, o1);
		}
		return true;
		
	}

}
