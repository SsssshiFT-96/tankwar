package com.zzj.tank.abstractfactory;

import com.zzj.tank.Dir;
import com.zzj.tank.Group;
import com.zzj.tank.TankFrame;

public class RectFactory extends GameFactory {

	@Override
	public BaseTank createTank(int x, int y, Dir dir, 
			Group group, TankFrame tf) {
		// TODO Auto-generated method stub
		return new RectTank(x, y, dir, group, tf);
	}

	@Override
	public BaseExplode createExplode(int x, int y, TankFrame tf) {
		// TODO Auto-generated method stub
		return new RectExplode(x, y ,tf);
	}

	@Override
	public BaseBullet createBullet(int x, int y, Dir dir, 
			Group group, TankFrame tf) {
		// TODO Auto-generated method stub
		return new RectBullet(x, y, dir, group, tf);
	}

}
