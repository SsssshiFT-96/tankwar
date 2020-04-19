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
	//创建一个通道组,将连接通道都存在这个组中。
	public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	public void serverStart() throws Exception {
		//负责接收客户端的连接
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		//负责连接上的客户端的处理，比如一些IO事件
		EventLoopGroup workerGroup = new NioEventLoopGroup(2);
		
		try {
			ServerBootstrap b = new ServerBootstrap();
			ChannelFuture f = b.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)//指定channel类型
			//childHandler就是用于当连接的客户端上发生事件时，进行处理
			.childHandler(new ChannelInitializer<SocketChannel>(){
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					// TODO Auto-generated method stub
//					System.out.println(ch);
					ChannelPipeline p1 = ch.pipeline();//获得该channel的责任链
					p1.addLast(new ServerChildHandler());//往责任链上增加Handler，
														//一旦往这个上面写数据，则用Handler来处理。
				}
			})
			.bind(8888)//监听8888端口
			.sync();//用于判断bind是否成功进行阻塞。若没有阻塞则还没判断就进行下面操作。
//			System.out.println("server started");
			ServerFrame.INSTANCE.updateServerMsg("server started");
			f.channel().closeFuture().sync();//起到阻塞作用。
											//closeFuture()是等待channel调用close，然后这句话才会往下执行。
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
//ChannelInboundHandlerAdapter也继承了ChannelHandler，
//所以ServerChildHandler也继承了ChannelHandler
class ServerChildHandler extends ChannelInboundHandlerAdapter{

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Server.clients.add(ctx.channel());
	}

	@Override
	//用来读客户端写进来的数据
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = null; 
		try{
			buf = (ByteBuf)msg;
			//获得字节数组
			byte[] bytes = new byte[buf.readableBytes()];
			buf.getBytes(buf.readerIndex(),bytes);
			String s = new String(bytes);
			
			//将接收到的信息显示在frame的右边
			ServerFrame.INSTANCE.updateClientMsg(s);
			//判断是否是_bye_，若是则将该客户端退出，若不是则进行发送
			if(s.equals("_bye_")){
//				System.out.println("客户端要求退出");
				ServerFrame.INSTANCE.updateClientMsg("客户端要求退出");
				Server.clients.remove(ctx.channel());
				ctx.close();
			}else{
				Server.clients.writeAndFlush(msg);
			}
			System.out.println(new String(bytes));
//			System.out.println(buf);
//			System.out.println(buf.refCnt());//有多少引用。
			
			//将msg写回去
//			ctx.writeAndFlush(msg);
			
			//将接收到的数据写给之前定义的通道组内的所有通道
//			Server.clients.writeAndFlush(msg);
			
		}finally{
			//将buf释放，否则会造成内存泄露。
//			if(buf != null) ReferenceCountUtil.release(buf);
//			System.out.println(buf.refCnt());
		}
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		//删除出现异常的客户端channel，并关闭连接
		Server.clients.remove(ctx.channel());
		ctx.close();
	}
	
}
