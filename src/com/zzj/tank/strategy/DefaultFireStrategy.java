package com.zzj.tank.strategy;

import com.zzj.tank.Bullet;
import com.zzj.tank.GameModel;
import com.zzj.tank.Tank;
import com.zzj.tank.decorator.RectDecorator;
import com.zzj.tank.decorator.TailDecorator;

public class DefaultFireStrategy implements FireStrategy{
	@Override
	public void fire(Tank t){
		int bX = t.x + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
		int bY = t.y + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;
		
//		new Bullet(bX, bY, t.dir, t.group /*, GameModel.getInstance()/*t.gm*/);
		//ʹ��װ��ģʽװ���ӵ�
		//����һ��bug�����ǻ��������мӽ�ȥ�����ӵ���
		GameModel.getInstance().add(
				new RectDecorator(
						new TailDecorator(
						new Bullet(bX, bY, t.dir, t.group))));
	}
}
