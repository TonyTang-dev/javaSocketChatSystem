package ht.db_resource;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
 
import ht.bean.Account;
import ht.cmd.Cmd;
 
//�������ݿ�����ĺ���
public class DBOper {
 
	//����ֵ���ŵ�account������ȥ�� ����һ��Account�������,ע���û��ĺ���
	public boolean addUser(Account acc)
	{
		boolean bok=false;
		Connection conn=new DBConn().DBConn();
		
		try {
			
			String sql="insert into account values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt=conn.prepareStatement(sql);
			int i=1;
			pstmt.setInt(i++,acc.getQqCode());
			pstmt.setString(i++,acc.getNickName());
			pstmt.setString(i++,acc.getPwd());
			pstmt.setString(i++,acc.getIpAddr());
			pstmt.setInt(i++,acc.getPort());
			pstmt.setInt(i++,acc.getAge());
			pstmt.setString(i++,acc.getSex());
			pstmt.setString(i++,acc.getNation());
			pstmt.setString(i++,acc.getStar());
			pstmt.setString(i++,acc.getFace());
			pstmt.setString(i++,acc.getRemark());
			pstmt.setString(i++,acc.getSelfsign());
			pstmt.setInt(i++,acc.getStatus());
			//ִ�����ݿ����
	//		bok=pstmt.execute();
			if(pstmt.executeUpdate()>0)
			{
				bok=true;
			}
			
			pstmt.close();
			conn.close();
		}catch(Exception e)
		{
			e.printStackTrace();
			
		}
		
		return bok;
		
	}
	//�ж϶˿��Ƿ��Ѿ���ռ��
	public boolean isPort(int port)
	{
		
		boolean bok=false;
		
		Connection conn=new DBConn().DBConn();
		
		try {
			
			String sql="select port from account where port =?";
			PreparedStatement pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1,port);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next())
			{
				bok=true;
			}
			
			
			pstmt.close();
			conn.close();
		}catch(Exception e)
		{
			e.printStackTrace();
			
		}
		
		
		
		
		return bok;
		
		
	}
	
	//��¼����,����account����
	public Account login(Account acc)
	{
				
		Connection conn=new DBConn().DBConn();
		
		try {
			
			String sql="select * from account where qqCode =? and pwd=?";
			PreparedStatement pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1,acc.getQqCode());
			pstmt.setString(2,acc.getPwd());
			
			//ResultSet��ʾ�����ݿ��в�ѯ���ķ��ؽ��
			ResultSet rs=pstmt.executeQuery();
			if(rs.next())//�鵽���˺���������ƥ��Ķ���  �Ѿ���½��
			{	//��½�ɹ�����ȡ�û�������Ϣ
				//�޸�״̬Ϊ����(1)
				acc.setNickName(rs.getString("nickname").trim());
				acc.setIpAddr(rs.getString("ipaddr").trim());
				acc.setPort(rs.getInt("port"));
				acc.setAge(rs.getInt("age"));
				acc.setSex(rs.getString("sex").trim());
				acc.setNation(rs.getString("nation").trim());
				acc.setStar(rs.getString("star").trim());
				acc.setFace(rs.getString("face").trim());
				acc.setRemark(rs.getString("remark").trim());
				acc.setSelfsign(rs.getString("selfsign").trim());
				
				acc.setStatus(1);//�޸Ķ���ֵΪ1
				//�޸����ݿ��д洢״̬Ϊ����
				modifyStatus(acc.getQqCode(),1);
				
			}
			
			
			pstmt.close();
			conn.close();
		}catch(Exception e)
		{
			e.printStackTrace();
			
		}
		
		
		
		return acc;
		
		
	}
	
	
	//���Ҹ�������
		public Account findByQQcode (int qqcode)
		{
					
			Connection conn=new DBConn().DBConn();
			Account acc=new Account();
			
			
			try {
				
		
				String sql="select * from account where qqCode =?";
				PreparedStatement pstmt=conn.prepareStatement(sql);
				
				pstmt.setInt(1,qqcode);
			
				
				//ResultSet��ʾ�����ݿ��в�ѯ���ķ��ؽ��
				ResultSet rs=pstmt.executeQuery();
				if(rs.next())//�鵽���˺���������ƥ��Ķ���  �Ѿ���½��
				{	//��½�ɹ�����ȡ�û�������Ϣ
		
					acc.setQqCode(rs.getInt("qqcode"));
					
					acc.setNickName(rs.getString("nickname").trim());
					acc.setIpAddr(rs.getString("ipaddr").trim());
					acc.setPort(rs.getInt("port"));
					acc.setAge(rs.getInt("age"));
					acc.setSex(rs.getString("sex").trim());
					acc.setNation(rs.getString("nation").trim());
					acc.setStar(rs.getString("star").trim());
					acc.setFace(rs.getString("face").trim());
					acc.setRemark(rs.getString("remark").trim());
					acc.setSelfsign(rs.getString("selfsign").trim());
					
				}
				
				
				pstmt.close();
				conn.close();
			}catch(Exception e)
			{
				e.printStackTrace();
				
			}
			
			
			
			return acc;
			
			
		}
	
	public boolean modifyStatus(int qqcode,int status)
	{
		boolean bok=false;
		Connection conn=new DBConn().DBConn();
		
		try {
			
			String sql="update account set status=? where qqcode=?";
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,status);
			pstmt.setInt(2,qqcode);
			
		
			if(pstmt.executeUpdate()>0)
			{
				bok=true;
			}
			
			pstmt.close();
			conn.close();
		}catch(Exception e)
		{
			e.printStackTrace();
			
		}
		
		return bok;
		
	}
	
	
	//����ͷ��
	public String getFace(int qqcode)
	{
		String face="";
		Connection conn=new DBConn().DBConn();
		
		try {
			
			String sql="select face from account where qqCode =?";
			PreparedStatement pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1,qqcode);
			
			//ResultSet��ʾ�����ݿ��в�ѯ���ķ��ؽ��
			ResultSet rs=pstmt.executeQuery();
			if(rs.next())//�鵽���˺���������ƥ��Ķ���  �Ѿ���½��
			{	
				face=rs.getString("face").trim();
				
				
			}
			
			
			pstmt.close();
			conn.close();
		}catch(Exception e)
		{
			e.printStackTrace();
			
		}
		
		
		
		return face;
		
		
	}
	
	//�������к��ѣ����ˣ�ͬѧ��������������
	public Vector<Account> getAllInfo(Account acc)
	{
				
		Connection conn=new DBConn().DBConn();
		Vector<Account> allInfo=new Vector<Account>();
		try {
			
			//ȡ�����ѵ���Ϣ������״̬���Լ�����
			String sql="select a.*,f.groupname from account a right outer join friend f on a.qqcode=f.friendaccount where f.selfAccount =?";
			PreparedStatement pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1,acc.getQqCode());
			
			
			//ResultSet��ʾ�����ݿ��в�ѯ���ķ��ؽ��
			ResultSet rs=pstmt.executeQuery();
			while(rs.next())//�鵽���˺���������ƥ��Ķ���  �Ѿ���½��
			{	
				Account a=new Account();//һ����¼����һ��Account����
				a.setQqCode(rs.getInt("qqcode"));
				a.setNickName(rs.getString("nickname").trim());
				a.setPwd(rs.getString("pwd").trim());		
				a.setIpAddr(rs.getString("ipaddr").trim());
				a.setPort(rs.getInt("port"));
				a.setAge(rs.getInt("age"));
				a.setSex(rs.getString("sex").trim());
				a.setNation(rs.getString("nation").trim());
				a.setStar(rs.getString("star").trim());
				a.setFace(rs.getString("face").trim());
				a.setRemark(rs.getString("remark").trim());
				a.setSelfsign(rs.getString("selfsign").trim());
				a.setStatus(rs.getInt("status"));
				a.setGroupname(rs.getString("groupname").trim());
				
				allInfo.add(a);
				
			}
			
			
			pstmt.close();
			conn.close();
		}catch(Exception e)
		{
			e.printStackTrace();
			
		}
		
		
		
		return allInfo;
		
		
	}
	
	//���Һ���
		public Vector<Account> find(Account acc)
		{
					
			Connection conn=new DBConn().DBConn();
			Vector allInfo=new Vector();
			try {
				
				//ȡ�����ѵ���Ϣ������״̬���Լ�����
			//	String sql="select * from account where qqcode=? or nickname like ? or age=? or sex=? ";
				
				String sql="select * from account where qqcode=? or nickname =? or age=?";
				
				PreparedStatement pstmt=conn.prepareStatement(sql);
				
				int i=1;
				
				pstmt.setInt(i++,acc.getQqCode());
				pstmt.setString(i++,acc.getNickName());
				pstmt.setInt(i++,acc.getAge());
		//		pstmt.setString(i++,acc.getSex());
				
				//ResultSet��ʾ�����ݿ��в�ѯ���ķ��ؽ��
				ResultSet rs=pstmt.executeQuery();
				while(rs.next())//�鵽���˺���������ƥ��Ķ���  �Ѿ���½��
				{	
					Vector a=new Vector();
					a.addElement(rs.getInt("qqcode"));
					a.addElement(rs.getString("nickname").trim());
			//		a.addElement(rs.getString("pwd").trim());		
					a.addElement(rs.getString("ipaddr").trim());
					a.addElement(rs.getInt("port"));
					a.addElement(rs.getInt("age"));
					a.addElement(rs.getString("sex").trim());
					a.addElement(rs.getString("nation").trim());
					a.addElement(rs.getString("star").trim());
					a.addElement(rs.getString("face").trim());
					a.addElement(rs.getString("remark").trim());
					a.addElement(rs.getString("selfsign").trim());
					a.addElement(rs.getInt("status"));
			//		a.addElement(rs.getString("groupname").trim());
					
					allInfo.add(a);
					
				}
				
				
				pstmt.close();
				conn.close();
			}catch(Exception e)
			{
				e.printStackTrace();
				
			}
			
			
			
			return allInfo;
			
			
		}
		//��Ӻ���
		public boolean addFriend(Account acc,int friendcode)
		{
			
			
			
			boolean bok=false;
			Connection conn=new DBConn().DBConn();
			
			try {
				
				String sql="insert into friend values(?,?,?,?)";
				PreparedStatement pstmt=conn.prepareStatement(sql);
				int i=1;
				pstmt.setInt(i++,acc.getQqCode());
				pstmt.setInt(i++,friendcode);
				pstmt.setString(i++,Cmd.F_FRIEND);
				pstmt.setInt(i++,0);
				pstmt.executeUpdate();
				pstmt.close();
			//����Ӻ���	
				pstmt=conn.prepareStatement(sql);
				i=1;
				pstmt.setInt(i++,friendcode);
				pstmt.setInt(i++,acc.getQqCode());
				pstmt.setString(i++,Cmd.F_FRIEND);
				pstmt.setInt(i++,0);
				pstmt.executeUpdate();
				pstmt.close();
				
				
				conn.close();
			}catch(Exception e)
			{
				e.printStackTrace();
				
			}
			
			return true;
			
		}	
		
		//ɾ������
		public boolean delFriend(Account acc,int friendcode)
		{
			
			Connection conn=new DBConn().DBConn();
			
			try {
				
				String sql="delete friend where selfAccount=? and friendAccount=?";
				PreparedStatement pstmt=conn.prepareStatement(sql);
				int i=1;
				pstmt.setInt(i++,acc.getQqCode());
				pstmt.setInt(i++,friendcode);
				pstmt.executeUpdate();
				pstmt.close();
			//����ɾ������	
				pstmt=conn.prepareStatement(sql);
				i=1;
				pstmt.setInt(i++,friendcode);
				pstmt.setInt(i++,acc.getQqCode());
				pstmt.executeUpdate();
				pstmt.close();
				
				
				conn.close();
			}catch(Exception e)
			{
				e.printStackTrace();
				
			}
			
			return true;
			
		}	
		
		//�ƶ�����
				public boolean moveFriend(Account acc,int friendcode,String groupname)
				{
					
					Connection conn=new DBConn().DBConn();
					
					try {
						
						String sql="update friend set groupname=? where selfAccount=? and friendAccount=?";
						PreparedStatement pstmt=conn.prepareStatement(sql);
						int i=1;
						pstmt.setString(i++,groupname);
						pstmt.setInt(i++,acc.getQqCode());
						pstmt.setInt(i++,friendcode);
						pstmt.executeUpdate();
						
						
						pstmt.close();
						conn.close();
					}catch(Exception e)
					{
						e.printStackTrace();
						
					}
					
					return true;
					
				}			
		
		
}
 