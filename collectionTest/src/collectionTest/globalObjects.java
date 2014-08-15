package collectionTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class globalObjects extends Objects {
 
	private String valStr;
	private static final int con = -99999999;
	private int valInt;
	private long valLong;
	private double valDouble;
	private boolean F ;
	private String fileName;
	private String userNames;
	private FileWriter pr;
	private PrintWriter fl;
	private File fobj;
	private globalObjects(){
		valStr = null;
		valInt = con;
		valLong = con;
		valDouble = con;
		userNames = null;
		F = false;
		fileName = null;
		
	}
	public static globalObjects createObj(boolean F,String fileName) throws FileNotFoundException{
		globalObjects tempVar = new  globalObjects();
		tempVar.setF(F);
		tempVar.setFileName(fileName);
		tempVar.setFileOperations();
		return tempVar;
	}
	public void setFileOperations() throws FileNotFoundException{
		fobj = new File(fileName);
		try {
			pr = new FileWriter(fobj,true);
			fl = new PrintWriter(pr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static globalObjects createObjNoParam(){
		return new globalObjects();
	}
	public void setUserName(String name){
		userNames = name;
	}
	public String getUserName(){
		return userNames;
	}
	public void setFileName(String fn){
		fileName = fn;
	}
	public String getFileName(){
		return fileName;
	}
	public boolean isWriteToFile(){
		if(F == true)
			return true;
		else
			return false;
	}
	public void setF(boolean set){
		F = set;
	}
	public void setString(String v){
		valStr = v;
	}
	public void setLong(long v){
	   valLong = v;
	   return;
	}
	public void setInt(int v){
		valInt = v;
		return;
	}
	public int getInt(){
		return valInt;
	}
	public long getLong(){
		return valLong;
	}
	public String getString(){
		return valStr;
	}
	public void print(){
		if(F == true)
			try {
				printToFile();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else
			printToStdOut();
			
	}
	public void printToFile() throws IOException{
		//System.out.print("Start Printing\n");
		if(valLong != con)
			fl.write((new Long(valLong)).toString());
		if(userNames != null)
			fl.write("\t"+userNames);
		if(valStr != null)
			fl.write("\t"+valStr);
		if(valInt != con)
			fl.write("\t"+ valInt);
		
		if(valDouble != con)
			fl.write("\t"+ valDouble);
		fl.println();
		fl.close();
	}
	public void printToStdOut(){
		if(valStr != null)
			
			System.out.print(valStr +" ");
		if(valInt != con)
			System.out.print(valInt + " ");
		if(valLong != con)
			System.out.print(valLong + " ");
		if(valDouble != con)
			System.out.print(valDouble+"\n");
	}
}
