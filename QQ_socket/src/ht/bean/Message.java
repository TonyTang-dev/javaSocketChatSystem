package ht.bean;

import java.io.Serializable;
//对应Message表，一个字段对应一个成员变量
public class Message implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int selfAccount;
	private int firendAccount;
	private int cmd;
	private String msgcontent;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSelfAccount() {
		return selfAccount;
	}
	public void setSelfAccount(int selfAccount) {
		this.selfAccount = selfAccount;
	}
	public int getFirendAccount() {
		return firendAccount;
	}
	public void setFirendAccount(int firendAccount) {
		this.firendAccount = firendAccount;
	}
	public int getCmd() {
		return cmd;
	}
	public void setCmd(int cmd) {
		this.cmd = cmd;
	}
	public String getMsgcontent() {
		return msgcontent;
	}
	public void setMsgcontent(String msgcontent) {
		this.msgcontent = msgcontent;
	}
	
}
