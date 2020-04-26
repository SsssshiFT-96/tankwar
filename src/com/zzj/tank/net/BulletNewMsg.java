package com.zzj.tank.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

import com.zzj.tank.Bullet;
import com.zzj.tank.Dir;
import com.zzj.tank.Group;
import com.zzj.tank.Tank;
import com.zzj.tank.TankFrame;

public class BulletNewMsg extends Msg {
	
	UUID id;
	UUID playerID;
	int x, y;
	Dir dir;
	Group group;
	
	public BulletNewMsg(){
		
	}
	
	public BulletNewMsg(Bullet b){
		this.id = b.getId();
		this.playerID = b.getPlayerID();
		this.x = b.getX();
		this.y =b.getY();
		this.dir = b.getDir();
		this.group = b.getGroup();
	}
	
	@Override
	public void handle() {
		//判断接收到的坦克是否是自己的坦克或者是列表中存在的坦克
		if(this.playerID.equals(TankFrame.INSTANCE.getMainTank().getId()))
			return;
//		System.out.println(111);

		Bullet bullet = new Bullet(this.playerID, x, y, dir,
				group, TankFrame.INSTANCE);
		bullet.setId(this.id);

		TankFrame.INSTANCE.addBullet(bullet);
				

	}

	@Override
	public byte[] toBytes() {
		ByteArrayOutputStream baos = null;
		DataOutputStream dos = null;
		byte[] bytes = null;

		try {
			baos = new ByteArrayOutputStream();
			dos = new DataOutputStream(baos);//用这个来往外写
			
			//将信息写入
			dos.writeLong(this.playerID.getMostSignificantBits());
			dos.writeLong(this.playerID.getLeastSignificantBits());
			dos.writeLong(id.getMostSignificantBits());
			dos.writeLong(id.getLeastSignificantBits());
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(dir.ordinal());
			dos.writeInt(group.ordinal());
			
			dos.flush();
			bytes = baos.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			try {
				if(baos != null){
				baos.close();	
				}
					
			} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
			try {
				if(dos != null){
				dos.close();	
				}
					
			} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
		}
		return bytes;
	}

	@Override
	public void parse(byte[] bytes) {
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
		try{
			this.playerID = new UUID(dis.readLong(), dis.readLong());
			this.id = new UUID(dis.readLong(), dis.readLong());
			this.x = dis.readInt();
			this.y = dis.readInt();
			this.dir = Dir.values()[dis.readInt()];
			this.group = Group.values()[dis.readInt()];

			
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try {
				dis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public MsgType getMsgType() {
		return MsgType.BulletNew;
	}

}
