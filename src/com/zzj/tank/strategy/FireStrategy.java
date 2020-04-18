package com.zzj.tank.strategy;

import java.io.Serializable;

import com.zzj.tank.Tank;

public interface FireStrategy extends Serializable{
	void fire(Tank t);
}
