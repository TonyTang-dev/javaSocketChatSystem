package ht.bean;
 
import java.io.Serializable;
 
//对应Account表，一个字段对应一个成员变量
public class Account implements Serializable{
 
	private int qqCode;
	private String nickName;
	private String pwd;
	private String ipAddr;
	private int port;
	private int age;
	private String sex;
	private String nation;
	private String star;
	private String face;
	private String remark;
	private String selfsign;
	private int status;//0离线 1在线 2隐身 3 忙碌
	private String groupname;
	
	
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getQqCode() {
		return qqCode;
	}
	public void setQqCode(int qqCode) {
		this.qqCode = qqCode;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getStar() {
		return star;
	}
	public void setStar(String star) {
		this.star = star;
	}
	public String getFace() {
		return face;
	}
	public void setFace(String face) {
		this.face = face;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSelfsign() {
		return selfsign;
	}
	public void setSelfsign(String selfsign) {
		this.selfsign = selfsign;
	}
}