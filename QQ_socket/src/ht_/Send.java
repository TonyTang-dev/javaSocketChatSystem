package ht_;
 
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import javax.swing.JOptionPane;

import ht.cmd.Cmd;
 
public class Send {
 
	public Vector send(SendMsg msg)
	{
		
		try
		{
			//��������
			if(msg.Cmd==Cmd.CMD_SERVER) {
				try {
		            Socket socket = new Socket("127.0.0.1", 11111);
					OutputStream os = socket.getOutputStream();
		            ObjectOutputStream oos = new ObjectOutputStream(os);
		            oos.writeObject(msg);
		            
		            socket.shutdownOutput();
		            socket.shutdownInput();
		            oos.close();
//		            is.close();
//		            br.close();
		            socket.close();
		        } catch (UnknownHostException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        } catch (IOException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        }
			}
			else if(msg.Cmd==Cmd.CMD_SERVER_GETALL) {
				try {
		            Socket socket = new Socket("127.0.0.1", 11111);
					OutputStream os = socket.getOutputStream();
		            ObjectOutputStream oos = new ObjectOutputStream(os);
		            oos.writeObject(msg);
		            
//		            socket.shutdownOutput();
//		            oos.close();
		            
		    		//���ܷ���
		    		InputStream in = null;
		            in = socket.getInputStream();// ��ȡ�ͻ��˴�������Ϣ��DataInputStream   
		            ObjectInputStream ois=new ObjectInputStream(in);
		    		SendMsg msg3=(SendMsg)ois.readObject();
		            socket.shutdownInput();
		            socket.close();
		            oos.close();
		    		return msg3.re;
		        } 
				catch (IOException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        }
			}
			else {
				//�ֽ����������
				ByteArrayOutputStream bos=new  ByteArrayOutputStream();
				ObjectOutputStream oos=new ObjectOutputStream(bos);
				oos.writeObject(msg);
				//����
				byte[] b=bos.toByteArray();//��Ҫ���͵���Ϣת��Ϊ�ֽ�����
				//�����Ϸ����κζ������Ƿ����ֽ�����
				
				DatagramSocket socket=new DatagramSocket();//UDPͨ��
				
				if(msg.friendAccount==null) {
					JOptionPane.showMessageDialog(null, "û���ҵ�����Ŷ", "��ʾ", JOptionPane.PLAIN_MESSAGE);
					socket.close();
					return null;//���û��ѡ��ֱ���˳�����ʾ
				}
				
				InetAddress add=InetAddress.getByName(msg.friendAccount.getIpAddr());
				int port=msg.friendAccount.getPort();
				
				System.out.println(add);
				DatagramPacket p=new DatagramPacket(b,0,b.length,add,port);
				
				//���� 
				socket.send(p);
				socket.close();
			}
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
 
}