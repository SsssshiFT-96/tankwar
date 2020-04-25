package test;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;

import com.zzj.tank.Dir;
import com.zzj.tank.Group;
import com.zzj.tank.net.MsgDecoder;
import com.zzj.tank.net.MsgEncoder;
import com.zzj.tank.net.MsgType;
import com.zzj.tank.net.TankJoinMsg;
import com.zzj.tank.net.TankStartMovingMsg;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;

public class TankStartMovingMsgCodeTest {
	@Test
	public void TestEncoder(){
		EmbeddedChannel ch = new EmbeddedChannel();
		
		UUID id = UUID.randomUUID();
		TankStartMovingMsg msg = new TankStartMovingMsg(id, 5, 10,
				Dir.DOWN);
		ch.pipeline()
			.addLast(new MsgEncoder());
		ch.writeOutbound(msg);
		
		ByteBuf buf = (ByteBuf)ch.readOutbound();
		MsgType msgType = MsgType.values()[buf.readInt()];
		assertEquals(MsgType.TankStartMoving, msgType);
		
		int length = buf.readInt();
		assertEquals(28, length);
		
		UUID uuid = new UUID(buf.readLong(), buf.readLong());
		int x = buf.readInt();
		int y = buf.readInt();
		int dirOrdinal = buf.readInt();
		Dir dir = Dir.values()[dirOrdinal];
		
		
		assertEquals(id, uuid);
		assertEquals(5, x);
		assertEquals(10, y);
		assertEquals(Dir.DOWN, dir);
		
	}
	@Test
	public void TestDecoder(){
		EmbeddedChannel ch = new EmbeddedChannel();
		
		UUID id = UUID.randomUUID();
		TankStartMovingMsg msg = new TankStartMovingMsg(id, 5, 
				10, Dir.DOWN);
		ch.pipeline()
			.addLast(new MsgDecoder());
		ByteBuf buf = Unpooled.buffer();
		buf.writeInt(MsgType.TankStartMoving.ordinal());
		byte[] bytes = msg.toBytes();
		buf.writeInt(bytes.length);
		buf.writeBytes(bytes);
		
//		buf.writeBytes(msg.toBytes());
		
		ch.writeInbound(buf.duplicate());
		TankStartMovingMsg msgR = (TankStartMovingMsg)ch.readInbound();
		
		assertEquals(id, msgR.getId());
		assertEquals(5, msgR.getX());
		assertEquals(10, msgR.getY());
		assertEquals(Dir.DOWN, msgR.getDir());
		
	}
}
