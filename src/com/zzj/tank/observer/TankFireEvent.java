package com.zzj.tank.observer;

import com.zzj.tank.Tank;

public class TankFireEvent {
	Tank tank;
	public Tank getSource(){
		return tank;
	}
	public TankFireEvent(Tank tank){
		this.tank = tank;
	}
}	
