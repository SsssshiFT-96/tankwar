package com.zzj.NettyStudy.s01;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;

//Netty�Զ�ʹ�ö��߳�
public class Client {
	public static void main(String[] args){
		//�̳߳أ�ѭ���¼����ϴ�����顣Ĭ��ֵ�ܶ࣬�ͻ��˵Ļ���һ���߳̽�ȥ����
		EventLoopGroup group = new NioEventLoopGroup(1);//�̷߳�װ�ڴ�
		
		Bootstrap b = new Bootstrap();//����������
		
		try {
			//����
//			b.group(group)//���̳߳ط���
//				.channel(NioSocketChannel.class)//ָ���������ӵ���������channel�����ͣ�
//												//������NIO��channel���ͣ���������
//				//��channel�Ϸ����¼�������handler������
//				.handler(new ClientChannelInitializer())//����connect֮��handler�ſ�ʼִ�У��ſ�ʼ��ʼ����
//				.connect("localhost", 8888)//����Զ�̷�������id�Ͷ˿ں�
////				.addListener(null)//��������ߣ����ӽ������ж��Ƿ�ɹ�
//				.sync();//netty����ķ�����Ϊ�첽����������connect���첽������
//						//ִ����֮��Ϳ���ȥ������£���Ҫ����connectִ���꣬��ô����Ҫ��sync()������
//						//����õ���future��sync�������������ִ�У�
//						//��ʹ֮��Ϊͬ��������connect�󷵻ص���һ��future������û��sync�����������������ʽ
//						//sync�ǽ�������ס���������ټ�����ȥ��
			
			  ChannelFuture f = b.group(group)
				.channel(NioSocketChannel.class)
				.handler(new ClientChannelInitializer())
				.connect("localhost", 8888);
				f.addListener(new ChannelFutureListener(){
					@Override
					public void operationComplete(ChannelFuture future) throws Exception {
						if(!future.isSuccess()){
							System.out.println("aaa");
						}else{
							System.out.println("bbb");
						}
					}
				});
				f.sync();				
			 
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			group.shutdownGracefully();//�����ͻ���
		}
	}
}
//ClientChannelInitializer�̳���ChannelInitializer��
//channel�ĳ�ʼ������ʵ����һ��channelHandler�����Կ�����handler���洫
class ClientChannelInitializer extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		//pipeline��channelͨ���ϸ���handler��������Ҳ����handler��������
		ch.pipeline().addLast(new ClientChildHandler());
		
	}	
}
class ClientChildHandler extends ChannelInboundHandlerAdapter{

	@Override
	//�����������д����������
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = null; 
		try{
			buf = (ByteBuf)msg;
			//����ֽ�����
			byte[] bytes = new byte[buf.readableBytes()];
			buf.getBytes(buf.readerIndex(),bytes);
			System.out.println(new String(bytes));
//			System.out.println(buf);
//			System.out.println(buf.refCnt());//�ж������á�
			
			//��msgд��ȥ
//			ctx.writeAndFlush(msg);
		}finally{
			//��buf�ͷţ����������ڴ�й¶��
			if(buf != null) ReferenceCountUtil.release(buf);
//			System.out.println(buf.refCnt());
		}
		
	}
	@Override
	//���Handler�����ˣ��Ϳ���д����
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
		//channel��һ�����Ͽ��ã�д��һ���ַ��������ַ���ת��ByteBuf
		ByteBuf buf = Unpooled.copiedBuffer("hello".getBytes());
		//BytebufЧ�ʸߡ���Ϊ�����������ֱ�ӷ��ʲ���ϵͳ���ڴ棬��ֱ���ڴ档
		//����һ����������������������ջ��ơ�
		//netty������д���������ݱ�����ByteBuf
		//��Ϊnetty�����ݵ�ʱ��msg����Bytebuf
		//��buf����д�벢ˢ�£�Ȼ���ͷ��ڴ档
		ctx.writeAndFlush(buf);
	}
		
}



