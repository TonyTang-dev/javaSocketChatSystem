package ht_;
 
import ht.bean.Account;
 
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

import javax.swing.text.StyledDocument;
 
public class SendMsg implements Serializable{
 
	public int Cmd;//������
	public Account selfAccount,friendAccount;//�Լ�����Ϣ,�Է�����Ϣ
	public StyledDocument doc;
	public String sFileName;
	public byte[] b;

	public Vector<Vector<String>> re=null;
	
}