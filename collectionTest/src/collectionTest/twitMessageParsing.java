package collectionTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;

import twitter4j.examples.directmessage.SendDirectMessage;

public class twitMessageParsing {
  private Pattern pat;
  private String data;
  private String[] patterns =new String[]{"\\s+","[a-b]*","a(?!b","(\\w)(\\s+)([\\.,])","^[^<>%$?\\//-_:]*$","[0-9]+"};
  private String[] twitterShortWords = new String[]{"rt","sh","?4u","<3","ac","lol","rt,",",rt","rt.","the","The"};
  private String normalizingStringList = "[\\/+*\'~:,\"!?$.]*$";
  private hashedMapobj hashOb ;
  private hashedMapobj hashObCounter ;
  private int patternIndex;
  private ArrayList<objectDes> tempList;
  private Vector<Integer> patternList;
  private findUniqueUsers finduniqueusers;
  private ArrayList<Integer> patternArrList;
  private Matcher match;
  private String[] splittedData;
  private PrintWriter pr;
  private File file_Object_To_Write;
  private File file_Object_To_Read;
  private String fileN = "twitMsg";
  private String pathToReadWrite = "C:\\TwitterBigData\\malletTweetData\\";
  public twitMessageParsing(){
	  finduniqueusers = findUniqueUsers.createFindUniqueUsers(20000);
	  hashOb = new hashedMapobj(50000);
	  hashObCounter = new hashedMapobj(50000);
  }
  public void setPatternList(Vector<Integer> lists){
	  this.patternList =lists; 
  }
  public void setFile_Object_To_Write(String fileName){
	  file_Object_To_Write = new File(fileName);
  }
  public File get_File_To_Write(){
	  if(!file_Object_To_Write.exists())
		try {
			file_Object_To_Write.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  return file_Object_To_Write;
  }
  public void setPrintWriter(){
	  try {
		pr  = new PrintWriter(get_File_To_Write());
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}catch(NullPointerException ex){
		System.err.println(ex.getMessage());
	}
  }
  public Vector<Integer> getPatternList(){
	  return this.patternList;
  }
  public void setData(String str){
	  data = str;
  }
  public void setPatternIndex(int index){
	  patternIndex = index;
  }
  public int getPatternIndex(){
	  return patternIndex;
  }
  public void createPattern(){
	  pat = Pattern.compile(patterns[getPatternIndex()]);
  }
  public void setMatcher(){
	match = pat.matcher(data);  
  }
  public Matcher getMatcher(){
	  return match;
  }
  
  public String getData(){
	  return data; 
  }
  public ArrayList<Integer> startParsing(){
	  ArrayList<Integer> matchedPattern = new ArrayList<Integer>(patternList.size());
	  for(int i = 0 ; i < patternList.size(); i++){
		  setPatternIndex(patternList.elementAt(i).intValue());
		  createPattern();
		  setMatcher();
		  if(getMatcher().find() ==true){
			  matchedPattern.add(new Integer(i));
		  }
		  
	  }
	  return matchedPattern;
  }
  public boolean setFile_object_To_Read(String str){
	  file_Object_To_Read = new File(str);
	  if(!file_Object_To_Read.exists()){
			return false;		
	  }
	  return true;
  }
  public File getFile_object_To_Read(){
	  return  file_Object_To_Read;
	  
  }
  public void writeProcessedData()throws IOException{
	  
	  int i = 0 ;
	  while(i< tempList.size()){
		  //File fi = new File("D:\\TwitterData\\mallet\\output\\"+ fileN + i++ +".txt");
		  objectDes  object = tempList.get(i);
		  if(object == null){
			  i++;
			  continue;
		  }
		  setFile_Object_To_Write(pathToReadWrite+ fileN + i++ +".txt");
		  setPrintWriter();
		  if(object.twitMsg!=null)
		    pr.println(object.twitMsg);
		  pr.close(); 
	  }
	  System.out.println("Finish writing the files");
	  
  }
  public void closeFileWriterObject(){
	  if(pr != null)
		  pr.close();
  }
  public void preProcessingData(int choice){	  
	  int i = 0;
	  FileReader fileReader = null;
	  BufferedReader bfReader = null;;
	  hashedMapobj hash = null;
	  PrintWriter printwriter =null;
	  if(choice == 2){
		  hash = new hashedMapobj(300);
		  setFile_Object_To_Write("D:\\TwitterData\\hdp\\dataFileTwitMessageQuit.txt");
		  setPrintWriter();
		  try {
			printwriter = new PrintWriter(new File("D:\\TwitterData\\hdp\\dataFileTwitMessageQuit.dat"),"UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }		 
	  int indexOfEachWord = 0;
	  for(;;){
		 // System.out.println("number of files processed: "+ i);
		 if( !setFile_object_To_Read("D:\\TwitterData\\malletOutput\\preProcessedQuit\\"+fileN+ i++ + ".txt")){
			 System.out.println("got out here no files");
			 break;
		 }
		 try {
			fileReader = new FileReader(getFile_object_To_Read());
		 } catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		 }
		 bfReader = new BufferedReader(fileReader);
		 try{			
			String tempStr;
			String twitMsg = "";
			while((tempStr = bfReader.readLine()) != null ){
				twitMsg = twitMsg +" "+ tempStr;
		    }
			String[] strings = finduniqueusers.stringTokenizer(twitMsg, " ", 300);
			for(int j = 0; j < strings.length; j++){
				if(strings[j] != null){
					if(choice == 2){
						if(hashOb.isContain(strings[j]))
							hash.setData(strings[j], new Long(1));
					}
					else{
						hashObCounter.setData(strings[j], new Long(1));
						//System.out.println("filling the hashObCounter object");
						//System.out.println(hashOb.isContain(strings[j]));
						if(!hashOb.isContain(strings[j])){
							//System.out.println("Filling hashOb object");
							hashOb.setData(strings[j], new Long(indexOfEachWord++));
						}
					}
				}else{
					System.out.print("String is null after: "+ j+"th iteration");
					break;
				}
			}
			if(choice == 2){
				if(hash.getSize() != 0 ){
					pr.print(hash.getSize());
					Set<Entry<String,Long>> sets = 	hash.getEnumset();
					Iterator<Entry<String,Long>> iterator = sets.iterator();
					for(;iterator.hasNext();){
						Entry<String,Long> it = iterator.next();
						pr.print(" ");
						pr.print(hashOb.get(it.getKey()) +":" + it.getValue() );
		    	 
					}
					pr.println();
					hash.clearContainer();
					hash.totalObj = 0;
				}
			}
			if(fileReader != null)
				fileReader.close();
			if(bfReader != null)
				bfReader.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IOException ex){
			System.err.println(ex.getMessage());
		}
		 finally{
			
		}
			
	  }
	  System.out.println("Size of hashOb after phase " +choice +": "+hashOb.getSize() );
	  System.out.println("Size of the hashObCounter after phase "+choice + " "+ hashObCounter.getSize());
	  if(choice == 2){
		  System.out.println("Wrting to the file the tokens");
		  Set<Entry<String, Long>> tempSet = hashOb.getEnumset();
		  Iterator<Entry<String, Long>> iterate = tempSet.iterator();
		  while(iterate.hasNext()){
			  Entry<String,Long> itOb = iterate.next();
			  printwriter.println(itOb.getKey());
		  }		  
	  }
	  if(choice == 2){
			printwriter.close();
			closeFileWriterObject();
	  }
		  
  }
  public void removeElementsFromHashOb(){
	  Set<Entry<String,Long>> operationOverSet = hashObCounter.getEnumset();
	  Iterator<Entry<String,Long>> iterate = operationOverSet.iterator();
	  System.out.println("Size of the hashobCounter berofre dumping " + hashObCounter.getSize());
	  System.out.println("Size of hashOb conatiner before dumping: "+ hashOb.getSize());
	  int countout = 0;
	  while(iterate.hasNext()){
		  
		  Entry<String,Long> setElement = iterate.next();
		  int count = hashObCounter.get(setElement.getKey());
		  if(count <= 2){
			  countout++;
			  hashOb.removeData(setElement.getKey());
		  }
	  }
	  System.out.println("Size of the hashObcounter after dumping " + hashObCounter.getSize());
	  System.out.println("Size of HashOb container after dumping: "+ countout +" hashob "+ hashOb.getSize());
	  
	  return;
  }
  public void findTwitShortWords()throws NullPointerException{
	  for(int i = 0 ; i < tempList.size(); i++){
		  String[] tmpString = finduniqueusers.stringTokenizer(tempList.get(i).twitMsg, " ", 200);
		  String totalMsg="";
		  for(int j = 0; j < tmpString.length; j++){
			  String strval =tmpString[j];
			  if(strval == null)
				  break;
				  try {
			            URL url = new URL(strval);
			            // If possible then replace with anchor...
			            //System.out.println("<a href=\"" + url + "\">"+ url + "</a> " );
			            continue;
			        } catch (MalformedURLException e) {
			            // If there was an URL that was not it!...
			            
			        }
				 // System.out.println(strval);
				  //Matcher match = pattern.matcher(strval.toLowerCase());
				  Pattern pat = Pattern.compile("^[^<>%$?\\//-_:# @,&() ]*$");
				  strval = strval.toLowerCase();
				  int index = 0;
				  for(int k = 0; k < twitterShortWords.length; k++){
					  if(strval.equals(twitterShortWords[k])){
						  index = 1;
						  break;
					  }
				  }
				  Matcher match = pat.matcher(strval);
				  if(match.find() == false)
					  continue;
				  if(index == 1 )
					  continue;
						  
				  if(strval.contains("#")){
					  //index = strval.indexOf('#');
					  strval = strval.replace("#", "");
				  } 
			
				  if(strval.contains("@")){
					  continue;
				  }
				
				  Pattern patMe = Pattern.compile("\\w+-*\\w+");
					String tempStrval="";
					try{
						   Matcher matchMe = patMe.matcher(strval);
						   while(matchMe.find()){
							   tempStrval += matchMe.group();
							   tempStrval +=" ";
								   //System.out.println(t);
							   strval = strval.replace(matchMe.group(),"");
							   //System.out.println(str);
							   matchMe = patMe.matcher(strval);
							   
						   }
						
					}catch(NullPointerException ex){
							System.err.println(ex.getMessage());
						}
					strval = tempStrval.trim();
				  totalMsg += strval;
				  totalMsg +=" ";
				  			
		  }
		  tempList.get(i).settwitMsg(totalMsg);
	  }
  }
  public String[] splitData(){
	  String[] tempData = data.split(patterns[getPatternIndex()]);
	  splittedData = tempData;
	  return splittedData;
  }
  public void printData(int index){
	  System.out.println(data.replaceAll(patterns[getPatternIndex()], "$1$3"));
  }
  public void readDatafile() throws IOException{
	  
		int i = 0;
		tempList = new ArrayList<objectDes>();
		while(true){
			File fi = new File(pathToReadWrite+ fileN + i++ +".txt");
			if(!fi.exists())
				break;
			//messageParser msgParser1 = new messageParser("D:\\TwitterData\\mallet\\"+ fileN + i++);//option3
			FileReader fReader = new FileReader(fi);
			BufferedReader bf = new BufferedReader(fReader);
			String Str;
			String totalStr = "";
			try {
				while((Str = bf.readLine()) != null ){
					totalStr =totalStr+" "+ Str;
				}
				objectDes obj = new objectDes();
				obj.settwitMsg(totalStr);
				tempList.add(obj);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				fReader.close();
				bf.close();
			}
				
		}
		System.out.println(tempList.size());
  }
}
