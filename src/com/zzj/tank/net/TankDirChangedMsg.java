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

public class TankDirChangedMsg extends Msg {
	
	UUID id;
	int x, y;
	
	Dir dir;
	
	public TankDirChangedMsg(){
		
	}
	
	public TankDirChangedMsg(UUID id, int x, int y, Dir dir){
		super();
		this.id = id;
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public TankDirChangedMsg(Tank tank){
		this.id = tank.getId();
		this.x = tank.x;
		this.y = tank.y;
		this.dir = tank.getDir();
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

	public Dir getDir() {
		return dir;
	}

	@Override
	public void handle() {
		if(this.id.equals(TankFrame.INSTANCE.getMainTank().getId()))
			return;
		Tank t = TankFrame.INSTANCE.findByUUID(this.id);
		if(t !=null){
			t.setMoving(true);
			t.setX(this.x);
			t.setY(this.y);
			t.setDir(this.dir);		
		}
	}

	@Override
	public byte[] toBytes() {
		ByteArrayOutputStream baos = null;
		DataOutputStream dos = null;
		byte[] bytes = null;

		try {
			baos = new ByteArrayOutputStream();
			dos = new DataOutputStream(baos);//�����������д
			
			//����Ϣд��
			dos.writeLong(id.getMostSignificantBits());//��ΪUUID��128λ�����������64λд����
			dos.writeLong(id.getLeastSignificantBits());//����д��64λ
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(dir.ordinal());//ö����������Ե������飬����ǰ��ֵ���±괫��
			
			
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
			this.dir = Dir.values()[dis.readInt()];
			
			
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
		return MsgType.TankStartMoving;
	}

}
