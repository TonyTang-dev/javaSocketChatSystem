package tcpsocket;

import java.awt.FileDialog;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.swing.JOptionPane;

import ht.bean.Account;
import ht.cmd.Cmd;
import ht.db.DBOper;
import ht_.Send;
import ht_.SendMsg;


public class ServerThread extends Thread{
	private Socket socket=null;
	
	//接收消息的线程
	public ServerThread(Socket s)
	{
		this.socket=s;
	}
	@Override
	public void run()
	{
		DataOutputStream out = null;
		InputStream in = null;
        try {
            in = this.socket.getInputStream();// 读取客户端传过来信息的DataInputStream                   
            out = new DataOutputStream(this.socket  
                    .getOutputStream());// 向客户端发送信息的DataOutputStream
            ObjectInputStream ois=new ObjectInputStream(in);
			SendMsg msg=(SendMsg)ois.readObject();
			
			if(msg.Cmd==Cmd.CMD_SERVER) {
				new DBOper().addFriend(msg.selfAccount, msg.selfAccount.getQqCode());
			}
			
			else if(msg.Cmd==Cmd.CMD_SERVER_GETALL) {
				//发回
				Vector<Vector<String>> re=new DBOper().findALL(msg.selfAccount);//getAllInfo(msg.selfAccount);
				SendMsg msg2=new SendMsg();
				msg2.Cmd=Cmd.CMD_SERVER_GETALL;
				msg2.friendAccount=null;
				msg2.selfAccount=msg.selfAccount;
				msg2.re=re;
//				new Send().send(msg2);
				
				OutputStream os = socket.getOutputStream();
	            ObjectOutputStream oos = new ObjectOutputStream(os);
	            oos.writeObject(msg2);
	            socket.shutdownOutput();
	            oos.close();
			}
            
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
        finally {
        	try {
        		in.close();
        		out.close();
				this.socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//关闭连接          
        }			
	}
}
