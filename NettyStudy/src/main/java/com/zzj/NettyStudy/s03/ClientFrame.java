package com.zzj.NettyStudy.s03;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClientFrame extends Frame{
	//创ClientFrame单例
	public static final ClientFrame INSTANCE = new ClientFrame();
	
	TextArea ta = new TextArea();
	TextField tf = new TextField();
	
	Client c = null;
	
	public ClientFrame(){
		this.setSize(600,400);
		this.setLocation(100,20);
		this.add(ta, BorderLayout.CENTER);
		this.add(tf, BorderLayout.SOUTH);
		
		tf.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// 用send方法把字符串发送到服务器
				c.send(tf.getText());
//				ta.setText(ta.getText() + tf.getText());
				tf.setText("");
			}
			
		});
		this.addWindowListener(new WindowAdapter(){

			@Override
			public void windowClosing(WindowEvent e) {
				//当点击红叉要退出时，调用closeConnect向服务器发送bye
				c.closeConnect();
				System.exit(0);
			}
			
		});
//		this.setVisible(true);
	
		//调用客户端
//		new Client().connect();
//		connectToServer();
	}
	
	private void connectToServer() {
		// TODO Auto-generated method stub
		c = new Client();
		c.connect();
	}

	public static void main(String[] args){
		ClientFrame frame = ClientFrame.INSTANCE;
		frame.setVisible(true);
		//调用客户端
		frame.connectToServer();
	}
	
	//更新frame的内容
	public void updateText(String msgAccepted) {
		this.ta.setText(ta.getText() + 
				System.getProperty("line.separator") + msgAccepted);
	}
	
}
