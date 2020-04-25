package com.zzj.tank.net;

import java.util.List;
import java.util.UUID;

import com.zzj.tank.Dir;
import com.zzj.tank.Group;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
//解码器
public class TankJoinMsgDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		//判断是否读全，读全才处理。这里解决了TCP的拆包和粘包的问题
		if(in.readableBytes() < 33) return;
		
//		in.markReaderIndex();
		TankJoinMsg msg = new TankJoinMsg();
		
		msg.x = in.readInt();
		msg.y = in.readInt();
		msg.dir = Dir.values()[in.readInt()];
		msg.moving = in.readBoolean();
		msg.group = Group.values()[in.readInt()];
		msg.id = new UUID(in.readLong(), in.readLong());
		
		out.add(msg);
		

	}

}
