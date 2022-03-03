package tcpsocket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JOptionPane;

public class client {
	public static void main(String args[]) {
		try {
			Socket s=new Socket("localhost",11111);
			DataInputStream is=new DataInputStream(s.getInputStream());
			DataOutputStream os=new DataOutputStream(s.getOutputStream());
			
//			ObjectOutputStream oos=new ObjectOutputStream(os);
//			
//			oos.writeObject(msg);
//			
//			if(msg.friendAccount==null) {
//				JOptionPane.showMessageDialog(null, "没有找到好友哦", "提示", JOptionPane.PLAIN_MESSAGE);
//				socket.close();
//				return;//如果没有选择，直接退出并提示
//			}
//			
//			InetAddress add=InetAddress.getByName(msg.friendAccount.getIpAddr());
//			int port=msg.friendAccount.getPort();
//			
//			DatagramPacket p=new DatagramPacket(b,0,b.length,add,port);
//			
//			//发送 
//			socket.send(p);
//			socket.close();
			
			System.out.println("======================");
			String outstr,instr;
			boolean NotEnd=true;
			BufferedReader buf=new BufferedReader(new InputStreamReader(System.in));
			while(NotEnd) {
				outstr=buf.readLine();
				System.out.println("发送："+outstr);
				os.write(outstr.getBytes());
//				os.flush();
				byte[] b=new byte[1024];
				is.read(b);
				instr=new String(b);
				if(!instr.equals("bye")) {
					System.out.println("返回结果："+instr);
				}
				else {
					NotEnd=false;
				}
			}
			is.close();
			os.close();
			s.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
