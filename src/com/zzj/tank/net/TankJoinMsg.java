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
	public UUID id;//UUID��һ��128λ�����֣�ͨ��ר�ŵ��㷨���ɣ���һ�޶���
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
	//����bytes��д������
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
	//��������Ϣת����byte����
	//���ﲻ��ByteBuf��ԭ���Ƿ�ֹ���첻��nettyҲ���ճ�ʹ�á�
	@Override
	public byte[] toBytes(){
		ByteArrayOutputStream baos = null;
		DataOutputStream dos = null;
		byte[] bytes = null;

		try {
			baos = new ByteArrayOutputStream();
			dos = new DataOutputStream(baos);//�����������д
			
			//����Ϣд��
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(dir.ordinal());//ö����������Ե������飬����ǰ��ֵ���±괫��
			dos.writeBoolean(moving);//������������д��һ���ֽ�
			dos.writeInt(group.ordinal());
			dos.writeLong(id.getMostSignificantBits());//��ΪUUID��128λ�����������64λд����
			dos.writeLong(id.getLeastSignificantBits());//����д��64λ
			
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
		//�жϽ��յ���̹���Ƿ����Լ���̹�˻������б��д��ڵ�̹��
		if(this.id.equals(TankFrame.INSTANCE.getMainTank().getId()) ||
				TankFrame.INSTANCE.findByUUID(this.id)!= null) return;
//		System.out.println(111);
		//ͨ��msg��ʼ��һ��̹�ˣ�Ȼ��̹�˼����б�
		Tank t = new Tank(this);
		TankFrame.INSTANCE.addTank(t);
		//���Լ���̹����Ϣ����ȥ
		Client.INSTANCE.send(new TankJoinMsg(TankFrame.INSTANCE.getMainTank()));
		
	}
	//������Ϣ������

	public MsgType getMsgType() {
		return MsgType.TankJoin;
	}
	
	
}