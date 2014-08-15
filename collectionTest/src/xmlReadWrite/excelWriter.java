package xmlReadWrite;

import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import jxl.Cell;
import jxl.CellFeatures;
import jxl.CellView;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.LabelCell;
import jxl.format.CellFormat;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.*;

import jxl.write.biff.RowsExceededException;
import jxl.write.biff.WritableFonts;
import jxl.write.biff.WritableWorkbookImpl;
public class excelWriter {
	
	public WritableCellFormat boldunderLine;
	public WritableCellFormat dataTimes;
	public String filename;
	public BufferedReader bf;
	public void createLabel(WritableSheet sheet,String[] cols,int iD) throws WriteException{
		WritableFont times10 = new WritableFont(WritableFont.TIMES,10);
		dataTimes = new WritableCellFormat(times10);
		dataTimes.setWrap(true);
		WritableFont timesBold = new WritableFont(WritableFont.TIMES,10,WritableFont.BOLD,false, UnderlineStyle.SINGLE);
		boldunderLine = new WritableCellFormat(timesBold);
		boldunderLine.setWrap(true);
		for(int i = 0; i < iD;i++){
			addCaption(sheet,i,0,cols[i]);
		}
		return;
	}
	public void createDatabook(WritableSheet sheet,WritableWorkbook wb){
		String str;
		Pattern pat = Pattern.compile("[0-9]+");
		Pattern pat1 = Pattern.compile("[a-zA-Z]+");
		Pattern pat2 = Pattern.compile("^[^<>%$?\\//-_:]*$");
		Matcher mat,mat1,mat2;
		int sheetNo = 0;
		try {
			int i =1,j=0;
			while((str = bf.readLine()) != null){
				StringTokenizer st = new StringTokenizer(str, "\t");
				while(st.hasMoreElements()){
					String strdata = (String)st.nextElement();
					mat = pat.matcher(strdata);
					mat1 = pat1.matcher(strdata);
					mat2 = pat2.matcher(strdata);
					if(mat.find() && !mat1.find() && mat2.find())
						addNumber(sheet, j, i, new Long(strdata));
					else
						addLabel(sheet, j, i, strdata);
					j++;
				}
				i++;
				if((i % 60000)== 0){
					i =  i % 60000;
					sheet = wb.createSheet("DataTwit"+ ++sheetNo,  sheetNo);					 
				}
					
				j = 0;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
	public void addLabel(WritableSheet wt, int cols,int rows, String str) throws RowsExceededException, WriteException{
		Label label;
		label = new Label(cols,rows,str,dataTimes);
		wt.addCell(label);
		return;
	}
	public void addNumber(WritableSheet sh,int cols,int rows,Long num) throws RowsExceededException, WriteException{
		Number number;
		WritableCellFormat cellFormat = new WritableCellFormat(NumberFormats.INTEGER);
		number = new Number(cols,rows,num,cellFormat);
		sh.addCell(number);
		return;
	}
	public void addCaption(WritableSheet sheet,int cols,int rows,String str) throws RowsExceededException, WriteException{
		Label label;
		label = new Label(cols, rows, str,boldunderLine);
		sheet.addCell(label);
	}
	public void writeTofile() throws IOException, WriteException{
		
		File descriptor = new File(this.filename);
		if(!descriptor.exists())
			descriptor.createNewFile();
		WorkbookSettings wbsettings = new WorkbookSettings();
		wbsettings.setLocale(new Locale("en","EN"));
		WritableWorkbook wb = Workbook.createWorkbook(descriptor,wbsettings);
		wb.createSheet("DataTwit", 0);
		WritableSheet sh = wb.getSheet(0);
		String tmpStr = bf.readLine();
		StringTokenizer st = new StringTokenizer(tmpStr,"\t");
		String[] cols = new String[60];
		int i = 0;
		while(st.hasMoreElements()){
			cols[i++] = (String)st.nextElement();
		}
		createLabel(sh,cols,i);
		createDatabook(sh,wb);
		wb.write();
		wb.close();
	}
	public void mainStart(String filename, String sourceFile) throws FileNotFoundException{
		bf = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFile)));
		this.filename = filename;
		try {
			writeTofile();
		} catch (WriteException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
