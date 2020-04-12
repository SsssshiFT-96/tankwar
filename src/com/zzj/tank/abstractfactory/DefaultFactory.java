package com.zzj.tank.abstractfactory;

import com.zzj.tank.Bullet;
import com.zzj.tank.Dir;
import com.zzj.tank.Explode;
import com.zzj.tank.Group;
import com.zzj.tank.Tank;
import com.zzj.tank.TankFrame;

public class DefaultFactory extends GameFactory{
	@Override
	public BaseTank createTank(int x, int y, 
			Dir dir, Group group, TankFrame tf){
		return new Tank(x, y, dir, group, tf);//new Tank(x, y, dir, group, tf);
	}
	@Override
	public BaseExplode createExplode(int x, int y, 
			TankFrame tf){
		return new Explode(x, y ,tf);
	}
	
	@Override
	public BaseBullet createBullet(int x, int y, 
			Dir dir, Group group, TankFrame tf){
		return new Bullet(x, y, dir, group, tf);
	}

}
