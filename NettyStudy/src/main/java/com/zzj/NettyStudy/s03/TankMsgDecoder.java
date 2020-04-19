package com.zzj.NettyStudy.s03;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
//������
public class TankMsgDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		//�ж��Ƿ��ȫ����ȫ�Ŵ�����������TCP�Ĳ����ճ��������
		if(in.readableBytes() < 8) return;
		
//		in.markReaderIndex();
		//�ȶ�x�ٶ�y
		int x = in.readInt();
		int y = in.readInt();
		//�����������Ķ���װ��list��ȥ
		out.add(new TankMsg(x, y));
		

	}

}
