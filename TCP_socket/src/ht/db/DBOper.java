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
 
//�������ݿ�����ĺ���
public class DBOper {
	
	//�������к��ѣ����ˣ�ͬѧ��������������
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
				Account acc=new Account();//һ����¼����һ��Account����
				
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
	
	//���Һ���
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
				//ȡ�����ѵ���Ϣ������״̬���Լ�����
			}catch(Exception e)
			{
				e.printStackTrace();
				
			}
			return allInfo;
			
		}
	
	//���Һ���
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
				//ȡ�����ѵ���Ϣ������״̬���Լ�����
			}catch(Exception e)
			{
				e.printStackTrace();
				
			}
			return allInfo;
			
		}
		//��Ӻ���
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
				//��Ӻ���
				HSSFRow currow = sheet.createRow((short)num);
		        //��row�ｨ����cell����Ԫ�񣩣�����Ϊ�кţ���һ�У�                  //����cell���������͵�ֵ
				currow.createCell(0).setCellValue(String.valueOf(acc.getQqCode()));     //����cell�������͵�ֵ
				currow.createCell(1).setCellValue(String.valueOf(acc.getNickName()));   //����cell�ַ����͵�ֵ
				currow.createCell(3).setCellValue(String.valueOf(acc.getIpAddr()));     //����cell�������͵�ֵ
				currow.createCell(4).setCellValue(String.valueOf(acc.getPort()));   //����cell�ַ����͵�ֵ
				currow.createCell(5).setCellValue(String.valueOf(acc.getAge()));    //����cell�������͵�ֵ
				currow.createCell(6).setCellValue(String.valueOf(acc.getSex()));     //����cell�������͵�ֵ
				currow.createCell(7).setCellValue(String.valueOf(acc.getNation()));   //����cell�ַ����͵�ֵ
				currow.createCell(8).setCellValue(String.valueOf(acc.getStar()));    //����cell�������͵�ֵ
				currow.createCell(9).setCellValue(String.valueOf(acc.getFace()));     //����cell�������͵�ֵ
				currow.createCell(10).setCellValue(String.valueOf(acc.getRemark()));   //����cell�ַ����͵�ֵ
				currow.createCell(11).setCellValue(String.valueOf(acc.getSelfsign()));    //����cell�������͵�ֵ
				currow.createCell(12).setCellValue(String.valueOf(acc.getStatus()));    //����cell�������͵�ֵ
		        FileOutputStream fileOut = new FileOutputStream("data.xls");  
	 
		        conn.write(fileOut);  
		        fileOut.close();
		        
				//������ӣ�
				//״̬
				bok=true;
			}catch(Exception e)
			{
				e.printStackTrace();
				
			}
			return bok;
		}	
}
 