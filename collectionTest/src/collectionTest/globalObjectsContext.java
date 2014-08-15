package collectionTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class globalObjectsContext {
	private Objects dataContainer;
	private multiHashedMapObj multiDataContainer;
	private hashedMapobj hashMaperContainer;
	private String fileName;
	private String outputFileName;
	private File fileObj;
	private File outputFileObj;
	private PrintWriter pr;
	private FileReader fileReader;
	private BufferedReader bufferFileReader;
	private ArrayList<String> arr;
	public static Pattern patNum = Pattern.compile("[0-9]+");
	public static Pattern patStr = Pattern.compile("[a-zA-Z]+");
	public static Pattern patSpec = Pattern.compile("^[^<>%$?\\//-_:# @,&() ]*$|[a-zA-Z]*");
	public static Pattern patS = Pattern.compile("[^\\w\\*]");
	private static int record;
	private globalObjectsContext(){
		dataContainer = null;
		fileName = null;
		arr = null;
		record = 0;
	}
	public static globalObjectsContext creatorOfObjects(Objects obj,String file,String output){
		globalObjectsContext tempVar = new globalObjectsContext();
		tempVar.setFileName(file);
		tempVar.setOutputFileName(output);
		tempVar.setDataContainer(obj);
		tempVar.setHashMaperContainer();
		return tempVar;
	}
	public void startFillUniqueHashMap() throws IOException{
		String prevStr;
		try{
		while((prevStr = bufferFileReader.readLine())!= null){
			String[] strArr = prevStr.split("\t");
			globalObjects gb = globalObjects.createObj(true,"C:\\TwitterBigData\\userRetweetingGroup\\topRetweetedUserProfiles.txt");
			gb.setLong((new Long(strArr[0])).longValue());
			gb.setUserName(strArr[1]);
			if(strArr.length == 3)
				gb.setString(strArr[2]);
			if(dataContainer instanceof multiHashedMapObj){
				if(!(((multiHashedMapObj)dataContainer).isContain(strArr[0])))
					((multiHashedMapObj)dataContainer).put(strArr[0],gb );//previous;y it was strArr[0] for userIds
			}
			
		}
		}catch(NullPointerException ex){
			System.out.print(ex.getMessage());
		}finally{
			
		}
		
	}
	public void startFillHashMap(){
		String tempStr;
		String prevStr;
		String currStr;
		String nextStr;
		try {
			//while(true){
			//prevStr = bufferFileReader.readLine();
			prevStr = bufferFileReader.readLine();
			int ij = 0;
				while(prevStr  != null){
					currStr = prevStr;
					//String strVal = currStr.split("\t")[0].trim();
					//while(true){
				try{
						do{
						
							nextStr = bufferFileReader.readLine();
							ij++;
					//		if(ij > 50000)
						//		break;
							
							if(nextStr == null)
								break;
							if(nextStr.length() == 0)
								continue;
							String strVal = nextStr.split("\t")[0].trim();
							//System.out.println(strVal);
							Matcher matchSpec = patS.matcher(strVal);
							Matcher matchStr = patStr.matcher(strVal);
							Matcher matchNum = patNum.matcher(strVal);
							if( matchStr.find() == false && !matchSpec.find() == true){
								//System.out.print((new String(matchStr.find())).toString() + !matchSpec.find());
								break;
							}
							System.out.print("Reach Here\n");
							currStr = currStr + " "+ nextStr;
							
						}while(true);
							prevStr = currStr;
							//break;
					
					//}
					String[] arrStr = prevStr.split("\t");
				//	System.out.print(arrStr[0] + " "+arrStr[1] +" ");
					/*if(arrStr.length == 3)
						System.out.println(arrStr[2]);
					else
						System.out.println();*/
						System.out.println("Record :"+ ++record);
					if(arrStr.length < 3){
						prevStr = nextStr;
						continue;
					}
					globalObjects gb = globalObjects.createObj(true, outputFileName);
					System.out.println(arrStr[0]);
					
					gb.setUserName(arrStr[1]);
					if(arrStr.length == 3)//previously it was 3
					  gb.setString(arrStr[2]);
					gb.setLong((new Long(arrStr[0]).longValue()));//changed to 1 for userprofile content
					if(dataContainer instanceof multiHashedMapObj){
						
						((multiHashedMapObj)dataContainer).put(arrStr[0],gb );
					}
					prevStr = nextStr;
				}catch(NullPointerException ex){
					System.out.println(ex.getMessage());
				}
					
				}
			//}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return;
	}
	public void setHashMaperContainer(){
		hashMaperContainer = new hashedMapobj();
	}
	public void setFileToReadWrite() throws FileNotFoundException{
		fileObj = new File(fileName);
		outputFileObj = new File(outputFileName);
		pr = new PrintWriter(outputFileObj);
		fileReader  = new FileReader(fileObj);
		bufferFileReader = new BufferedReader(fileReader);
	}
	public void setFileToRead(String fileToRead) throws FileNotFoundException{
		fileObj = new File(fileToRead);
		fileReader = new FileReader(fileObj);
		bufferFileReader = new BufferedReader(fileReader);
	}
	public void setFileInput()throws FileNotFoundException{
		outputFileObj = new File(outputFileName);
		fileReader  = new FileReader(outputFileObj);
		bufferFileReader = new BufferedReader(fileReader);
	}
	public void closeOutputStream(){
		pr.close();
	}
	public void closeInputStream() throws IOException{
		bufferFileReader.close();
	}
	public void setOutputFileName(String fileNames){
		this.outputFileName = fileNames;
		return;
	}
	public String getOutputFileName(){
		return this.outputFileName;
	}
	public void setFileName(String fileNames){
		this.fileName = fileNames;
		return;
	}
	public void readFromfile(){
		
		return;
	}
	public String getFileName(){
		
		return this.fileName;
	}
	public multiHashedMapObj getContainer(){
		return multiDataContainer;
	}
	public void setDataContainer(Objects obj){
		dataContainer = obj;
		if(dataContainer instanceof multiHashedMapObj){
			multiDataContainer = (multiHashedMapObj)dataContainer;
		}
	}
	
	public void inceptionOfContextOperation(){
		arr = multiDataContainer.getKeys();
		for(int i = 0; i < arr.size(); i++)
			hashMaperContainer.setData(arr.get(i), new Long(1));
	}
	public void dataComparision(multiHashedMapObj container,int choice) throws FileNotFoundException{
		//PrintWriter print = new PrintWriter(new File(outputfilePath));
		 arr = container.getKeys();
		 for(int i = 0; i < arr.size();i++){
			 if(multiDataContainer.isContain(arr.get(i))){
				 Set<Objects> tmpdata = multiDataContainer.getData(arr.get(i));
				 Iterator<Objects> it= tmpdata.iterator();
				 if(choice == 0){
					 while(it.hasNext()){
						 globalObjects gb = (globalObjects)it.next();
						 gb.print();
					 }
				 }else{
					 if(it.hasNext()){
						 globalObjects gb = (globalObjects)it.next();
						 gb.print();
					 }
				 }
			 
				 multiDataContainer.removeData(arr.get(i));
			 }
		 }
	}
	public void writeToFile(){
		multiDataContainer.print();
	}
	public void startParsing(){
		
	}
	public void startCounting(){
		
	}
	public void doArrayTraverseOPeration(ArrayList<String> arrlist) {
		// TODO Auto-generated method stub
		for(int i = 0; i < arrlist.size();i++){
			Set<Objects> tmpData = multiDataContainer.getData(arrlist.get(i));
			Iterator<Objects> it = tmpData.iterator();
			if(it.hasNext()){
				globalObjects gb = (globalObjects)it.next();
				gb.print();
			}
		}
	}
}
