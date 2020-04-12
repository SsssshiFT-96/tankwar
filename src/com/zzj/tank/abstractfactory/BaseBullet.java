package com.zzj.tank.abstractfactory;

import java.awt.Graphics;

import com.zzj.tank.Tank;

public abstract class BaseBullet {

	public abstract void collideWith(BaseTank baseTank);
	public abstract void paint(Graphics g);
}
