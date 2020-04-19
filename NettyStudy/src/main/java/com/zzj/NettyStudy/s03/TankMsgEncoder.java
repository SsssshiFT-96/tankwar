package com.zzj.NettyStudy.s03;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
//±àÂëÆ÷
public class TankMsgEncoder extends MessageToByteEncoder<TankMsg> {

	@Override
	protected void encode(ChannelHandlerContext ctx, TankMsg msg, ByteBuf buf) throws Exception {
		buf.writeInt(msg.x);
		buf.writeInt(msg.y);
		
	}

}
