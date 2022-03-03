package tcpsocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class multiserver {
	public static void main(String args[]) {
		try {
			System.out.println("================µÈ´ýÁ¬½Ó===================");
			@SuppressWarnings("resource")
			ServerSocket serversocket=new ServerSocket(11111);
			Socket s=null;
			while(true) {
				s=serversocket.accept();
				new ServerThread(s).start();
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
