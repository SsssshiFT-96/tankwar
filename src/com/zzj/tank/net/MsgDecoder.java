package com.zzj.tank.net;

import java.util.List;
import java.util.UUID;

import com.zzj.tank.Dir;
import com.zzj.tank.Group;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
//������
public class MsgDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		//�ж��Ƿ��ȫ����ȫ�Ŵ�����������TCP�Ĳ����ճ��������
//		if(in.readableBytes() < 33) return;
		//Type����4���ֽڣ���Ϣ����4���ֽ�
		if(in.readableBytes() < 8) return;
		
		//�������ˣ�������ǡ�
		in.markReaderIndex();
		
		MsgType msgType = MsgType.values()[in.readInt()];		
		int length = in.readInt();
		
		//���ɶ��Ĳ���С��length��������Ϣû����ȫ������
		if(in.readableBytes() < length){
			//��ָ��ص�����ı�Ǻõ�λ�ã�Ҳ����0��λ��
			in.resetReaderIndex();
			return;
		}
		
		byte[] bytes = new byte[length];
		in.readBytes(bytes);
		
		Msg msg = null;
		//ʹ�÷������switch
//		msg = (Msg) Class.forName("com.zzj.tank.net" + msgType.toString() + "Msg").
//		getDeclaredConstructor().newInstance();	
		
		
		switch(msgType){
		case TankJoin:
			msg = new TankJoinMsg();
			break;
		case TankStartMoving:
			msg = new TankStartMovingMsg();
			break;
		case TankStop:
			msg = new TankStopMsg();
			break;
		case BulletNew:
			msg = new BulletNewMsg();
			break;
		case TankDie:
			msg = new TankDieMsg();
			break;
		case TankDirChanged:
			msg = new TankDirChangedMsg();
			break;
		default:
			break;
		}
		
		msg.parse(bytes);//����bytes������������д��
		out.add(msg);
		
		
//		TankJoinMsg msg = new TankJoinMsg();
//		
//		msg.x = in.readInt();
//		msg.y = in.readInt();
//		msg.dir = Dir.values()[in.readInt()];
//		msg.moving = in.readBoolean();
//		msg.group = Group.values()[in.readInt()];
//		msg.id = new UUID(in.readLong(), in.readLong());
//		
//		out.add(msg);
		

	}

}
