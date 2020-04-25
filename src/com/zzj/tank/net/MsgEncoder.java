package com.zzj.tank.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
//编码器
//首先给包写一个信息头，如信息的类型即MsgType；第二个是消息的长度；第三个是消息体；
public class MsgEncoder extends MessageToByteEncoder<Msg> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Msg msg, ByteBuf buf) throws Exception {
//		buf.writeBytes(msg.toBytes());
		buf.writeInt(msg.getMsgType().ordinal());//消息类型
		byte[] bytes = msg.toBytes();//将消息体变成字节数组
		buf.writeInt(bytes.length);//消息的长度也就是字节数组的长度
		buf.writeBytes(bytes);//消息体
	}

}
