package com.zzj.tank.strategy;

import com.zzj.tank.Bullet;
import com.zzj.tank.Dir;
import com.zzj.tank.GameModel;
import com.zzj.tank.Tank;

public class FourDirFireStrategy implements FireStrategy{
	@Override
	public void fire(Tank t){
		int bX = t.x + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
		int bY = t.y + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;
		
		Dir[] dirs = Dir.values();
		for(Dir dir : dirs){
			new Bullet(bX, bY, dir, t.group /*, GameModel.getInstance()/*t.gm*/);
		}
		
	}
}
