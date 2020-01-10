package GroupName.Pro_1;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class MyxlsTest {

	/** 测试数据
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		InputStream is=new FileInputStream("C:/zifang/cs.xls");//1.获取输入流
		Workbook  wb= new HSSFWorkbook(is);//2.创建一个此文件流的workbook以对应excel
		Sheet sheet1=wb.getSheetAt(0);//3.对应sheet
		Iterator<Row> rows=sheet1.rowIterator();//4.获得sheet 的 row
		Map sendMap=new HashMap();
		while(rows.hasNext()){                 // 5.遍历输出
		 Row row1=rows.next();
		 row1.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
		 row1.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
		 String s=row1.getCell(0).getStringCellValue().trim();
		 String v=row1.getCell(1).getStringCellValue().trim();
		 sendMap.put(""+s, ""+v);
		}
		sendMap.remove("请求字段");
		for (Object s : sendMap.keySet().toArray()) {
			String v=(String) sendMap.get(s);
			System.out.println("map.put(\""+s+"\",\""+v+"\");");
		}
		is.close();                             //7.关闭流
	
	}

	
}
