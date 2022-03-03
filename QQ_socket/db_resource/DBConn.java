package ht.db_resource;
 
import java.sql.Connection;
import java.sql.DriverManager;
 
 
//这类仅仅负责调入驱动程序 并且连接数据库 其他功能都在库同名javaBean里写
public class DBConn {
 
//使用传统方法jdbcdriver连接数据库
//	static String url="jdbc:sqlserver://localhost:1433;databasename=QQ";//连接数据库的字符串  localhost可以写ip(一般服务器)或者机器名, 1433是数据库默认端口
//	static String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";//驱动程序;
 
//使用JDBC-ODBC桥连方式操作数据库
	static String url="jdbc:odbc:LocalServer";//连接数据源  数据源名称是LocalServer
	static String driver="sun.jdbc.odbc.JdbcOdbcDriver";//驱动程序;
	
	static Connection conn=null;
	
	
	
	//静态语句块 负责调入驱动 整个程序只需要调用一次
	static
	{
 
		try {
			
			Class.forName(driver);
			
		}catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		
 
	}
	
	
	
	//构造函数 用来连接数据库
	public Connection DBConn()
	{
		
		try
		{
			//2.连接数据库
			conn=DriverManager.getConnection(url);
			
			System.out.println("数据库连接成功。。。");
			
		}catch(Exception e)
		{
			e.printStackTrace();						
		}
		
		
		return conn;
		
		
	}
	
}	
	