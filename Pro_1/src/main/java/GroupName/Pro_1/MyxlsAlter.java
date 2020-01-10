package GroupName.Pro_1;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class MyxlsAlter {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		InputStream is=new FileInputStream("C:/zifang/sjk.xls");//1.获取输入流
 		Workbook  wb= new HSSFWorkbook(is);//2.创建一个此文件流的workbook以对应excel
 		Sheet sheet1=wb.getSheetAt(1);//3.对应sheet
 		Row row=sheet1.getRow(0);//获取表名
 		String bm=row.getCell(1).getStringCellValue().toUpperCase();
 		System.out.println("表名:"+bm);
        String gn=row.getCell(3).getStringCellValue();
        System.out.println("功能:"+gn);
 		Iterator<Row> rows=sheet1.rowIterator();//4.获得sheet 的 row
        List<Sjk> list=new ArrayList();
 		while(rows.hasNext()){                  // 5.遍历输出
 		 Row row1=rows.next();
 		 String caozuo=row1.getCell(0).getStringCellValue().trim();
 		 String ziduan=row1.getCell(1).getStringCellValue().trim().toUpperCase();
 		 String type=row1.getCell(2).getStringCellValue().trim();
 		 row1.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
 		 String length=row1.getCell(3).getStringCellValue();
 		 row1.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
 		 String ismain=row1.getCell(4).getStringCellValue();
 		 String isindex=row1.getCell(5).getStringCellValue();
 		 String isnull=row1.getCell(6).getStringCellValue();
 		 Sjk sjk=new Sjk();
 		 sjk.setName(ziduan);
 		 sjk.setType(type);
 		 sjk.setLength(length);
 		 sjk.setIsmain(ismain);
 		 sjk.setIsindex(isindex);
 		 sjk.setIsnull(isnull);
 		 sjk.setCaozuo(caozuo);
 		 list.add(sjk);
 		 if(ziduan.equals("表名")||ziduan.equals("字段名")){
 			 continue;
 		 }
 		}
 		makingsql(list,gn,bm);
	}

	public static void makingsql(List<Sjk> list,String gn,String bm){
		System.out.println("**************************************");
		Sjk sjk=new Sjk();
		if(gn.equals("新增")){
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).getCaozuo().equals("新增")){
					sjk=list.get(i);
					System.out.println("ALTER TABLE "+bm+" ADD "+list.get(i).getName()+" "+list.get(i).getType()+"("+list.get(i).getLength()+")" +(list.get(i).getIsnull().equals("Y")?";":" NOT NULL;") );
				}
			}
		}else if(gn.equals("修改字段名")){
			Sjk  sb=new Sjk();
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).getCaozuo().equals("源字段")){
					sb=list.get(i);
					break;
				}
			}
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).getCaozuo().equals("修改")){
					sjk=list.get(i);
				    System.out.println("ALTER TABLE "+bm+" CHANGE "+sb.getName()+" "+list.get(i).getName()+" "+list.get(i).getType()+"("+list.get(i).getLength()+")" +(list.get(i).getIsnull().equals("Y")?";":" NOT NULL;") );
				}
			}
		}else if(gn.equals("修改字段类型")){
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).getCaozuo().equals("修改")){
					sjk=list.get(i);
				    System.out.println("ALTER TABLE "+bm+" ALTER COLUMN "+list.get(i).getName()+" SET  DATA TYPE "+list.get(i).getType()+ "("+list.get(i).getLength()+");" );
				}
			}
		}else{
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).getCaozuo().equals("删除")){
					sjk=list.get(i);
					if(list.get(i).getCaozuo().equals("删除")){
						sjk=list.get(i);
					    System.out.println("ALTER TABLE "+bm+" DROP "+list.get(i).getName()+";" );
					}
				}
			}
		}
		System.out.println("**************************************");
	}
}
