package test;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

import com.zzj.tank.Dir;
import com.zzj.tank.Group;
import com.zzj.tank.net.TankJoinMsg;
import com.zzj.tank.net.TankJoinMsgDecoder;
import com.zzj.tank.net.TankJoinMsgEncoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;

public class TankJoinMsgCodecTest {

	@Test
	public void TestEncoder(){
		EmbeddedChannel ch = new EmbeddedChannel();
		
		UUID id = UUID.randomUUID();
		TankJoinMsg msg = new TankJoinMsg(5, 10, Dir.DOWN, 
				true, Group.BAD, id);
		ch.pipeline()
			.addLast(new TankJoinMsgEncoder());
		ch.writeOutbound(msg);
		
		ByteBuf buf = (ByteBuf)ch.readOutbound();
		
		int x = buf.readInt();
		int y = buf.readInt();
		int dirOrdinal = buf.readInt();
		Dir dir = Dir.values()[dirOrdinal];
		boolean moving = buf.readBoolean();
		int groupOrdinal = buf.readInt();
		Group g = Group.values()[groupOrdinal];
		UUID uuid = new UUID(buf.readLong(), buf.readLong());
		
		assertEquals(5, x);
		assertEquals(10, y);
		assertEquals(Dir.DOWN, dir);
		assertEquals(true, moving);
		assertEquals(Group.BAD, g);
		assertEquals(id, uuid);
	}
	@Test
	public void TestDecoder(){
		EmbeddedChannel ch = new EmbeddedChannel();
		
		UUID id = UUID.randomUUID();
		TankJoinMsg msg = new TankJoinMsg(5, 10, Dir.DOWN, 
				true, Group.BAD, id);
		ch.pipeline()
			.addLast(new TankJoinMsgDecoder());
		ByteBuf buf = Unpooled.buffer();
		buf.writeBytes(msg.toBytes());
		ch.writeInbound(buf.duplicate());
		TankJoinMsg msgR = (TankJoinMsg)ch.readInbound();
		
		assertEquals(5, msgR.x);
		assertEquals(10, msgR.y);
		assertEquals(Dir.DOWN, msgR.dir);
		assertEquals(true, msgR.moving);
		assertEquals(Group.BAD, msgR.group);
		assertEquals(id, msgR.id);
	}

}
