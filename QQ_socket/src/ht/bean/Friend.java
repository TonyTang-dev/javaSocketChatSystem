package ht.bean;

import java.io.Serializable;
//��ӦFriend��һ���ֶζ�Ӧһ����Ա����
public class Friend implements Serializable{
	private int id;
	private int selfAccount;
	private int firendAccount;
	private String groupname;
	private int invalid;
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
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public int getInvalid() {
		return invalid;
	}
	public void setInvalid(int invalid) {
		this.invalid = invalid;
	}
	
	
}
