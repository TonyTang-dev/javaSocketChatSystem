package ht.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
 
 
//���������������������� �����������ݿ� �������ܶ��ڿ�ͬ��javaBean��д
public class DBConn {
	//ʹ���ļ��洢���������ݿ���
	
//	public WritableWorkbook DBConn() throws IOException {
//		File file = new File("data.xls");
//		if(!file.exists()) {
//			file.createNewFile();
//		}
//		WritableWorkbook workbook =Workbook.createWorkbook(file);
//		
//		return workbook;
//	}
	
	public HSSFWorkbook getWrokbook() throws IOException {
		File file=new File("data.xls");
		InputStream is=null;
		HSSFWorkbook wb = null;  
		if(!file.exists()) {
			is=new FileInputStream(file);
			wb = new HSSFWorkbook(is);  
			wb.createSheet("mine");
	        wb.createSheet("friends");  
	        wb.createSheet("others");  
	        FileOutputStream fileOut = new FileOutputStream("data.xls");//�����ļ���
	    	wb.write(fileOut);//��Workbook���������·��path��
	    	fileOut.close();
		}
		else {
			is=new FileInputStream(file);
			wb = new HSSFWorkbook(is); 
		}
		is.close();
		return wb;
	}
	
}	
	