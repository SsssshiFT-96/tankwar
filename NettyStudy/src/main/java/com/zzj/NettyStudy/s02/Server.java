package com.zzj.NettyStudy.s02;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GlobalEventExecutor;



public class Server {
	//����һ��ͨ����,������ͨ��������������С�
	public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	public void serverStart() throws Exception {
		//������տͻ��˵�����
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		//���������ϵĿͻ��˵Ĵ�������һЩIO�¼�
		EventLoopGroup workerGroup = new NioEventLoopGroup(2);
		
		try {
			ServerBootstrap b = new ServerBootstrap();
			ChannelFuture f = b.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)//ָ��channel����
			//childHandler�������ڵ����ӵĿͻ����Ϸ����¼�ʱ�����д���
			.childHandler(new ChannelInitializer<SocketChannel>(){
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					// TODO Auto-generated method stub
//					System.out.println(ch);
					ChannelPipeline p1 = ch.pipeline();//��ø�channel��������
					p1.addLast(new ServerChildHandler());//��������������Handler��
														//һ�����������д���ݣ�����Handler������
				}
			})
			.bind(8888)//����8888�˿�
			.sync();//�����ж�bind�Ƿ�ɹ�������������û��������û�жϾͽ������������
//			System.out.println("server started");
			ServerFrame.INSTANCE.updateServerMsg("server started");
			f.channel().closeFuture().sync();//���������á�
											//closeFuture()�ǵȴ�channel����close��Ȼ����仰�Ż�����ִ�С�
		}finally{
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

//	public static void main(String[] args) throws IOException, Exception {
//		// TODO Auto-generated method stub
//		
//	}
}
//ChannelInboundHandlerAdapterҲ�̳���ChannelHandler��
//����ServerChildHandlerҲ�̳���ChannelHandler
class ServerChildHandler extends ChannelInboundHandlerAdapter{

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Server.clients.add(ctx.channel());
	}

	@Override
	//�������ͻ���д����������
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = null; 
		try{
			buf = (ByteBuf)msg;
			//����ֽ�����
			byte[] bytes = new byte[buf.readableBytes()];
			buf.getBytes(buf.readerIndex(),bytes);
			String s = new String(bytes);
			
			//�����յ�����Ϣ��ʾ��frame���ұ�
			ServerFrame.INSTANCE.updateClientMsg(s);
			//�ж��Ƿ���_bye_�������򽫸ÿͻ����˳�������������з���
			if(s.equals("_bye_")){
//				System.out.println("�ͻ���Ҫ���˳�");
				ServerFrame.INSTANCE.updateClientMsg("�ͻ���Ҫ���˳�");
				Server.clients.remove(ctx.channel());
				ctx.close();
			}else{
				Server.clients.writeAndFlush(msg);
			}
			System.out.println(new String(bytes));
//			System.out.println(buf);
//			System.out.println(buf.refCnt());//�ж������á�
			
			//��msgд��ȥ
//			ctx.writeAndFlush(msg);
			
			//�����յ�������д��֮ǰ�����ͨ�����ڵ�����ͨ��
//			Server.clients.writeAndFlush(msg);
			
		}finally{
			//��buf�ͷţ����������ڴ�й¶��
//			if(buf != null) ReferenceCountUtil.release(buf);
//			System.out.println(buf.refCnt());
		}
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		//ɾ�������쳣�Ŀͻ���channel�����ر�����
		Server.clients.remove(ctx.channel());
		ctx.close();
	}
	
}
