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
	//��ClientFrame����
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
				// ��send�������ַ������͵�������
				c.send(tf.getText());
//				ta.setText(ta.getText() + tf.getText());
				tf.setText("");
			}
			
		});
		this.addWindowListener(new WindowAdapter(){

			@Override
			public void windowClosing(WindowEvent e) {
				//��������Ҫ�˳�ʱ������closeConnect�����������bye
				c.closeConnect();
				System.exit(0);
			}
			
		});
//		this.setVisible(true);
	
		//���ÿͻ���
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
		//���ÿͻ���
		frame.connectToServer();
	}
	
	//����frame������
	public void updateText(String msgAccepted) {
		this.ta.setText(ta.getText() + 
				System.getProperty("line.separator") + msgAccepted);
	}
	
}
