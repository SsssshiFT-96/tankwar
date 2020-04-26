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

public class TankDieMsg extends Msg {
	
	UUID bulletID;//记录谁的炮弹
	UUID id;
	
	public TankDieMsg(){
		
	}
	
	public TankDieMsg(UUID playerID, UUID id){
		this.id = id;
		this.bulletID = playerID;
	}
	
	@Override
	public void handle() {
//		System.out.println("we got a tank die" + id);
//		System.out.println("and my tank is:" + TankFrame.INSTANCE.getMainTank().getId());
		
		Tank tt = TankFrame.INSTANCE.findByUUID(id);
		Bullet b = TankFrame.INSTANCE.findBulletByUUID(bulletID);
		
		if(b != null){
			b.die();
		}
		//判断接收到的坦克是否是自己的坦克或者是列表中存在的坦克
		if(this.id.equals(TankFrame.INSTANCE.getMainTank().getId()))
			TankFrame.INSTANCE.getMainTank().die();
		else{
			Tank t = TankFrame.INSTANCE.findByUUID(id);
			if(t != null)
				t.die();
		}				
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
			dos.writeLong(bulletID.getMostSignificantBits());
			dos.writeLong(bulletID.getLeastSignificantBits());
			dos.writeLong(id.getMostSignificantBits());
			dos.writeLong(id.getLeastSignificantBits());
			
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
			this.bulletID = new UUID(dis.readLong(), dis.readLong());
			this.id = new UUID(dis.readLong(), dis.readLong());
			
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
		return MsgType.TankDie;
	}

}
