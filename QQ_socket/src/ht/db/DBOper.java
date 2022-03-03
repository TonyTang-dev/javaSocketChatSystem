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
 
//�������ݿ�����ĺ���
public class DBOper {
 
	//����ֵ���ŵ�account������ȥ�� ����һ��Account�������,ע���û��ĺ���
	public boolean addUser(Account acc) throws IOException
	{
		boolean bok=false;
		
		HSSFWorkbook conn=new DBConn().getWrokbook();
		for (int sh=0;sh<2;sh++) {
			HSSFSheet sheet=conn.getSheetAt(sh);

			int index=sheet.getLastRowNum();
			HSSFRow row = sheet.getRow(index);
			if(row != null) {//������ǲ��ǿ�
				index+=1;
			}
			try {
				HSSFRow currow = sheet.createRow((short)index);
				
				HSSFCellStyle textStyle = conn.createCellStyle();
				HSSFDataFormat format = conn.createDataFormat();
	//			textStyle.setDataFormat(format.getFormat("@"));
	//			cell.setCellStyle(textStyle);//���õ�Ԫ���ʽΪ"�ı�"
	//			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				
				HSSFCell cell=null;
				
		        //��row�ｨ����cell����Ԫ�񣩣�����Ϊ�кţ���һ�У�                  //����cell���������͵�ֵ
				cell=currow.createCell(0);
				cell.setCellValue(String.valueOf(acc.getQqCode()));     //����cell�������͵�ֵ
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell=currow.createCell(1);
				cell.setCellValue(String.valueOf(acc.getNickName()));   //����cell�ַ����͵�ֵ
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell=currow.createCell(2);
				cell.setCellValue(String.valueOf(acc.getPwd()));    //����cell�������͵�ֵ
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell=currow.createCell(3);
				cell.setCellValue(String.valueOf(acc.getIpAddr()));     //����cell�������͵�ֵ
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell=currow.createCell(4);
				cell.setCellValue(String.valueOf(acc.getPort()));   //����cell�ַ����͵�ֵ
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell=currow.createCell(5);
				cell.setCellValue(String.valueOf(acc.getAge()));    //����cell�������͵�ֵ
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell=currow.createCell(6);
				cell.setCellValue(String.valueOf(acc.getSex()));     //����cell�������͵�ֵ
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell=currow.createCell(7);
				cell.setCellValue(String.valueOf(acc.getNation()));   //����cell�ַ����͵�ֵ
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell=currow.createCell(8);
				cell.setCellValue(String.valueOf(acc.getStar()));    //����cell�������͵�ֵ
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell=currow.createCell(9);
				if(String.valueOf(acc.getFace()).equals("")) {
					cell.setCellValue("face\3.jpg");
				}
				else {
					cell.setCellValue(String.valueOf(acc.getFace()));     //����cell�������͵�ֵ
				}
				
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell=currow.createCell(10);
				cell.setCellValue(String.valueOf(acc.getRemark()));   //����cell�ַ����͵�ֵ
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell=currow.createCell(11);
				cell.setCellValue(String.valueOf(acc.getSelfsign()));    //����cell�������͵�ֵ
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell=currow.createCell(12);
				cell.setCellValue(String.valueOf(acc.getStatus()));    //����cell�������͵�ֵ
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				FileOutputStream fileOut = new FileOutputStream("data.xls");
		        
		        conn.write(fileOut);  
		        fileOut.close();
				
				//״̬
				bok=true;
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		
		return bok;
		
	}
	//�ж϶˿��Ƿ��Ѿ���ռ��
	public boolean isPort(int port) throws IOException
	{
		
		boolean bok=false;
		
		HSSFWorkbook conn=new DBConn().getWrokbook();
		
		try {
			for (int i=0;i<3;i++) {
				HSSFSheet sheet=conn.getSheetAt(i);
				int num=sheet.getLastRowNum();
				if(sheet.getRow(0)==null) {//��һ���ǿվʹ���û�������ˣ���������getCell��
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
	
	//��¼����,����account����
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
			//ResultSet��ʾ�����ݿ��в�ѯ���ķ��ؽ��
			if(flag1)//�鵽���˺���������ƥ��Ķ���  �Ѿ���½��
			{	//��½�ɹ�����ȡ�û�������Ϣ
				//�޸�״̬Ϊ����(1)
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
				
				acc.setStatus(1);//�޸Ķ���ֵΪ1
				//�޸����ݿ��д洢״̬Ϊ����
				modifyStatus(acc.getQqCode(),1);
				
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			
		}
		return acc;
	}
	
	
	//���Ҹ�������
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
			
			if(flag2)//�鵽���˺���������ƥ��Ķ���  �Ѿ���½��
			{	//��½�ɹ�����ȡ�û�������Ϣ
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
		        //��row�ｨ����cell����Ԫ�񣩣�����Ϊ�кţ���һ�У�                  //����cell���������͵�ֵ
				currow.createCell(0).setCellValue(String.valueOf(qqcode));     //����cell�������͵�ֵ
				currow.createCell(1).setCellValue(String.valueOf(NickName));   //����cell�ַ����͵�ֵ
				currow.createCell(2).setCellValue(String.valueOf(pwd));    //����cell�������͵�ֵ
				currow.createCell(3).setCellValue(String.valueOf(IpAddr));     //����cell�������͵�ֵ
				currow.createCell(4).setCellValue(String.valueOf(Port));   //����cell�ַ����͵�ֵ
				currow.createCell(5).setCellValue(String.valueOf(Age));    //����cell�������͵�ֵ
				currow.createCell(6).setCellValue(String.valueOf(Sex));     //����cell�������͵�ֵ
				currow.createCell(7).setCellValue(String.valueOf(Nation));   //����cell�ַ����͵�ֵ
				currow.createCell(8).setCellValue(String.valueOf(Star));    //����cell�������͵�ֵ
				currow.createCell(9).setCellValue(String.valueOf(Face));     //����cell�������͵�ֵ
				currow.createCell(10).setCellValue(String.valueOf(Remark));   //����cell�ַ����͵�ֵ
				currow.createCell(11).setCellValue(String.valueOf(Selfsign));    //����cell�������͵�ֵ
				currow.createCell(12).setCellValue(String.valueOf(status));    //����cell�������͵�ֵ
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
	
	
	//����ͷ��
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
		
		//ɾ������
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
					//������
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
				        //��row�ｨ����cell����Ԫ�񣩣�����Ϊ�кţ���һ�У�                  //����cell���������͵�ֵ
						currow.createCell(0).setCellValue(String.valueOf(qqcode));     //����cell�������͵�ֵ
						currow.createCell(1).setCellValue(String.valueOf(NickName));   //����cell�ַ����͵�ֵ
						currow.createCell(2).setCellValue(String.valueOf(pwd));    //����cell�������͵�ֵ
						currow.createCell(3).setCellValue(String.valueOf(IpAddr));     //����cell�������͵�ֵ
						currow.createCell(4).setCellValue(String.valueOf(Port));   //����cell�ַ����͵�ֵ
						currow.createCell(5).setCellValue(String.valueOf(Age));    //����cell�������͵�ֵ
						currow.createCell(6).setCellValue(String.valueOf(Sex));     //����cell�������͵�ֵ
						currow.createCell(7).setCellValue(String.valueOf(Nation));   //����cell�ַ����͵�ֵ
						currow.createCell(8).setCellValue(String.valueOf(Star));    //����cell�������͵�ֵ
						currow.createCell(9).setCellValue(String.valueOf(Face));     //����cell�������͵�ֵ
						currow.createCell(10).setCellValue(String.valueOf(Remark));   //����cell�ַ����͵�ֵ
						currow.createCell(11).setCellValue(String.valueOf(Selfsign));    //����cell�������͵�ֵ
						currow.createCell(12).setCellValue(String.valueOf(Status));    //����cell�������͵�ֵ
				        fileOut = new FileOutputStream("data.xls");  
			 
//				        conn.write(fileOut);  
//				        fileOut.close();
						
					}
//					sheet.createRow((short)num);//���һ���ÿ�
					sheet.removeRow(sheet.getRow(num));
			        conn.write(fileOut);  
			        fileOut.close();
				}		
				JOptionPane.showMessageDialog(null, "�ѽ����Ѵ����ݿ�ɾ��", "��ʾ", JOptionPane.PLAIN_MESSAGE);
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			return true;
			
		}	
		
		//�ƶ�����
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
			        //��row�ｨ����cell����Ԫ�񣩣�����Ϊ�кţ���һ�У�                  //����cell���������͵�ֵ
					currow.createCell(0).setCellValue(String.valueOf(qqcode));     //����cell�������͵�ֵ
					currow.createCell(1).setCellValue(String.valueOf(NickName));   //����cell�ַ����͵�ֵ
					currow.createCell(2).setCellValue(String.valueOf(pwd));    //����cell�������͵�ֵ
					currow.createCell(3).setCellValue(String.valueOf(IpAddr));     //����cell�������͵�ֵ
					currow.createCell(4).setCellValue(String.valueOf(Port));   //����cell�ַ����͵�ֵ
					currow.createCell(5).setCellValue(String.valueOf(Age));    //����cell�������͵�ֵ
					currow.createCell(6).setCellValue(String.valueOf(Sex));     //����cell�������͵�ֵ
					currow.createCell(7).setCellValue(String.valueOf(Nation));   //����cell�ַ����͵�ֵ
					currow.createCell(8).setCellValue(String.valueOf(Star));    //����cell�������͵�ֵ
					currow.createCell(9).setCellValue(String.valueOf(Face));     //����cell�������͵�ֵ
					currow.createCell(10).setCellValue(String.valueOf(Remark));   //����cell�ַ����͵�ֵ
					currow.createCell(11).setCellValue(String.valueOf(Selfsign));    //����cell�������͵�ֵ
					currow.createCell(12).setCellValue(String.valueOf(Status));    //����cell�������͵�ֵ
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
 