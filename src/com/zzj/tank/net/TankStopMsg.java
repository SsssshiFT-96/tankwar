package com.zzj.tank.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

import com.zzj.tank.Dir;
import com.zzj.tank.Group;
import com.zzj.tank.Tank;
import com.zzj.tank.TankFrame;

public class TankStopMsg extends Msg {
	
	UUID id;
	int x, y;
	public TankStopMsg(){
		
	}
	
	public TankStopMsg(UUID id, int x, int y, Dir dir){
		this.id = id;
		this.x = x;
		this.y = y;
	}
	
	public TankStopMsg(Tank tank){
		this.id = tank.getId();
		this.x = tank.getX();
		this.y = tank.getY();

	}

	public UUID getId() {
		return id;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public void handle() {
		if(this.id.equals(TankFrame.INSTANCE.getMainTank().getId()))
			return;
		Tank t = TankFrame.INSTANCE.findByUUID(this.id);
		if(t !=null){
			t.setMoving(false);
			t.setX(this.x);
			t.setY(this.y);	
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
			dos.writeLong(id.getMostSignificantBits());//因为UUID是128位。所以这里高64位写出来
			dos.writeLong(id.getLeastSignificantBits());//这里写低64位
			dos.writeInt(x);
			dos.writeInt(y);
			
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
			this.id = new UUID(dis.readLong(), dis.readLong());
			this.x = dis.readInt();
			this.y = dis.readInt();
			
			
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
		return MsgType.TankStop;
	}

}
