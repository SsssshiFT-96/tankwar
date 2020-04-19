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
		//new��ʱ��ֱ�Ӱ�TankMsgEncoder�ӵ�channel��
		EmbeddedChannel ch = new EmbeddedChannel(new TankMsgEncoder());
		//ͨ��EmbeddedChannel����дһ��msg
		ch.writeOutbound(msg);
		//����������д���ݶ�������
		ByteBuf buf = (ByteBuf)ch.readOutbound();
		int x = buf.readInt();
		int y = buf.readInt();
		
		Assert.assertTrue(x == 10 && y ==10);
		buf.release();
	}
	
	
	@Test
	public void testTankMsgEncoder2() {
		//����дһ��ByteBuf����������ByteBuf�����Բ��ᾭ��Encoder���ҵ�DeCoderִ��
		ByteBuf buf = Unpooled.buffer();
		TankMsg msg = new TankMsg(10, 10);
		buf.writeInt(msg.x);
		buf.writeInt(msg.y);
		
		
		//��TankMsgEncoder��TankMsgDecoder�ӵ�channel��
		//����ע���Ⱦ���decoder���پ���encoder�����ٱ��룬��Ϊ����ȥ�ķ���ͬ��
		EmbeddedChannel ch = new EmbeddedChannel(
				new TankMsgEncoder(), new TankMsgDecoder());
		//��ת���õ�ByteBufд����
		ch.writeInbound(buf.duplicate());
		//������
		TankMsg tm = (TankMsg)ch.readInbound();
		
		Assert.assertTrue(tm.x == 10 && tm.y ==10);
	}

}
