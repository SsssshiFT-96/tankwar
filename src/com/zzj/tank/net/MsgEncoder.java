package com.zzj.tank.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
//������
//���ȸ���дһ����Ϣͷ������Ϣ�����ͼ�MsgType���ڶ�������Ϣ�ĳ��ȣ�����������Ϣ�壻
public class MsgEncoder extends MessageToByteEncoder<Msg> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Msg msg, ByteBuf buf) throws Exception {
//		buf.writeBytes(msg.toBytes());
		buf.writeInt(msg.getMsgType().ordinal());//��Ϣ����
		byte[] bytes = msg.toBytes();//����Ϣ�����ֽ�����
		buf.writeInt(bytes.length);//��Ϣ�ĳ���Ҳ�����ֽ�����ĳ���
		buf.writeBytes(bytes);//��Ϣ��
	}

}
