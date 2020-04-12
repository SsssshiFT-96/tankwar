package com.zzj.tank.abstractfactory;

import com.zzj.tank.Dir;
import com.zzj.tank.Group;
import com.zzj.tank.TankFrame;

//利用工厂模式对坦克进行生产


public abstract class GameFactory {
	public abstract BaseTank createTank(int x, int y, 
			Dir dir, Group group, TankFrame tf);
	public abstract BaseExplode createExplode(int x, int y, 
			TankFrame tf);
	public abstract BaseBullet createBullet(int x, int y, 
			Dir dir, Group group, TankFrame tf);
	
}
