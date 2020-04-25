package com.zzj.tank;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.zzj.tank.net.Client;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		Frame f = new Frame();
//		f.setSize(800,600);//设置窗口大小
//		f.setResizable(false);//无法改变窗口大小
//		f.setTitle("tank war");//设置窗口名称
//		f.setVisible(true);//显示创建的窗口类
//		
//		f.addWindowListener(new WindowAdapter(){//添加window监听器
//			@Override//监听windowClosing动作，监听到就退出。
//			public void windowClosing(WindowEvent e) {
//				// TODO Auto-generated method stub
//				System.exit(0);
//			}
//		});
		TankFrame tf = TankFrame.INSTANCE;
		tf.setVisible(true);
		
		//获得配置文件中初始坦克的个数
//		int initTankCount = Integer.parseInt
//				((String)PropertyMgr.get("initTankCount"));
//		//初始化敌方坦克
//		for(int i = 0; i < initTankCount; i++){
//			tf.tanks.add(new Tank(50 + i*100, 200, Dir.DOWN, Group.BAD, tf));
//		}
		//用单独的线程来重画，若不用单独线程，由于while的存在则下面的代码就执行不了。
		new Thread(()->{
			while(true){
			try {//每隔50ms重新调用一次窗口。
				Thread.sleep(25);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tf.repaint();//调用repaint方法
		}
		}).start();
		
		//进行网络连接
//		Client c = new Client();
		Client.INSTANCE.connect();
		
	}

}
