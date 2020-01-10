package GroupName.Pro_1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class Myxlssjk {
	
	 private Sjk sjk;
	
     public Sjk getSjk() {
		return sjk;
	}

	public void setSjk(Sjk sjk) {
		this.sjk = sjk;
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
    	createsjk();
	}

	private static void createsjk() throws FileNotFoundException, IOException {
		InputStream is=new FileInputStream("C:/zifang/sjk.xls");//1.获取输入流
 		Workbook  wb= new HSSFWorkbook(is);//2.创建一个此文件流的workbook以对应excel
 		Sheet sheet1=wb.getSheetAt(0);//3.对应sheet
 		Row row=sheet1.getRow(0);//获取表名
 		String bm=row.getCell(1).getStringCellValue().toUpperCase();
 		System.out.println("表名:"+bm);
 		
 		Iterator<Row> rows=sheet1.rowIterator();//4.获得sheet 的 row
 		List<String> isIndex=new ArrayList<String>();
  		List<String> isMain=new ArrayList<String>();
        List<Sjk> list=new ArrayList();
 		while(rows.hasNext()){                  // 5.遍历输出
 		 Row row1=rows.next();
 		 String ziduan=row1.getCell(0).getStringCellValue().trim().toUpperCase();
 		 String type=row1.getCell(1).getStringCellValue().trim();
 		 row1.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
 		 String length=row1.getCell(2).getStringCellValue();
 		 String ismain=row1.getCell(3).getStringCellValue();
 		 String isindex=row1.getCell(4).getStringCellValue();
 		 String isnull=row1.getCell(5).getStringCellValue();
 		 Sjk sjk=new Sjk();
 		 sjk.setName(ziduan);
 		 sjk.setType(type);
 		 sjk.setLength(length);
 		 sjk.setIsmain(ismain);
 		 sjk.setIsindex(isindex);
 		 sjk.setIsnull(isnull);
 		 if(ziduan.equals("表名")||ziduan.equals("字段名")){
// 			System.out.println("字段名"+" "+"字段类型"+" "+"长度"+" "+"主键"+" "+"索引"+"允许为null");
 			 continue;
 		 }
// 		 System.out.println(sjk.getName()+" "+sjk.getType()+" "+sjk.getLength()+" "+sjk.getIsmain()+" "+sjk.getIsindex());
// 		 System.out.println(ziduan+" "+type+" "+length+" "+ismain+" "+isindex);
 		 list.add(sjk);
 		
 		}
// 		System.out.println(sendMap);
// 		sendMap.remove("请求字段");
// 		for (Object s : sendMap.keySet().toArray()) {
// 			String v=(String) sendMap.get(s);
// 			System.out.println("map.put(\""+s+"\",\""+v+"\");");
// 		}
 		is.close();                             //7.关闭流
 		makingSql(list,bm,isMain,isIndex);
	}
	
	public static void makingSql(List<Sjk> list,String bm,List isMain,List isIndex){// 建表语句
		System.out.println("*********************************************************************");
		System.out.println("set current schema netbank;");
		System.out.println("CREATE TABLE NETBANK."+bm+ " (");
		for (int i = 0; i < list.size(); i++) {
			System.out.println("   "+list.get(i).getName()+ "    "
		    + msg(list.get(i).getType(),list.get(i).getLength())+ isNull(list.get(i).getIsnull())+ ((i==list.size()-1)&&(isMain.size()==0)?"":","));
		}
		if(isMain.size()>0){
			System.out.print("  CONSTRAINT  PK_"+bm+" PRIMARY KEY("+isMain.get(0));
			if(isMain.size()>1){
				for (int i = 1; i < isMain.size(); i++) {
					System.out.print(","+isMain.get(i));
				}
			}
			System.out.println(")");
		}
		System.out.println(")IN DMS_DATA INDEX IN DMS_IDX;");
		System.out.println();
		
//		 CREATE INDEX I_REALESTATECODE_INDEX
//			ON REALESTATECODE(CODE,NAME);
	    if(isIndex.size()>0){
	    	System.out.print("CREATE INDEX I_"+bm+"_INDEX("+isIndex.get(0));
			if(isIndex.size()>1){
				for (int i = 1; i < isIndex.size(); i++) {
					System.out.print(","+isIndex.get(i));
				}
			}
			System.out.println(");");
	    }
		
		System.out.println("grant control on table netbank."+bm+" to user netbank;");
		System.out.println("grant select on table netbank."+bm+" to user jssjcx;");
		System.out.println("grant select,update on table netbank."+bm+" to user jssjwh;");
		System.out.println("*********************************************************************");
		
	}
	
	
	public static String msg(String type,String length){
		if(type.equals("VARCHAR")){
			return type+"("+length+")";
		}
		return type;
		
	}
	
	public static String isNull(String isnull){
		if(isnull.equals("N")){
			return "  NOT NULL";
		}
		return "";
		
	}
}
