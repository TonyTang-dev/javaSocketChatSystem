package ht_;
 
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JOptionPane;

import ht.bean.Account;
import ht.cmd.Cmd;
 
public class Send {
 
	public Vector<Account> send(SendMsg msg) throws ClassNotFoundException
	{
		if(msg.Cmd==Cmd.CMD_SERVER_GETALL) {
			try {
	            Socket socket = new Socket(msg.selfAccount.getIpAddr(),msg.selfAccount.getPort());
				OutputStream os = socket.getOutputStream();
	            ObjectOutputStream oos = new ObjectOutputStream(os);
	            oos.writeObject(msg);
	            
	            socket.shutdownOutput();
	            oos.close();
//	            
//
//	    		//接受返回
//	    		InputStream in = null;
//	            in = socket.getInputStream();// 读取客户端传过来信息的DataInputStream   
//	            ObjectInputStream ois=new ObjectInputStream(in);
//	    		SendMsg msg3=(SendMsg)ois.readObject();
//	            socket.shutdownInput();
//	            socket.close();
//	    		return msg3.re;
	        } 
			catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
		}
		
		else if(msg.Cmd==Cmd.CMD_SERVER){
			try {
				//字节数组输出流
				ByteArrayOutputStream bos=new  ByteArrayOutputStream();
				ObjectOutputStream oos=new ObjectOutputStream(bos);
				
				oos.writeObject(msg);	
						
				//最终
				byte[] b=bos.toByteArray();//把要发送的信息转换为字节数组
				//网络上发送任何东西都是发送字节数组
				
				DatagramSocket socket=new DatagramSocket();//UDP通信
				
				if(msg.friendAccount==null) {
					JOptionPane.showMessageDialog(null, "没有找到好友哦", "提示", JOptionPane.PLAIN_MESSAGE);
					socket.close();
					return null;//如果没有选择，直接退出并提示
				}
				
				InetAddress add=InetAddress.getByName(msg.friendAccount.getIpAddr());
				int port=msg.friendAccount.getPort();
				
				DatagramPacket p=new DatagramPacket(b,0,b.length,add,port);
				
				//发送 
				socket.send(p);
				socket.close();
			}catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}
 
}