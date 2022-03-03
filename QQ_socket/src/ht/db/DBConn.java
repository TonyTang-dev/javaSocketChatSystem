package ht.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
 
 
//这类仅仅负责调入驱动程序 并且连接数据库 其他功能都在库同名javaBean里写
public class DBConn {
	//使用文件存储，不用数据库了
	
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
	        FileOutputStream fileOut = new FileOutputStream("data.xls");//创建文件流
	    	wb.write(fileOut);//把Workbook对象输出到路径path中
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
	