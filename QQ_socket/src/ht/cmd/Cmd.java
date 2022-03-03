package ht.cmd;
//系统中要用到的命令字，常量
public class Cmd {
	
	public static final int CMD_ONLINE=1000;//上线通知
	public static final int CMD_OFFLINE=1001;//下线通知
	public static final int CMD_BUSY=1002;//忙碌
	public static final int CMD_LEAVE=1003;//离开	
	
	public static final int CMD_CHAT=1004;//聊天
	public static final int CMD_DOUDONG=1005;//抖动
	public static final int CMD_ADDFRIEND=1006;//添加好友
	public static final int CMD_DELFRIEND=1007;//删除好友
	public static final int CMD_SENDFILE=1008;//发送文件	
	public static final int CMD_AFREEFRIEND=1009;//发送文件
	public static final int CMD_REJECTFRIEND=1010;//发送文件
	public static final int CMD_FILESUCC=1011;//文件发送成功
	public static final int CMD_FILEFAILED=1012;//拒绝接收文件
	public static final int CMD_FILESEND=1013;//文件发送
	public static final int CMD_SERVER=1014;//给服务器备份
	public static final int CMD_SERVER_GETALL=1015;//给服务器备份
	
 
	public static final String F_FRIEND="好友";
	public static final String F_FAMILY="家人";
	public static final String F_CLASSMATE="同学";
	public static final String F_HMD="黑名单";
}