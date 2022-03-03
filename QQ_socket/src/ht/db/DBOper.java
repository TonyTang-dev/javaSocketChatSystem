package ht.db;
 
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Vector;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import ht.bean.Account;
import ht.cmd.Cmd;
 
//进行数据库操作的函数
public class DBOper {
 
	//所有值都放到account对象里去了 ，传一个Account对象过来,注册用户的函数
	public boolean addUser(Account acc) throws IOException
	{
		boolean bok=false;
		
		HSSFWorkbook conn=new DBConn().getWrokbook();
		for (int sh=0;sh<2;sh++) {
			HSSFSheet sheet=conn.getSheetAt(sh);

			int index=sheet.getLastRowNum();
			HSSFRow row = sheet.getRow(index);
			if(row != null) {//检查行是不是空
				index+=1;
			}
			try {
				HSSFRow currow = sheet.createRow((short)index);
				
				HSSFCellStyle textStyle = conn.createCellStyle();
				HSSFDataFormat format = conn.createDataFormat();
	//			textStyle.setDataFormat(format.getFormat("@"));
	//			cell.setCellStyle(textStyle);//设置单元格格式为"文本"
	//			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				
				HSSFCell cell=null;
				
		        //在row里建立新cell（单元格），参数为列号（第一列）                  //设置cell的整数类型的值
				cell=currow.createCell(0);
				cell.setCellValue(String.valueOf(acc.getQqCode()));     //设置cell浮点类型的值
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell=currow.createCell(1);
				cell.setCellValue(String.valueOf(acc.getNickName()));   //设置cell字符类型的值
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell=currow.createCell(2);
				cell.setCellValue(String.valueOf(acc.getPwd()));    //设置cell布尔类型的值
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell=currow.createCell(3);
				cell.setCellValue(String.valueOf(acc.getIpAddr()));     //设置cell浮点类型的值
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell=currow.createCell(4);
				cell.setCellValue(String.valueOf(acc.getPort()));   //设置cell字符类型的值
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell=currow.createCell(5);
				cell.setCellValue(String.valueOf(acc.getAge()));    //设置cell布尔类型的值
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell=currow.createCell(6);
				cell.setCellValue(String.valueOf(acc.getSex()));     //设置cell浮点类型的值
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell=currow.createCell(7);
				cell.setCellValue(String.valueOf(acc.getNation()));   //设置cell字符类型的值
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell=currow.createCell(8);
				cell.setCellValue(String.valueOf(acc.getStar()));    //设置cell布尔类型的值
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell=currow.createCell(9);
				if(String.valueOf(acc.getFace()).equals("")) {
					cell.setCellValue("face\3.jpg");
				}
				else {
					cell.setCellValue(String.valueOf(acc.getFace()));     //设置cell浮点类型的值
				}
				
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell=currow.createCell(10);
				cell.setCellValue(String.valueOf(acc.getRemark()));   //设置cell字符类型的值
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell=currow.createCell(11);
				cell.setCellValue(String.valueOf(acc.getSelfsign()));    //设置cell布尔类型的值
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell=currow.createCell(12);
				cell.setCellValue(String.valueOf(acc.getStatus()));    //设置cell布尔类型的值
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				FileOutputStream fileOut = new FileOutputStream("data.xls");
		        
		        conn.write(fileOut);  
		        fileOut.close();
				
				//状态
				bok=true;
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		
		return bok;
		
	}
	//判断端口是否已经被占用
	public boolean isPort(int port) throws IOException
	{
		
		boolean bok=false;
		
		HSSFWorkbook conn=new DBConn().getWrokbook();
		
		try {
			for (int i=0;i<3;i++) {
				HSSFSheet sheet=conn.getSheetAt(i);
				int num=sheet.getLastRowNum();
				if(sheet.getRow(0)==null) {//第一行是空就代表没有数据了，更不能用getCell了
//					return false;
					continue;
				}
				for (int j=0;j<=num;j++) {
					HSSFRow row = sheet.getRow(j);
					if(String.valueOf(port).equals(String.valueOf(row.getCell(4).getStringCellValue()))) {
						return true;
					}
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			
		}
		return bok;
	}
	
	//登录函数,返回account对象
	public Account login(Account acc) throws IOException
	{
				
		HSSFWorkbook conn=new DBConn().getWrokbook();
		
		try {
			boolean flag1=false;
			int index=-1;
			
			HSSFSheet sheet=conn.getSheetAt(0);
			int num=sheet.getLastRowNum();
			if(sheet.getRow(0)==null) {
				return null;
			}
			for (int j=0;j<=num;j++) {
				HSSFRow row = sheet.getRow(j);
				if(String.valueOf(acc.getQqCode()).equals(String.valueOf(row.getCell(0).getStringCellValue()))
						&& String.valueOf(acc.getPwd()).equals(String.valueOf(row.getCell(2).getStringCellValue()))) {
					flag1=true;
					index=j;
					break;
				}
			}
			//ResultSet表示从数据库中查询到的返回结果
			if(flag1)//查到了账号名跟密码匹配的东西  已经登陆了
			{	//登陆成功，读取用户所有信息
				//修改状态为在线(1)
				HSSFRow row = sheet.getRow(index);
				acc.setNickName(String.valueOf(row.getCell(1).getStringCellValue()));
				acc.setIpAddr(String.valueOf(row.getCell(3).getStringCellValue()));
				acc.setPort(Integer.parseInt(String.valueOf(row.getCell(4).getStringCellValue())));
				acc.setAge(Integer.parseInt(String.valueOf(row.getCell(5).getStringCellValue())));
				acc.setSex(String.valueOf(row.getCell(6).getStringCellValue()));
				acc.setNation(String.valueOf(row.getCell(7).getStringCellValue()));
				acc.setStar(String.valueOf(row.getCell(8).getStringCellValue()));
				acc.setFace(String.valueOf(row.getCell(9).getStringCellValue()));
				acc.setRemark(String.valueOf(row.getCell(10).getStringCellValue()));
				acc.setSelfsign(String.valueOf(row.getCell(11).getStringCellValue()));
				
				acc.setStatus(1);//修改对象值为1
				//修改数据库中存储状态为上线
				modifyStatus(acc.getQqCode(),1);
				
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			
		}
		return acc;
	}
	
	
	//查找个人资料
	public Account findByQQcode (int qqcode) throws IOException{
				
		HSSFWorkbook conn=new DBConn().getWrokbook();
		HSSFSheet sheet=conn.getSheetAt(1);
		int num=sheet.getLastRowNum();
		if(sheet.getRow(0)==null) {
			return null;
		}
		Account acc=new Account();
		
		try {
			int index=-1;
			boolean flag2=false;
			
			for(int i=1;i<=num;i++) {
				HSSFRow row = sheet.getRow(i);
				if(String.valueOf(qqcode).equals(String.valueOf(row.getCell(0).getStringCellValue()))) {
					index=i;
					flag2=true;
					break;
				}
			}
			
			if(flag2)//查到了账号名跟密码匹配的东西  已经登陆了
			{	//登陆成功，读取用户所有信息
				HSSFRow row = sheet.getRow(index);
				acc.setQqCode(qqcode);
				acc.setNickName(String.valueOf(row.getCell(1).getStringCellValue()));
				acc.setIpAddr(String.valueOf(row.getCell(3).getStringCellValue()));
				acc.setPort(Integer.parseInt(String.valueOf(row.getCell(4).getStringCellValue())));
				acc.setAge(Integer.parseInt(String.valueOf(row.getCell(5).getStringCellValue())));
				acc.setSex(String.valueOf(row.getCell(6).getStringCellValue()));
				acc.setNation(String.valueOf(row.getCell(7).getStringCellValue()));
				acc.setStar(String.valueOf(row.getCell(8).getStringCellValue()));
				acc.setFace(String.valueOf(row.getCell(9).getStringCellValue()));
				acc.setRemark(String.valueOf(row.getCell(10).getStringCellValue()));
				acc.setSelfsign(String.valueOf(row.getCell(11).getStringCellValue()));
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return acc;
	}
	
	public boolean modifyStatus(int qqcode,int status) throws IOException
	{
		boolean bok=false;
		HSSFWorkbook conn=new DBConn().getWrokbook();
		HSSFSheet sheet=conn.getSheetAt(1);
		
		int num=sheet.getLastRowNum();
		if(sheet.getRow(0)==null) {
			return false;
		}
		
		try {
			int index=-1;
			boolean flag3=false;
			
			for(int i=1;i<=num;i++) {
				HSSFRow row = sheet.getRow(i);
				if(String.valueOf(qqcode).equals(String.valueOf(row.getCell(0).getStringCellValue()))){
					index=i;
					flag3=true;
					break;
				}
			}
			if(flag3) {
				HSSFRow row = sheet.getRow(index);
				String NickName=(String.valueOf(row.getCell(1).getStringCellValue()));
				String pwd=(String.valueOf(row.getCell(2).getStringCellValue()));
				String IpAddr=(String.valueOf(row.getCell(3).getStringCellValue()));
				String Port=(String.valueOf(row.getCell(4).getStringCellValue()));
				String Age=(String.valueOf(row.getCell(5).getStringCellValue()));
				String Sex=(String.valueOf(row.getCell(6).getStringCellValue()));
				String Nation=(String.valueOf(row.getCell(7).getStringCellValue()));
				String Star=(String.valueOf(row.getCell(8).getStringCellValue()));
				String Face=(String.valueOf(row.getCell(9).getStringCellValue()));
				String Remark=(String.valueOf(row.getCell(10).getStringCellValue()));
				String Selfsign=(String.valueOf(row.getCell(11).getStringCellValue()));
				
				HSSFRow currow = sheet.createRow((short)index);
		        //在row里建立新cell（单元格），参数为列号（第一列）                  //设置cell的整数类型的值
				currow.createCell(0).setCellValue(String.valueOf(qqcode));     //设置cell浮点类型的值
				currow.createCell(1).setCellValue(String.valueOf(NickName));   //设置cell字符类型的值
				currow.createCell(2).setCellValue(String.valueOf(pwd));    //设置cell布尔类型的值
				currow.createCell(3).setCellValue(String.valueOf(IpAddr));     //设置cell浮点类型的值
				currow.createCell(4).setCellValue(String.valueOf(Port));   //设置cell字符类型的值
				currow.createCell(5).setCellValue(String.valueOf(Age));    //设置cell布尔类型的值
				currow.createCell(6).setCellValue(String.valueOf(Sex));     //设置cell浮点类型的值
				currow.createCell(7).setCellValue(String.valueOf(Nation));   //设置cell字符类型的值
				currow.createCell(8).setCellValue(String.valueOf(Star));    //设置cell布尔类型的值
				currow.createCell(9).setCellValue(String.valueOf(Face));     //设置cell浮点类型的值
				currow.createCell(10).setCellValue(String.valueOf(Remark));   //设置cell字符类型的值
				currow.createCell(11).setCellValue(String.valueOf(Selfsign));    //设置cell布尔类型的值
				currow.createCell(12).setCellValue(String.valueOf(status));    //设置cell布尔类型的值
		        FileOutputStream fileOut = new FileOutputStream("data.xls");  
	
		        conn.write(fileOut);  
		        fileOut.close();
				bok=true;
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
			
		}
		
		return bok;
		
	}
	
	
	//返回头像
	public String getFace(int qqcode) throws IOException
	{
		String face="";
		HSSFWorkbook conn=new DBConn().getWrokbook();
		
		HSSFSheet sheet=conn.getSheetAt(1);
		
		int num=sheet.getLastRowNum();
		if(sheet.getRow(num)==null) {
			return "face\1.jpg";
		}
//		Account acc=new Account();
		
		try {
			int index=-1;
			boolean flag4=false;
			
			for(int i=1;i<=num;i++) {
				HSSFRow row = sheet.getRow(i);
				if(String.valueOf(qqcode).equals(String.valueOf(row.getCell(0).getStringCellValue()))) {
					index=i;
					flag4=true;
					break;
				}
			}
			
			if(flag4) {
				HSSFRow row = sheet.getRow(index);
				face=String.valueOf(row.getCell(9).getStringCellValue());
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
			
		}
		return face;
	}
	
	//返回所有好友，家人，同学，黑名单等资料
	public Vector<Account> getAllInfo(Account acc1) throws IOException
	{
				
		HSSFWorkbook conn=new DBConn().getWrokbook();
		HSSFSheet sheet=conn.getSheetAt(1);
		int num=sheet.getLastRowNum();
		if(sheet.getRow(0)==null) {
			return null;
		}
		Vector<Account> allInfo=new Vector<Account>();
		try {
			for (int index=0;index<=num;index++) {
				Account acc=new Account();//一条记录创建一个Account对象
				
				HSSFRow row = sheet.getRow(index);
				acc.setQqCode(Integer.parseInt(String.valueOf(row.getCell(0).getStringCellValue())));
				acc.setNickName(String.valueOf(row.getCell(1).getStringCellValue()));
				acc.setIpAddr(String.valueOf(row.getCell(3).getStringCellValue()));
				acc.setPort(Integer.parseInt(String.valueOf(row.getCell(4).getStringCellValue())));
				acc.setAge(Integer.parseInt(String.valueOf(row.getCell(5).getStringCellValue())));
				acc.setSex(String.valueOf(row.getCell(6).getStringCellValue()));
				acc.setNation(String.valueOf(row.getCell(7).getStringCellValue()));
				acc.setStar(String.valueOf(row.getCell(8).getStringCellValue()));
				acc.setFace(String.valueOf(row.getCell(9).getStringCellValue()));
				acc.setRemark(String.valueOf(row.getCell(10).getStringCellValue()));
				acc.setSelfsign(String.valueOf(row.getCell(11).getStringCellValue()));
				acc.setStatus(Integer.parseInt(String.valueOf(row.getCell(12).getStringCellValue())));
				
				acc.setGroupname("myQQFriends");
				
				allInfo.add(acc);
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
			
		}
		
		return allInfo;
	}
	
	
	//查找好友
		public Vector<Vector<String>> find(Account acc1) throws IOException
		{
					
			HSSFWorkbook conn=new DBConn().getWrokbook();
//			Vector<Account> allInfo=new Vector<Account>();
			Vector<Vector<String>> allInfo=new Vector<Vector<String>>();
			HSSFSheet sheet=conn.getSheetAt(0);
			int num=sheet.getLastRowNum();
			if(sheet.getRow(0)==null) {
				return allInfo;
			}
			try {
				int index=-1;
				boolean flag5=false;
				
				for(int i=0;i<=num;i++) {
					HSSFRow row = sheet.getRow(i);
					if(String.valueOf(acc1.getQqCode()).equals(String.valueOf(row.getCell(0).getStringCellValue()))){
						index=i;
						flag5=true;
						break;
					}
				}
				
				if(flag5) {
					Vector<String> a=new Vector<String>();
//					Account acc=new Account();
					//a.addElement(Integer.parseInt(String.valueOf(sheet.getCell(index,0))));
					HSSFRow row = sheet.getRow(index);
					a.addElement(String.valueOf(row.getCell(0).getStringCellValue()));
					a.addElement(String.valueOf(row.getCell(1).getStringCellValue()));
					a.addElement(String.valueOf(row.getCell(3).getStringCellValue()));
					a.addElement(String.valueOf(row.getCell(4).getStringCellValue()));
					a.addElement(String.valueOf(row.getCell(5).getStringCellValue()));
					a.addElement(String.valueOf(row.getCell(6).getStringCellValue()));
					a.addElement(String.valueOf(row.getCell(7).getStringCellValue()));
					a.addElement(String.valueOf(row.getCell(8).getStringCellValue()));
					a.addElement(String.valueOf(row.getCell(9).getStringCellValue()));
					a.addElement(String.valueOf(row.getCell(10).getStringCellValue()));
					a.addElement(String.valueOf(row.getCell(11).getStringCellValue()));
					a.addElement(String.valueOf(row.getCell(12).getStringCellValue()));
					a.addElement("myQQFriends");
					allInfo.add(a);
				}
				//取出朋友的信息，在线状态，以及分组
			}catch(Exception e)
			{
				e.printStackTrace();
				
			}
			return allInfo;
			
		}
		//添加好友
		public boolean addFriend(Account acc,int friendcode) throws IOException
		{
//			System.out.println(acc.getQqCode()+" "+friendcode);
			boolean bok=false;
			HSSFWorkbook conn=new DBConn().getWrokbook();
			HSSFSheet sheet=conn.getSheetAt(1);
			
			int num=sheet.getLastRowNum();
			if(sheet.getRow(num)!=null) {
				num+=1;
			}
			try {
				//添加好友
				HSSFRow currow = sheet.createRow((short)num);
		        //在row里建立新cell（单元格），参数为列号（第一列）                  //设置cell的整数类型的值
				currow.createCell(0).setCellValue(String.valueOf(acc.getQqCode()));     //设置cell浮点类型的值
				currow.createCell(1).setCellValue(String.valueOf(acc.getNickName()));   //设置cell字符类型的值
				currow.createCell(3).setCellValue(String.valueOf(acc.getIpAddr()));     //设置cell浮点类型的值
				currow.createCell(4).setCellValue(String.valueOf(acc.getPort()));   //设置cell字符类型的值
				currow.createCell(5).setCellValue(String.valueOf(acc.getAge()));    //设置cell布尔类型的值
				currow.createCell(6).setCellValue(String.valueOf(acc.getSex()));     //设置cell浮点类型的值
				currow.createCell(7).setCellValue(String.valueOf(acc.getNation()));   //设置cell字符类型的值
				currow.createCell(8).setCellValue(String.valueOf(acc.getStar()));    //设置cell布尔类型的值
				currow.createCell(9).setCellValue(String.valueOf(acc.getFace()));     //设置cell浮点类型的值
				currow.createCell(10).setCellValue(String.valueOf(acc.getRemark()));   //设置cell字符类型的值
				currow.createCell(11).setCellValue(String.valueOf(acc.getSelfsign()));    //设置cell布尔类型的值
				currow.createCell(12).setCellValue(String.valueOf(acc.getStatus()));    //设置cell布尔类型的值
		        FileOutputStream fileOut = new FileOutputStream("data.xls");  
	 
		        conn.write(fileOut);  
		        fileOut.close();
		        
				//互相添加？
				//状态
				bok=true;
			}catch(Exception e)
			{
				e.printStackTrace();
				
			}
			
			return bok;
			
		}	
		
		//删除好友
		public boolean delFriend(Account acc,int friendcode) throws IOException
		{
			
			HSSFWorkbook conn=new DBConn().getWrokbook();
			HSSFSheet sheet=conn.getSheetAt(1);
			FileOutputStream fileOut=null;
			
			int num=sheet.getLastRowNum();
			if(sheet.getRow(num)==null) {
				return false;
			}
			try {
				int index=-1;
				boolean flag6=false;
				
				for(int i=0;i<num;i++) {
					HSSFRow row = sheet.getRow(i);
					if(String.valueOf(friendcode).equals(String.valueOf(row.getCell(0).getStringCellValue()))) {
						index=i;
						flag6=true;
						break;
					}
				}
				if(flag6) {
					//逐步上移
					for(int i=index;i<num;i++) {
						HSSFRow row = sheet.getRow(i+1);
						String qqcode=(String.valueOf(row.getCell(0).getStringCellValue()));
						String NickName=(String.valueOf(row.getCell(1).getStringCellValue()));
						String pwd=(String.valueOf(row.getCell(2).getStringCellValue()));
						String IpAddr=(String.valueOf(row.getCell(3).getStringCellValue()));
						String Port=(String.valueOf(row.getCell(4).getStringCellValue()));
						String Age=(String.valueOf(row.getCell(5).getStringCellValue()));
						String Sex=(String.valueOf(row.getCell(6).getStringCellValue()));
						String Nation=(String.valueOf(row.getCell(7).getStringCellValue()));
						String Star=(String.valueOf(row.getCell(8).getStringCellValue()));
						String Face=(String.valueOf(row.getCell(9).getStringCellValue()));
						String Remark=(String.valueOf(row.getCell(10).getStringCellValue()));
						String Selfsign=(String.valueOf(row.getCell(11).getStringCellValue()));
						String Status=(String.valueOf(row.getCell(12).getStringCellValue()));
						
						HSSFRow currow = sheet.createRow((short)i);
				        //在row里建立新cell（单元格），参数为列号（第一列）                  //设置cell的整数类型的值
						currow.createCell(0).setCellValue(String.valueOf(qqcode));     //设置cell浮点类型的值
						currow.createCell(1).setCellValue(String.valueOf(NickName));   //设置cell字符类型的值
						currow.createCell(2).setCellValue(String.valueOf(pwd));    //设置cell布尔类型的值
						currow.createCell(3).setCellValue(String.valueOf(IpAddr));     //设置cell浮点类型的值
						currow.createCell(4).setCellValue(String.valueOf(Port));   //设置cell字符类型的值
						currow.createCell(5).setCellValue(String.valueOf(Age));    //设置cell布尔类型的值
						currow.createCell(6).setCellValue(String.valueOf(Sex));     //设置cell浮点类型的值
						currow.createCell(7).setCellValue(String.valueOf(Nation));   //设置cell字符类型的值
						currow.createCell(8).setCellValue(String.valueOf(Star));    //设置cell布尔类型的值
						currow.createCell(9).setCellValue(String.valueOf(Face));     //设置cell浮点类型的值
						currow.createCell(10).setCellValue(String.valueOf(Remark));   //设置cell字符类型的值
						currow.createCell(11).setCellValue(String.valueOf(Selfsign));    //设置cell布尔类型的值
						currow.createCell(12).setCellValue(String.valueOf(Status));    //设置cell布尔类型的值
				        fileOut = new FileOutputStream("data.xls");  
			 
//				        conn.write(fileOut);  
//				        fileOut.close();
						
					}
//					sheet.createRow((short)num);//最后一行置空
					sheet.removeRow(sheet.getRow(num));
			        conn.write(fileOut);  
			        fileOut.close();
				}		
				JOptionPane.showMessageDialog(null, "已将好友从数据库删除", "提示", JOptionPane.PLAIN_MESSAGE);
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			return true;
			
		}	
		
		//移动好友
		public boolean moveFriend(Account acc,int friendcode,String groupname) throws IOException
		{
			
			HSSFWorkbook conn=new DBConn().getWrokbook();
			HSSFSheet sheet=conn.getSheetAt(1);
			int number=0;
			
			int num=sheet.getLastRowNum();
			if(sheet.getRow(num)==null) {
				return false;
			}
			
			try {
				int index=-1;
				boolean flag7=false;
				
				for(int i=0;i<=number;i++) {
					HSSFRow row = sheet.getRow(i);
					if(String.valueOf(acc.getQqCode()).equals(String.valueOf(row.getCell(0).getStringCellValue()))) {
						index=i;
						flag7=true;
						break;
					}
				}
				if(flag7) {
					HSSFRow row = sheet.getRow(index);
					String qqcode=(String.valueOf(row.getCell(0).getStringCellValue()));
					String NickName=(String.valueOf(row.getCell(1).getStringCellValue()));
					String pwd=(String.valueOf(row.getCell(2).getStringCellValue()));
					String IpAddr=(String.valueOf(row.getCell(3).getStringCellValue()));
					String Port=(String.valueOf(row.getCell(4).getStringCellValue()));
					String Age=(String.valueOf(row.getCell(5).getStringCellValue()));
					String Sex=(String.valueOf(row.getCell(6).getStringCellValue()));
					String Nation=(String.valueOf(row.getCell(7).getStringCellValue()));
					String Star=(String.valueOf(row.getCell(8).getStringCellValue()));
					String Face=(String.valueOf(row.getCell(9).getStringCellValue()));
					String Remark=(String.valueOf(row.getCell(10).getStringCellValue()));
					String Selfsign=(String.valueOf(row.getCell(11).getStringCellValue()));
					String Status=(String.valueOf(row.getCell(12).getStringCellValue()));
					
					HSSFRow currow = sheet.createRow((short)index);
			        //在row里建立新cell（单元格），参数为列号（第一列）                  //设置cell的整数类型的值
					currow.createCell(0).setCellValue(String.valueOf(qqcode));     //设置cell浮点类型的值
					currow.createCell(1).setCellValue(String.valueOf(NickName));   //设置cell字符类型的值
					currow.createCell(2).setCellValue(String.valueOf(pwd));    //设置cell布尔类型的值
					currow.createCell(3).setCellValue(String.valueOf(IpAddr));     //设置cell浮点类型的值
					currow.createCell(4).setCellValue(String.valueOf(Port));   //设置cell字符类型的值
					currow.createCell(5).setCellValue(String.valueOf(Age));    //设置cell布尔类型的值
					currow.createCell(6).setCellValue(String.valueOf(Sex));     //设置cell浮点类型的值
					currow.createCell(7).setCellValue(String.valueOf(Nation));   //设置cell字符类型的值
					currow.createCell(8).setCellValue(String.valueOf(Star));    //设置cell布尔类型的值
					currow.createCell(9).setCellValue(String.valueOf(Face));     //设置cell浮点类型的值
					currow.createCell(10).setCellValue(String.valueOf(Remark));   //设置cell字符类型的值
					currow.createCell(11).setCellValue(String.valueOf(Selfsign));    //设置cell布尔类型的值
					currow.createCell(12).setCellValue(String.valueOf(Status));    //设置cell布尔类型的值
					currow.createCell(113).setCellValue("myFriends");
			        FileOutputStream fileOut = new FileOutputStream("data.xls");  
		 
			        conn.write(fileOut);  
			        fileOut.close();
				}		
				
			}catch(Exception e)
			{
				e.printStackTrace();
				
			}
			
			return true;
			
		}
}
 