package scheduler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.TimeoutException;

import javax.naming.TimeLimitExceededException;
import javax.rmi.CORBA.Util;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import collectionTest.hashedObject;
import twitter4j.Twitter;
import twitter4j.examples.user.GetHomeTimeline;
import twitter4j.examples.user.SearchUsers;


public class myTimerTask extends TimerTask {
    private final long ONCE_PER_HOUR = 1000*60*16;

    //private final static int ONE_DAY = 1;
    private final static  int TWO_AM = 2;
    private final static  int ZERO_MINUTES = 0;
    public  String usernameFile = "C:\\TwitterBigData\\dataset\\non_empty_unique_profile.txt"; //profileOutput\\New folder\\uniquwoutputEmptyProfiles.txt";
    public  String configurationFile; //"TwitterData/configurationFile.txt";
    public  File files;
    public  FileInputStream istream;
    public  FileOutputStream ostream;
    public  DataInputStream dataIn;
    public  final int searchSize = 98;
    public  DataOutputStream dataOut;
    public  BufferedReader configurationFileReader;
    public  PrintWriter configurationFileWriter;
    public  File fileConfiguration;
    public  PrintWriter tmpFileWriter;
    private  int Count;
    private  long lastWrite;
    private long lastReadTime;
    private int lastReadCount;
    private int lastReadInception;
    private static HashSet<String> hashSet = new HashSet<String>();
    private SearchUsers searchUser;
    private static int threadCount = 0;
    private String auth[] = new String[4];
    private int inceptionPoint;
    private int range;
    private String outputPath;
	private BufferedReader filingBr;
	private Twitter twitter;
    public myTimerTask(int startIndex,int fileInd){
    	
    	Count = startIndex;
    	lastWrite = 0;
    	//usernameFile =/* "uniqueUserRecord.txt";*/"D:\\TwitterBigData\\uniqueUserRecord.txt";
    	configurationFile = /*"TwitterData/configurationFile" + fileInd +".txt";*/"C:\\TwitterBigData\\configurationFiles\\configuration_for_28384_datasets" + fileInd +".txt";
    	fileConfiguration = new File(configurationFile);
    	
    	try {
    		
			//istream = new FileInputStream(configurationFile);
			//ostream = new FileOutputStream(configurationFile);
			//dataIn = new DataInputStream(istream);
			dataOut = new DataOutputStream(ostream);
			
			//configurationFileReader = new BufferedReader(new InputStreamReader(dataIn));
			//configurationFileWriter = new BufferedWriter(new OutputStreamWriter(dataOut));
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    public void setTimerTask(String outputPath){
    	searchUser = new SearchUsers(auth[0], auth[1], auth[2], auth[3]);
    	searchUser.setPath(outputPath);
    	this.twitter = searchUser.getTwitterObject();
    }
    @Override
    public void run() {
    	try {
			//httpUrl.startOperations("Nothing");
			try{
				//configurationFileReader.mark(200);
				//if(configurationFileReader.readLine())
				
				configurationFileReader = new BufferedReader(new FileReader(fileConfiguration));
				int trys = 0;
				while(!configurationFileReader.ready()) {
					//Thread.currentThread();
					if(trys++ == 10){
						configurationFileWriter =new PrintWriter(new FileWriter(configurationFile));
						configurationFileWriter.println(lastReadTime);
						configurationFileWriter.println(lastReadCount);
						configurationFileWriter.print(lastReadInception);
						configurationFileWriter.close();
						configurationFileReader.close();
						this.cancel();
						return;
					}
					System.out.print(Thread.currentThread().getName() + ": " +configurationFile + " is ");
					System.out.println("Not ready");
					Thread.sleep(500);
				}
				String  timeInfo = configurationFileReader.readLine();
				String  countInfo = configurationFileReader.readLine();
				String  inceptionInfo = configurationFileReader.readLine();
				//String  lastWrite = configurationFileReader.readLine();
				System.out.println(Thread.currentThread().getName()+": "+timeInfo + " "+ countInfo+" "+inceptionInfo);
				configurationFileReader.close();
				configurationFileWriter = new PrintWriter(new FileWriter(configurationFile));
				if(timeInfo != null){
					System.out.println(Thread.currentThread().getName() +": Checking the Time Info");
					Count = (new Integer(countInfo)).intValue();
					lastWrite = (new Long(timeInfo)).longValue();
					inceptionPoint = (new Integer(inceptionInfo)).intValue();
					if(/*Math.abs((*/Count </*- */inceptionPoint){//)) < range){
						try{
							if(Math.abs((System.currentTimeMillis() - lastWrite)) >  3*1000*60*16 )
								System.out.println(Thread.currentThread().getName() + ": After Long Sleep Wake up");
							else if(Math.abs((System.currentTimeMillis() - lastWrite))< 1000*60*15 ){
									System.out.println(Thread.currentThread().getName()+": It is not time to Wakeup");
									if(lastWrite != 0)
										configurationFileWriter.println(lastWrite);
									else
										configurationFileWriter.println(lastReadTime);										
									configurationFileWriter.println(Count);
									configurationFileWriter.print(inceptionPoint);
									configurationFileWriter.close();
									return;
								}
							System.out.println("Initializing Count");
					   
							}catch(NullPointerException nulEx){
								System.err.print(nulEx.getMessage());
								fileConfiguration.delete();
							}
					}else
					{
						System.out.println("we are done collecting information");
						System.out.println("Cancelling the thread no need anymore");
						this.cancel();
						return;
					}
				}else {
					System.out.print(Thread.currentThread().getName() +": Failed to Read out the Configuration file\n");
					return;
				}
				
				File fileOb = new File(usernameFile);
				Scanner sc = new Scanner(fileOb);
				
				BufferedReader bfr = new BufferedReader(new FileReader(fileOb));
				String tmpStr;
				Vector<String> strName = new Vector<String>(300);
				int i = 0;
				if(Count == 0)
					tmpStr = bfr.readLine();
				if(Count >= inceptionPoint){
					System.out.println(Thread.currentThread().getName()+": Count > InceptionPoint");
					configurationFileReader.close();
					configurationFileWriter.close();
					this.cancel();
					bfr.close();
					return;
				}
				while(i++ < Count)
					bfr.readLine();
				//while(tmpStr  !=null){
				i = 0;
				System.out.println("Start Operation");
				while((tmpStr = bfr.readLine()) != null && i < searchSize && (Count+i)<inceptionPoint){					
				   if(tmpStr.indexOf('\t') == -1)
					  continue;
				  synchronized(this){
					if(hashSet.contains(tmpStr.substring(0,tmpStr.indexOf('\t'))) == true)
						continue;	
					String[] for_check_string = tmpStr.split("\t");// temporarily added for empty profile tweets collections
					//if(for_check_string.length == 3)//this is for empyt profile sets
						//continue;
					String tmpStr1 =  tmpStr.split("\t")[0].trim(); // previously it was zero
					strName.add(tmpStr1);					
					hashSet.add(new String(tmpStr1));
				  }
					i++;
				}
				if(tmpStr == null && strName.size() == 0){
					synchronized(this){
						threadCount++;
						if(threadCount >= 10)
							this.cancel();
				    }
					configurationFileWriter.println(lastWrite);
					configurationFileWriter.println(Count);
					configurationFileWriter.print(inceptionPoint);
					configurationFileWriter.close();
					configurationFileReader.close();
					System.out.print("Timer going to cancelled\n");
					Thread.sleep(5000);
				}
				 // System.out.println("Size of the vector: "+ strName.size());
				Count += i;
				String[] tempStrList = new String[strName.size() + 1];
				for(int il = 0; il < strName.size();il++){
					tempStrList[il] = strName.get(il);
					//System.out.println(tempStrList[il]);
				}
				
				//searchUser.mainSearch(strName);
				try{
					
					if(strName.size() != 0)
						GetHomeTimeline.startOperating(this.twitter, tempStrList, this.outputPath);
				}catch(Exception exp){
					System.err.println(exp.getMessage());
				}finally{
				System.out.println("Lenght of String "+ strName.size());
				System.out.println("End of Phase and Go into Sleep");
				//}
					lastReadTime = System.currentTimeMillis();
					lastReadCount = Count;
					lastReadInception = inceptionPoint;
					configurationFileWriter.println(new Long(System.currentTimeMillis()).toString());
					configurationFileWriter.println(Count);
					configurationFileWriter.print(inceptionPoint);
					configurationFileWriter.close();
				//configurationFileReader.reset();
					bfr.close();
				}
				
				/*for(int i1 = 0 ; i1 < 20;i1++){
					System.out.print(strName[i1]+"\n");
				}*/
				//SearchUsers.mainSearch(strName);
			}catch(IOException ex){
				ex.printStackTrace();
			}catch(ArrayIndexOutOfBoundsException arr){
				arr.printStackTrace();
			}
			
			//LookupUsers.mainLookups(strName);
			
		} catch (Exception e2) {
			// TOD/O Auto-generated catch block
			e2.printStackTrace();
			fileConfiguration.delete();
		}
    	
          System.out.print("End of first job waiting for next Session\n");
    }
    private static Date getTomorrowMorning2AM(){

        Date date2am = new java.util.Date(); 
           date2am.setHours(TWO_AM); 
           date2am.setMinutes(ZERO_MINUTES);  
           return date2am;
      }
    //call this method from your servlet init method
    public void startTask(String authenticationFile,String outputPath, int startIndex) throws IOException{
    	
    	
    	File filing = new File(authenticationFile);
    	FileReader  filingR = new FileReader(filing);
    	filingBr = new BufferedReader(filingR);
    	String readStr;
    	this.outputPath=outputPath;
    	inceptionPoint = Count + startIndex;
    	range = startIndex;// the calculation is wrong 
    	int i = 0;
    	while((readStr = filingBr.readLine()) != null){
    		auth[i++] = readStr.split("\t")[1].trim();
    	}
    	setTimerTask(outputPath);
    	searchUser.printAuthenticationData();
    	if(!fileConfiguration.exists()){
    		fileConfiguration.createNewFile();
			fileConfiguration.setReadable(true);
			fileConfiguration.setWritable(true);
    	
    		
    	try {
    			System.out.println("I am creating the file");
    			
				configurationFileWriter = new PrintWriter(new FileWriter(fileConfiguration));
				
    			//tmpFileWriter = new PrintWriter(fileConfiguration);	
				configurationFileWriter.println(new Long(System.currentTimeMillis()).toString());
				configurationFileWriter.println(new Integer(Count).toString());
				configurationFileWriter.print(new Integer(inceptionPoint).toString());
				//configurationFileWriter.println(new Integer(0).toString());
				configurationFileWriter.flush();
				configurationFileWriter.close();
				Thread.sleep(1000);
    			//configurationFileReader.mark(200);
    			//configurationFileReader.reset();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				
				configurationFileWriter.close();
				System.out.println("End of writing the file");
			}
    	}
    	
    	try{
    	
    		Timer timer = new Timer();
    		
    	    timer.schedule(this,1000,ONCE_PER_HOUR);// for your case u need to give 1000*60*60*24
    	}catch(Exception timeEx){
    		
    	}finally{
    		//tmpFileWriter.close();
    	}
    	System.out.println("End of timer Task");
    }
}