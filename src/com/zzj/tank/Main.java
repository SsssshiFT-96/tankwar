package com.zzj.tank;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.zzj.tank.net.Client;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		Frame f = new Frame();
//		f.setSize(800,600);//���ô��ڴ�С
//		f.setResizable(false);//�޷��ı䴰�ڴ�С
//		f.setTitle("tank war");//���ô�������
//		f.setVisible(true);//��ʾ�����Ĵ�����
//		
//		f.addWindowListener(new WindowAdapter(){//���window������
//			@Override//����windowClosing���������������˳���
//			public void windowClosing(WindowEvent e) {
//				// TODO Auto-generated method stub
//				System.exit(0);
//			}
//		});
		TankFrame tf = TankFrame.INSTANCE;
		tf.setVisible(true);
		
		//��������ļ��г�ʼ̹�˵ĸ���
//		int initTankCount = Integer.parseInt
//				((String)PropertyMgr.get("initTankCount"));
//		//��ʼ���з�̹��
//		for(int i = 0; i < initTankCount; i++){
//			tf.tanks.add(new Tank(50 + i*100, 200, Dir.DOWN, Group.BAD, tf));
//		}
		//�õ������߳����ػ��������õ����̣߳�����while�Ĵ���������Ĵ����ִ�в��ˡ�
		new Thread(()->{
			while(true){
			try {//ÿ��50ms���µ���һ�δ��ڡ�
				Thread.sleep(25);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tf.repaint();//����repaint����
		}
		}).start();
		
		//������������
//		Client c = new Client();
		Client.INSTANCE.connect();
		
	}

}
