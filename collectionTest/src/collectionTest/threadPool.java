package collectionTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

public class threadPool implements Runnable{

	private String pathName = "";
	private String pathNameOutput = "";
	private PrintWriter pr = null;
	private File fileObjectForRead = null;
	private File fileObjectForWrite = null;
	private FileReader fr = null;
	private BufferedReader br = null;
	private long looper;
	private long currentPoint;
	private int max_range = 25000;
	private expandedUrls expandUrl;
	private int mentioned_num_of_fields;
	private int field_to_expand;
	public threadPool(String argsInput,String argsOutput,long looperLenght,int number_of_fields,int expanded_index,int range){
		pathName = argsInput;
		pathNameOutput = argsOutput;
		looper = looperLenght;
		currentPoint = 0;
		mentioned_num_of_fields = number_of_fields;
		field_to_expand = expanded_index;
		this.max_range = range;
		//expandUrl = new expandedUrls();
	}
    public void setFieldToexpand(int index){
    	this.field_to_expand = index;
    	return;
    }
    public int getFieldToexpand(){
    	return this.field_to_expand;
    }
	public void openFileToRead() throws IOException{
		fileObjectForRead = new File(pathName);
		Vector<Vector<String>> vec = new Vector<Vector<String>>();
		fr = new FileReader(fileObjectForRead);
		br = new BufferedReader(fr);
		pr = new PrintWriter(new FileWriter(pathNameOutput,true));
		expandUrl = new expandedUrls();
		try {
			
			int  i = 0;
			String tempStr="";
			String tempS = br.readLine();
			System.out.println("get the looper lenght: " + looper);
			while(i++ < looper && tempS != null) tempS = br.readLine();
			System.out.println("this is startof thread: "+i);
			if(tempS == null ){
				throw new NullPointerException("its a null Object");
			}
			System.out.println("this is initiation:" + tempS);
			i= 0;
			while((tempStr = br.readLine()) != null && i < this.max_range){
				System.out.println("Stage 1");
				try{
					String[] tempStrArr = tempStr.split("\t");
					Vector<String> element = new Vector<String>();
					if(tempStrArr.length < 2)
						continue;
					for(int j = 0; j < tempStrArr.length; j++)
						if(j != field_to_expand)	
							element.add(tempStrArr[j]);
					element.add(tempStrArr[field_to_expand]);
					//element.add(tempStrArr[0]);
					//element.add(tempStrArr[1]);
					int size = tempStrArr.length;
					System.out.println("Stage 2");
					//if(size >= 3){
					String urlString;
						//element.add(tempStrArr[2]);
						//synchronized (expandedUrls.class) {
					urlString = expandedUrls.getExpandedUrls(tempStrArr[field_to_expand]);
						//}
					System.out.println("stage: "+ i +"url:" + urlString);
					element.add(urlString);
					//}
					vec.add(i++, element);
					//processCommand();
				}catch(NullPointerException ex){
					System.out.println("level inner :"+ex.getMessage());
				}finally{
					
				}
				//hashMap.setData(tempStrArr[3],  expandedUrls.getExpandedUrls(tempStrArr[3]));
				//System.out.println("this is end");
			}
		}catch(IOException io){
			System.out.print("Outer Level: "+io.getMessage());
		}catch(NullPointerException nullpointer){
			System.out.println("Null Pointer Exception: "+ nullpointer.getMessage());
		}finally{
				for(int i1 =0 ; i1 < vec.size(); i1++){
					for(int j = 0; j < vec.elementAt(i1).size(); j++)
						if(j != vec.elementAt(i1).size() - 1)
							pr.print(vec.elementAt(i1).elementAt(j) + "\t");
						else
							pr.print(vec.elementAt(i1).elementAt(j));
					pr.print("\n");
				}	
				br.close();
				pr.close();
			
		}
		
	
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			openFileToRead();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void processCommand(int delay){
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void printer(){
		System.out.println("current pointer in thread " +Thread.currentThread().getName() +": "+ currentPoint);
	}
}
