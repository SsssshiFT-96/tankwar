package com.zzj.tank.net;


import com.zzj.tank.Tank;
import com.zzj.tank.TankFrame;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;

public class Client {
	public static final Client INSTANCE = new Client();
	private Channel channel = null;
	private Client(){};
	public void connect(){
		//线程池，循环事件不断处理的组。默认值很多，客户端的话传一个线程进去即可
		EventLoopGroup group = new NioEventLoopGroup(1);//线程封装在此
		
		Bootstrap b = new Bootstrap();//辅助启动类
		
		try {
			//连接
//			b.group(group)//将线程池放入
//				.channel(NioSocketChannel.class)//指定将来连接到服务器的channel的类型，
//												//这里是NIO的channel类型，非阻塞版
//				//在channel上发生事件，交给handler来处理
//				.handler(new ClientChannelInitializer())//调用connect之后，handler才开始执行，才开始初始化。
//				.connect("localhost", 8888)//连得远程服务器的id和端口号
////				.addListener(null)//加入监听者，连接结束后判断是否成功
//				.sync();//netty里面的方法均为异步方法，所以connect是异步方法，
//						//执行完之后就可以去做别的事，若要等着connect执行完，那么就需要用sync()方法。
//						//这调用的是future的sync。不能让其继续执行，
//						//是使之成为同步本来在connect后返回的是一个future对象。若没有sync方法，则用下面的形式
//						//sync是将其阻塞住，不让其再继续下去。
			
			  ChannelFuture f = b.group(group)
				.channel(NioSocketChannel.class)
				.handler(new ClientChannelInitializer())
				.connect("localhost", 8888);
				f.addListener(new ChannelFutureListener(){
					@Override
					public void operationComplete(ChannelFuture future) throws Exception {
						if(!future.isSuccess()){
							System.out.println("not connected");
						}else{
							System.out.println("connected");
							//连接成功将channel初始化
							channel = future.channel();
						}
					}
				});
				f.sync();				
			 
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			group.shutdownGracefully();//结束客户端
		}
	}
	
//	public void send(String msg){
//		//将写好的字符串msg转成ByteBuf，通过channel并写给服务器
//		ByteBuf buf = Unpooled.copiedBuffer(msg.getBytes());
//		channel.writeAndFlush(buf);
//	}
	public void send(Msg msg){
		//将写好的字符串msg转成ByteBuf，通过channel并写给服务器
		channel.writeAndFlush(msg);
	}
	
	
//	public static void main(String[] args) throws Exception{
//		Client c = new Client();
//		c.connect();
//		
//	}
	public void closeConnect(){
//		this.send("_bye_");
	}
	
	
	
}
class ClientChannelInitializer extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline()
			.addLast(new MsgEncoder())//将msg编码发出去
			.addLast(new MsgDecoder())//用于对接收的数据解码
			.addLast(new ClientHandler());
			//.addLast(new ClientChildHandler());
	}	
}

//若接收到的消息类型确定，就可以继承这个类
/*
 接收坦克的逻辑处理
 1.判断是不是自己
 2.列表是不是已经有了
 3.发自己的一个TankJoinMsg，是为了发给新加入的客户端，使之产生本客户端的对象。
 */
class ClientHandler extends SimpleChannelInboundHandler<Msg>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
		//将接收到的消息自己进行处理
		msg.handle();
		
		//判断接收到的坦克是否是自己的坦克或者是列表中存在的坦克
//		if(msg.id.equals(TankFrame.INSTANCE.getMainTank().getId()) ||
//				TankFrame.INSTANCE.findByUUID(msg.id)!= null) return;
////		System.out.println(111);
//		//通过msg初始化一个坦克，然后将坦克加入列表
//		Tank t = new Tank(msg);
//		TankFrame.INSTANCE.addTank(t);
//		//将自己的坦克信息传出去
//		ctx.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.getMainTank()));
		
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.getMainTank()));
	}
	
}

class ClientChildHandler extends ChannelInboundHandlerAdapter{

	@Override
	//用来读服务端写进来的数据
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = null; 
		try{
			buf = (ByteBuf)msg;
			//获得字节数组
			byte[] bytes = new byte[buf.readableBytes()];
			buf.getBytes(buf.readerIndex(),bytes);
//			System.out.println(new String(bytes));
			//将从服务器端得到的字节数组转换成字符串，通过调用frame将其显示
			String msgAccepted = new String(bytes);
//			ClientFrame.INSTANCE.updateText(msgAccepted);
//			System.out.println(buf);
//			System.out.println(buf.refCnt());//有多少引用。
			
			//将msg写回去
//			ctx.writeAndFlush(msg);
		}finally{
			//将buf释放，否则会造成内存泄露。
			if(buf != null) ReferenceCountUtil.release(buf);
//			System.out.println(buf.refCnt());
		}
		
	}
	@Override
	//这个Handler启用了，就可以往外写数据
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
		//channel第一个连上可用，写出一个字符串。将字符串转成ByteBuf
//		ByteBuf buf = Unpooled.copiedBuffer("hello".getBytes());
//		ctx.writeAndFlush(buf);
		//因为有了TankMsgEncoder，所以可以直接写TankMsg然后通过编码写出去。
//		ctx.writeAndFlush(new TankJoinMsg(5, 8));
		
		ctx.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.getMainTank()));
	
	}
	
		
}
