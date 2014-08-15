package collectionTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.*;

//import twitter4j.examples.help.GetPrivacyPolicy;

public class messageParser {
  public ArrayList<objectDes> Msg;
  public long[] counter = new long[10];
  public String fileName;
  private File fileT;
  private File fileobj;
  private PrintWriter pr;
  private int setParsingOption;
  public Vector<objectDes> vectorContainer = new Vector<objectDes>();
  public String pat = ".*e/-cigs.*|  .*ecigs.*| .*ecig.*|.*cigs.*| .*liquids.* |.*vape.* |.*vapor.* | .*vaping.*|.*e/-cig.*|.*cig.*";
  public long Total ;
  public messageParser(String filename){
	  for(int i = 0; i < 10; i++)
		  counter[i] = 0;
	  fileName = filename;
	  setParsingOption = 0;
	  fileobj = new File(fileName);
	  Total = 0;
  }
  public void setArraylist(ArrayList<objectDes> str){
	  this.Msg = str;
	  System.out.println("size of arraylist: " + this.Msg.size());
  }
  public void setParsing(int set){
	  setParsingOption = set;
  }
  public int getParsing(){
	  return  setParsingOption;
  }
  public int getDecisionOverQuit(String strval){
	  int breakLevel = 0;
	  if(strval.contains("quit")||strval.contains("quitting")||strval.contains("injur")||strval.contains("harmful")){
		  breakLevel = 1;
		 // Total++;
		  //pr.println(Msg.get(i).getdbId()+"\t"+Msg.get(i).twitMsg);
	  }else if(strval.contains("regulate")|| strval.contains("stop") || strval.contains("confine") || strval.contains("control"))
		  breakLevel = 1;
	  return breakLevel;
  }
  public void parsingMsg(String fileDir) {
	  int i = 0;
	  fileT = new File(fileDir);
	  PrintWriter pr = null;
	try {
		pr = new PrintWriter(fileT,"UTF-8");
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  Pattern pattern = Pattern.compile(pat);
	  System.out.println("this size "+this.Msg.size());
	  int counter = 0;
	  pr.println("UserId"+"\t"+"Meassage");
	  
	  int nonEmptyUser = 0;
	 try{
	  for( ; i < Msg.size(); i++){
		  //System.out.println(Msg.get(i).twitMsg);
		  if(Msg.get(i).twitMsg == null)continue;
		  nonEmptyUser++;
		  StringTokenizer tokenizer = new StringTokenizer(Msg.get(i).twitMsg," ");
		// System.out.println(Msg.get(i).twitMsg);
		  int breakLevel = 0;
		  int setToBreak = 0;
		  int breakLevel2 = 0;
		  while(tokenizer.hasMoreElements()){
			  String strval = tokenizer.nextElement().toString();
			  //if(strval == null)
				//  continue;
			  Matcher match = pattern.matcher(strval.toLowerCase());
			  strval = strval.toLowerCase();
			  switch(getParsing()){
			  case 0:
			      breakLevel = getDecisionOverQuit(strval); 	
				  break;
			  case 2:
			  //if(strval.contains("#") || strval.contains("@")|| strval.contains("http://"))
				  break;
			  case 1:	  
					
				breakLevel2 = getDecisionOverQuit(strval);
				setToBreak = (1 == breakLevel2) ? 1 :0;
				  	
			  	if(strval.contains("e-cig")||strval.contains("ecig")||strval.contains("e-cigs")||strval.contains("ecigs")||strval.contains("cig")){
			  		System.out.println(strval);	  		
			  		breakLevel = 1;	
			  	}else if(strval.contains("vaper")||strval.contains("vape")||strval.contains("vapor")||strval.contains("liquid")||strval.contains("eliquid")||strval.contains("liquids")||strval.contains("vaping")){
			  		System.out.println(strval);			  		
			  		breakLevel = 1;
			  	}else if(strval.contains("cigarette")||strval.contains("cigs")||strval.contains("e-cigarette")||strval.contains("snus")){			  	
			  		breakLevel = 1;
			  	}else if(match.find()==true){			  	
			  		breakLevel = 1;
			  	}
			  
			  	break;
			  default:
				  break;
			  }
			 /* if(1 == setToBreak && getParsing() == 1 ){
				 breakLevel = 0;
				  break;
			  }*/
			 /* if(1 == breakLevel ){
				  vectorContainer.add(Msg.get(i));
				  Total++;
				  System.out.println("Break out");
				  break;
			  }*/
		  }	
		  if(0 == breakLevel && 0 == breakLevel2 ){
			  vectorContainer.add(Msg.get(i));
			  Total++;
			  System.out.println("Break out");
			  
		  }
		  
	  }
	  pr.close();
	  System.out.println("Total Strings contain Quit: "+ counter);
	  System.out.print("Number of NonEmpty userProfile: "+ nonEmptyUser);
	 }catch(NullPointerException nulex){
		 
	 }catch(RuntimeException rex){
		 System.out.println(rex.getMessage());
	 }
	 finally{
		 System.out.println("Iteration " + i);
	 }
  }
  public void calculateStatistics(){
	  for(int i = 0 ; i < vectorContainer.size(); i++){
		  if(vectorContainer.get(i).gettwitMsg().startsWith("#"))
			  counter[0]++;
		  else if(vectorContainer.get(i).gettwitMsg().startsWith("@"))
			  counter[1]++;
		  else if(vectorContainer.get(i).gettwitMsg().contains("http://"))
			  counter[2]++;
	  }
  }
  public long getHashtagCount(){
	  return counter[0];
  }
  
  public long getLinkCount(){
	  return counter[2];
  }
public long getFollowerCount() {
	// TODO Auto-generated method stub
	return counter[1];
}
public long getTotal(){
	return Total;
}
public void print(){
	try{
	pr = new PrintWriter(fileT);
	for(int i = 0 ; i < vectorContainer.size() ;i++){
		pr.println(/*vectorContainer.get(i).getdbId()+"\t"+*/ vectorContainer.get(i).userName+"\t"+vectorContainer.get(i).twitterId+"\t"+ vectorContainer.get(i).twitMsg );
	}
	pr.close();
	}catch(FileNotFoundException ex){
		System.err.println(ex.getMessage());
	}finally{
		
	}
}

}
