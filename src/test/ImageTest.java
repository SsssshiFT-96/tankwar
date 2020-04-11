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
			//从硬盘上读取图片文件，存在内存的BufferedImaged中
//			BufferedImage image = ImageIO.read(new File(""));
//			assertNotNull(image);
			//通过加载ImageTest类的加载器将classpath下的图片转化成stream交给read转化成图片。
			BufferedImage image2 = ImageIO.read(ImageTest.
					class.getClassLoader().getResourceAsStream("images/tankL.gif"));
			assertNotNull(image2);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
