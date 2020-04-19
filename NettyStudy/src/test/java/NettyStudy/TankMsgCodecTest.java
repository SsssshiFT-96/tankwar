package NettyStudy;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import com.zzj.NettyStudy.s03.TankMsg;
import com.zzj.NettyStudy.s03.TankMsgDecoder;
import com.zzj.NettyStudy.s03.TankMsgEncoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;

public class TankMsgCodecTest {

	@Test
	public void testTankMsgEncoder() {
		TankMsg msg = new TankMsg(10, 10);
		//new的时候直接把TankMsgEncoder加到channel上
		EmbeddedChannel ch = new EmbeddedChannel(new TankMsgEncoder());
		//通过EmbeddedChannel往外写一个msg
		ch.writeOutbound(msg);
		//将上面往外写数据读出来。
		ByteBuf buf = (ByteBuf)ch.readOutbound();
		int x = buf.readInt();
		int y = buf.readInt();
		
		Assert.assertTrue(x == 10 && y ==10);
		buf.release();
	}
	
	
	@Test
	public void testTankMsgEncoder2() {
		//往里写一个ByteBuf，由于这是ByteBuf，所以不会经过Encoder，找到DeCoder执行
		ByteBuf buf = Unpooled.buffer();
		TankMsg msg = new TankMsg(10, 10);
		buf.writeInt(msg.x);
		buf.writeInt(msg.y);
		
		
		//把TankMsgEncoder和TankMsgDecoder加到channel上
		//这里注意先经过decoder后，再经过encoder不会再编码，因为传进去的方向不同。
		EmbeddedChannel ch = new EmbeddedChannel(
				new TankMsgEncoder(), new TankMsgDecoder());
		//将转换好的ByteBuf写进来
		ch.writeInbound(buf.duplicate());
		//读进来
		TankMsg tm = (TankMsg)ch.readInbound();
		
		Assert.assertTrue(tm.x == 10 && tm.y ==10);
	}

}
