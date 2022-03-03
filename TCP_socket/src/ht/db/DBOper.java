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
 
//进行数据库操作的函数
public class DBOper {
	
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
		public Vector<Vector<String>> findALL(Account acc1) throws IOException
		{
			HSSFWorkbook conn=new DBConn().getWrokbook();
//					Vector<Account> allInfo=new Vector<Account>();
			Vector<Vector<String>> allInfo=new Vector<Vector<String>>();
			HSSFSheet sheet=conn.getSheetAt(1);
			int num=sheet.getLastRowNum();
			if(sheet.getRow(0)==null) {
				return allInfo;
			}
			try {
				int index=-1;
				boolean flag5=false;
				
				for(int i=0;i<=num;i++) {
//					HSSFRow row = sheet.getRow(i);
//					if(String.valueOf(acc1.getQqCode()).equals(String.valueOf(row.getCell(0).getStringCellValue()))){
//						index=i;
//						flag5=true;
//						break;
//					}
//				}
//				
//				if(flag5) {
					Vector<String> a=new Vector<String>();
//							Account acc=new Account();
					//a.addElement(Integer.parseInt(String.valueOf(sheet.getCell(index,0))));
					HSSFRow row = sheet.getRow(i);//ndex);
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
	
	//查找好友
		public Vector<Vector<String>> find(Account acc1) throws IOException
		{
					
			HSSFWorkbook conn=new DBConn().getWrokbook();
//			Vector<Account> allInfo=new Vector<Account>();
			Vector<Vector<String>> allInfo=new Vector<Vector<String>>();
			HSSFSheet sheet=conn.getSheetAt(1);
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
//				
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
}
 