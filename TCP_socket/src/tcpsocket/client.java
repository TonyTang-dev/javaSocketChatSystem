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
//				JOptionPane.showMessageDialog(null, "û���ҵ�����Ŷ", "��ʾ", JOptionPane.PLAIN_MESSAGE);
//				socket.close();
//				return;//���û��ѡ��ֱ���˳�����ʾ
//			}
//			
//			InetAddress add=InetAddress.getByName(msg.friendAccount.getIpAddr());
//			int port=msg.friendAccount.getPort();
//			
//			DatagramPacket p=new DatagramPacket(b,0,b.length,add,port);
//			
//			//���� 
//			socket.send(p);
//			socket.close();
			
			System.out.println("======================");
			String outstr,instr;
			boolean NotEnd=true;
			BufferedReader buf=new BufferedReader(new InputStreamReader(System.in));
			while(NotEnd) {
				outstr=buf.readLine();
				System.out.println("���ͣ�"+outstr);
				os.write(outstr.getBytes());
//				os.flush();
				byte[] b=new byte[1024];
				is.read(b);
				instr=new String(b);
				if(!instr.equals("bye")) {
					System.out.println("���ؽ����"+instr);
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
