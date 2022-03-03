package ht_;
 
import ht.bean.Account;
 
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

import javax.swing.text.StyledDocument;
 
public class SendMsg implements Serializable{
 
	public int Cmd;//命令字
	public Account selfAccount,friendAccount;//自己的信息,对方的信息
	public StyledDocument doc;
	public String sFileName;
	public byte[] b;

	public Vector<Vector<String>> re=null;
	
}