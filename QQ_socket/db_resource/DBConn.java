package ht.db_resource;
 
import java.sql.Connection;
import java.sql.DriverManager;
 
 
//���������������������� �����������ݿ� �������ܶ��ڿ�ͬ��javaBean��д
public class DBConn {
 
//ʹ�ô�ͳ����jdbcdriver�������ݿ�
//	static String url="jdbc:sqlserver://localhost:1433;databasename=QQ";//�������ݿ���ַ���  localhost����дip(һ�������)���߻�����, 1433�����ݿ�Ĭ�϶˿�
//	static String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";//��������;
 
//ʹ��JDBC-ODBC������ʽ�������ݿ�
	static String url="jdbc:odbc:LocalServer";//��������Դ  ����Դ������LocalServer
	static String driver="sun.jdbc.odbc.JdbcOdbcDriver";//��������;
	
	static Connection conn=null;
	
	
	
	//��̬���� ����������� ��������ֻ��Ҫ����һ��
	static
	{
 
		try {
			
			Class.forName(driver);
			
		}catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		
 
	}
	
	
	
	//���캯�� �����������ݿ�
	public Connection DBConn()
	{
		
		try
		{
			//2.�������ݿ�
			conn=DriverManager.getConnection(url);
			
			System.out.println("���ݿ����ӳɹ�������");
			
		}catch(Exception e)
		{
			e.printStackTrace();						
		}
		
		
		return conn;
		
		
	}
	
}	
	