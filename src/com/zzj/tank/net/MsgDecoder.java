package com.zzj.tank.net;

import java.util.List;
import java.util.UUID;

import com.zzj.tank.Dir;
import com.zzj.tank.Group;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
//解码器
public class MsgDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		//判断是否读全，读全才处理。这里解决了TCP的拆包和粘包的问题
//		if(in.readableBytes() < 33) return;
		//Type类型4个字节，消息长度4个字节
		if(in.readableBytes() < 8) return;
		
		//读到哪了，做个标记。
		in.markReaderIndex();
		
		MsgType msgType = MsgType.values()[in.readInt()];		
		int length = in.readInt();
		
		//若可读的部分小于length，表明消息没有完全传过来
		if(in.readableBytes() < length){
			//将指针回到最早的标记好的位置，也就是0的位置
			in.resetReaderIndex();
			return;
		}
		
		byte[] bytes = new byte[length];
		in.readBytes(bytes);
		
		Msg msg = null;
		//使用反射代替switch
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
		
		msg.parse(bytes);//根据bytes将各个属性填写好
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
