package test;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

public class ImageTest {

	@Test
	public void test() {
		try {
			//��Ӳ���϶�ȡͼƬ�ļ��������ڴ��BufferedImaged��
//			BufferedImage image = ImageIO.read(new File(""));
//			assertNotNull(image);
			//ͨ������ImageTest��ļ�������classpath�µ�ͼƬת����stream����readת����ͼƬ��
			BufferedImage image2 = ImageIO.read(ImageTest.
					class.getClassLoader().getResourceAsStream("images/tankL.gif"));
			assertNotNull(image2);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
