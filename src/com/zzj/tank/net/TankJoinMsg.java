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

public class TankJoinMsg extends Msg{
	public int x, y;
	public Dir dir;
	public boolean moving;
	public Group group;
	public UUID id;//UUID是一个128位的数字，通过专门的算法生成，独一无二的
	public TankJoinMsg(Tank t){
		this.x = t.x;
		this.y = t.y;
		this.dir = t.getDir();
		this.group = t.getGroup();
		this.id = t.getId();
		this.moving = t.isMoving();
	}
	public TankJoinMsg(int x, int y, Dir dir, boolean moving, Group group, UUID id){
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.group = group;
		this.id = id;
		this.moving = moving;
	}
	
	public TankJoinMsg(){
		
	}
	//根据bytes填写好属性
	public void parse(byte[] bytes){
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
		try{
			this.x = dis.readInt();
			this.y = dis.readInt();
			this.dir = Dir.values()[dis.readInt()];
			this.moving = dis.readBoolean();
			this.group = Group.values()[dis.readInt()];
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
	//将整个消息转化成byte数组
	//这里不用ByteBuf的原因是防止哪天不用netty也能照常使用。
	@Override
	public byte[] toBytes(){
		ByteArrayOutputStream baos = null;
		DataOutputStream dos = null;
		byte[] bytes = null;

		try {
			baos = new ByteArrayOutputStream();
			dos = new DataOutputStream(baos);//用这个来往外写
			
			//将信息写入
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(dir.ordinal());//枚举类里面可以当做数组，将当前数值的下标传入
			dos.writeBoolean(moving);//布尔类型往外写是一个字节
			dos.writeInt(group.ordinal());
			dos.writeLong(id.getMostSignificantBits());//因为UUID是128位。所以这里高64位写出来
			dos.writeLong(id.getLeastSignificantBits());//这里写低64位
			
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
	public String toString() {
		return "TankMsg" + x + "," + y;
	}
	@Override
	public void handle() {
		//判断接收到的坦克是否是自己的坦克或者是列表中存在的坦克
		if(this.id.equals(TankFrame.INSTANCE.getMainTank().getId()) ||
				TankFrame.INSTANCE.findByUUID(this.id)!= null) return;
//		System.out.println(111);
		//通过msg初始化一个坦克，然后将坦克加入列表
		Tank t = new Tank(this);
		TankFrame.INSTANCE.addTank(t);
		//将自己的坦克信息传出去
		Client.INSTANCE.send(new TankJoinMsg(TankFrame.INSTANCE.getMainTank()));
		
	}
	//返回消息的类型

	public MsgType getMsgType() {
		return MsgType.TankJoin;
	}
	
	
}