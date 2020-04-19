package com.zzj.NettyStudy.s03;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
//解码器
public class TankMsgDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		//判断是否读全，读全才处理。这里解决了TCP的拆包和粘包的问题
		if(in.readableBytes() < 8) return;
		
//		in.markReaderIndex();
		//先读x再读y
		int x = in.readInt();
		int y = in.readInt();
		//将解析出来的对象装到list中去
		out.add(new TankMsg(x, y));
		

	}

}
