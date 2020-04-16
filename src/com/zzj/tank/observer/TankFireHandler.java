package com.zzj.tank.observer;

import com.zzj.tank.Tank;

public class TankFireHandler implements TankFireObserver{
	@Override
	public void actionOnFire(TankFireEvent e){
		Tank t = e.getSource();
		t.fire();
	}
}
