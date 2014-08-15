package xmlReadWrite;
import java.io.File;
import java.io.IOException;
import java.util.*;

import collectionTest.hashedObject;
import collectionTest.objectDes;
import jxl.CellType;
import jxl.DateCell;
import jxl.LabelCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.Cell;
import jxl.CellView;
import jxl.read.biff.BiffException;
public class excelReader {
   
	public static Workbook w = null;
	public String name = null;
	public void readXcelBook(String file,hashedObject hash) throws BiffException, IOException{
		this.name = file;
		w = Workbook.getWorkbook(new File(this.name));
		if(w != null){
			int len = w.getNumberOfSheets();
			System.out.println("Number of Sheets :"+len);
		  for(int kj = 0 ;kj < len ;kj++){
			  
			Sheet sht0 = w.getSheet(kj);
			if(sht0 == null)
				break;
			//System.out.print("Number of Rows:" + sht0.getRows());
			//sht0.getRows();
			for(int i = 1 ; i < sht0.getRows(); i++){
				objectDes tmpObj = new objectDes();
				long longdataLong = 0;
				for(int  j = 0; j < sht0.getColumns(); j++){
						Cell cell = sht0.getCell(j,i);
						System.out.println(cell.getType().toString());
						if(cell.getType()== CellType.EMPTY)
							break;
					if(cell.getType() == CellType.LABEL){
						LabelCell  label = (LabelCell)cell;
						if( j == 3)
							tmpObj.settwitMsg(new String(label.getString()));
						else
							tmpObj.setUserName(label.getString());
						if(j == 1){
							tmpObj.setdbId(new Long(label.getString()).longValue());
							System.out.println("dbID: "+label.getString());
							longdataLong = (new Long(label.getString())).longValue();
						}
						else if(j == 2){
							tmpObj.settwitterId(new Long(label.getString()).longValue());
							System.out.println("LongID: "+label.getString());
						}
					//	System.out.print(label.getString()+" ");
					}else if(cell.getType() == CellType.NUMBER){
						NumberCell numcell = (NumberCell)cell;
						if(j == 1){
							tmpObj.setdbId(new Long((long)numcell.getValue()));
							System.out.println("dbID: "+numcell.getValue());
							longdataLong = (long)numcell.getValue();
						}
						else if(j == 2){
							tmpObj.settwitterId(new Long((long)numcell.getValue()));
							System.out.println("LongID: "+numcell.getValue());
						}
						//System.out.print(cell.getContents()+" ");
					}else if(cell.getType() == CellType.DATE){
						DateCell date = (DateCell)cell;
						Date dt = date.getDate();
						tmpObj.setDate(dt);
						//System.out.print(dt.toString());
					}
				}
				 
					if(hash.setHashdata(new Long(longdataLong), tmpObj) == 1){
						hash.hashCount++;
						System.out.println("hash counting " + hash.hashCount);
					}
					else
						;//System.out.println("data entry fialed for :"+ longdataLong);
						
				
				//System.out.print("\n");
			 }
			}
		}else{
			System.out.println("file does not exist");
		}
		System.out.println("Hash size: "+ hash.getHashsize());
	}
	
}
