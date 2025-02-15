package api.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class XLUtils {
	
	public static FileInputStream fi;
	public static FileOutputStream fo;
	public static XSSFWorkbook wb;
	public static XSSFSheet ws;
	public static XSSFRow row;
	public static XSSFCell cell;

	
	
	public static int getRowCount(String xlfile, String xlsheet) throws IOException {
	    fi = new FileInputStream(xlfile);
	    wb = new XSSFWorkbook(fi);
	    ws = wb.getSheet(xlsheet);

	    if (ws == null) {
	        throw new IOException("Sheet '" + xlsheet + "' not found in the file.");
	    }

	    int rowcount = ws.getLastRowNum();
	    wb.close();
	    fi.close();
	    return rowcount;
	}
	
	
	public static int getCellCount(String xlfile,String xlsheet,int rownum) throws IOException
	{
		fi=new FileInputStream(xlfile);
		wb=new XSSFWorkbook(fi);
		ws=wb.getSheet(xlsheet);
		row=ws.getRow(rownum);
		int cellcount=row.getLastCellNum();
		wb.close();
		fi.close();
		return cellcount;
	}
	
	
	public static String getCellData(String xlfile, String xlsheet, int rownum, int colnum) throws IOException {
	    fi = new FileInputStream(xlfile);
	    wb = new XSSFWorkbook(fi);
	    ws = wb.getSheet(xlsheet);
	    row = ws.getRow(rownum);

	    String data = "";

	    if (row == null) {
	        // If the row is null, return an empty string or handle accordingly
	        return data;
	    }

	    cell = row.getCell(colnum);

	    if (cell == null) {
	        // If the cell is null, return an empty string
	        return data;
	    }

	    try {
	        DataFormatter formatter = new DataFormatter();
	        data = formatter.formatCellValue(cell);
	    } catch (Exception e) {
	        // Handle any parsing errors
	        data = "";
	    }

	    wb.close();
	    fi.close();
	    return data;
	}
	
	public static void setCellData(String xlfile,String xlsheet,int rownum,int colnum,String data) throws IOException
	{
		fi=new FileInputStream(xlfile);
		wb=new XSSFWorkbook(fi);
		ws=wb.getSheet(xlsheet);
		row=ws.getRow(rownum);
		cell=row.createCell(colnum);
		cell.setCellValue(data);
		fo=new FileOutputStream(xlfile);
		wb.write(fo);		
		wb.close();
		fi.close();
		fo.close();
	}
	
	
}
