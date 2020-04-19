package com.zzj.tank.net;


import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ServerFrame extends Frame{
	public static final ServerFrame INSTANCE = new ServerFrame();
	
	Button btnStart = new Button("start");
	TextArea taLeft = new TextArea();
	TextArea taRight = new TextArea();
	
	Server server = new Server();
	
	public ServerFrame(){
		this.setSize(1600, 600);
		this.setLocation(300, 30);
		this.add(btnStart, BorderLayout.NORTH);
		//一行两列
		Panel p = new Panel(new GridLayout(1, 2));
		p.add(taLeft);
		p.add(taRight);
		this.add(p);
		this.addWindowListener(new WindowAdapter(){

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}			
		});
//		this.btnStart.addActionListener((e)->{
//			try {
//				server.serverStart();
//			} catch (Exception e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		});
		
	}
	public static void main(String[] args) throws Exception {
		ServerFrame.INSTANCE.setVisible(true);
		ServerFrame.INSTANCE.server.serverStart();

	}
	public void updateServerMsg(String string) {
		this.taLeft.setText(taLeft.getText() + 
				System.getProperty("line.separator") + string);
		
	}
	public void updateClientMsg(String s) {
		this.taRight.setText(taRight.getText() + 
				System.getProperty("line.separator") + s);
		
	}
	

}
