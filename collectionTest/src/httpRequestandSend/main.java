package httpRequestandSend;
import java.io.BufferedInputStream;

import scheduler.myTimerTask;
import twitter4j.TwitterException;
import twitter4j.examples.user.GetHomeTimeline;
import twitter4j.examples.user.LookupUsers;
import twitter4j.examples.user.SearchUsers;
import xmlReadWrite.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import collectionTest.expandedUrls;
import collectionTest.findUniqueUsers;
import collectionTest.globalObjectsContext;
import collectionTest.hashedMapobj;
import collectionTest.hashedObject;
import collectionTest.messageParser;
import collectionTest.multiHashedMapObj;
import collectionTest.objectDes;
import collectionTest.sortOutData;
import collectionTest.threadPool;
import collectionTest.topicGeneration;
import collectionTest.twitMessageParsing;
import jxl.read.biff.BiffException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class main {

	public static excelReader reader;
	public static String nameFile = "D:\\TwitterData\\userProfileInfo.xls";
	public static String outnameFile = "D:\\TwitterData\\output.xls";
	public static hashedObject hash = new hashedObject(32000);
	public static Pattern patNum = Pattern.compile("[0-9]+");
	public static String[] files = {"D:\\TwitterData\\outputExpandedUrls4.txt","D:\\TwitterData\\outputUrls1.txt","D:\\TwitterData\\outputuserMentions1.txt","D:\\TwitterData\\outputhashTags1.txt"};
	public static String[] outputFiles = {"D:\\TwitterData\\outputExpandedUrls4Ranks.txt","D:\\TwitterData\\outputUrlsRank.txt","D:\\TwitterData\\outputuserMentionsRank.txt","D:\\TwitterData\\outputhashTagsRank.txt"};
	public static int[] indexArr = {1,4,3,4}; // how many columns
	public static String[] readDataFromFile(String filePath) throws IOException{
		File fobj = new File("D:\\TwitterData\\userProfile.txt");
        FileReader fw = new FileReader(fobj);
        BufferedReader br = new BufferedReader(fw);
		//StringBuilder[] stbuilder;
		String[] str = new String[500];
		br.close();
		return str;
	}
	public static void writeToExcels(){
		excelWriter writer = new excelWriter();
		try {
			//writer.mainStart(outnameFile, "D:\\TwitterData\\output.txt");
			//writer.mainStart("D:\\TwitterData\\hashtags.xls", "D:\\TwitterData\\outputhashTags1.txt");
			//writer.mainStart("D:\\TwitterData\\usermentions.xls", "D:\\TwitterData\\outputuserMentions1.txt");
			//writer.mainStart("D:\\TwitterData\\urls.xls", "D:\\TwitterData\\outputUrls1.txt");
			writer.mainStart("C:\\TwitterBigData\\profileOutput\\uniqueoutputProcessedProfile.xls", "C:\\TwitterBigData\\profileOutput\\uniqueoutputProcessedProfile.txt");

			//writer.mainStart("D:\\TwitterData\\RetweetGroup.xls","D:\\TwitterData\\retweetGroup.txt");
		} catch (FileNotFoundException e3) {
		// TODO Auto-generated catch block
			e3.printStackTrace();
		}
	}
	public static void readAndSort(){
		/*String filing[] = {"D:\\TwitterBigData\\urls\\expandedURLS\\ultimateExpanded\\urlsuExpandedOutput0.txt",//
			"D:\\TwitterBigData\\urls\\expandedURLS\\ultimateExpanded\\urlsuExpandedOutput1.txt",//
			"D:\\TwitterBigData\\urls\\expandedURLS\\ultimateExpanded\\urlsuExpandedOutput2.txt",//
			"D:\\TwitterBigData\\urls\\expandedURLS\\ultimateExpanded\\urlsuExpandedOutput3.txt",//
			"D:\\TwitterBigData\\urls\\expandedURLS\\ultimateExpanded\\urlsuExpandedOutput4.txt"};
		String outputFiling = "D:\\TwitterBigData\\urls\\expandedURLS\\ultimateExpanded\\urlsuExpandedOutputRank.txt";*/
		String filing[] = {"C:\\TwitterBigData\\profileInfoContainspromo\\userProfileRTCountingForKey.txt"};
		String outputFiling = "C:\\TwitterBigData\\profileInfoContainspromo\\userProfileRTCountingRankForKeys.txt";
		hashedMapobj hashSet = new hashedMapobj(300000);
		int setBypass = 1;
		for(int i = 0 ; i < filing.length; i++){
			
			BufferedReader bfr = null;
			File fobj = new File(filing[i]);//files[i]);
			FileReader fw;
			try {
				fw = new FileReader(fobj);
				bfr = new BufferedReader(fw);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		//StringBuilder[] stbuilder;
			String tmpStr;
		    int kl = 0;
		    int index = 0;
		    
			try {
				bfr.readLine();
				while((tmpStr = bfr.readLine())!= null){
				    
					String[] strArr = tmpStr.split("\t");
					System.out.println("Stage #: "+ kl++);
					if( strArr.length < 3 )
						continue;
				 if(setBypass == 0){
					try{
						if(hashSet.isContain(strArr[index/*indexArr[i]*/]) == true){
							int val = hashSet.get(strArr[index/*indexArr[i]*/]);
							val++;
							hashSet.setData(strArr[index/*indexArr[i]*/], new Long(val));
						}else{
							hashSet.setData(strArr[index/*indexArr[i]*/], new Long(1));
						}
					}catch(NullPointerException ex){
						System.out.print(ex.getMessage());
					}
				 }else{
					 if(hashSet.isContain(strArr[0]) == false)
						 
						 hashSet.setData(strArr[0], new Long(strArr[2]));
				 }
			   }
				bfr.close();
			//	sortOutData.getDataToSort(hashSet,outputFiles[i]);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				
			}
				
		}
		try {
			sortOutData.getDataToSort(hashSet,outputFiling);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
	public static void readExpandedUrls() throws IOException{
		
		File fob = new File("D:\\TwitterBigData\\urlsRecordwithUser.txt");
		File fouput = new File("D:\\TwitterBigData\\urls\\urlsExpandedRecordwithUser.txt");
		PrintWriter pr = new PrintWriter(fouput);
		if(!fob.exists()){
			System.err.print("i am exiting nor source of data\n");
			System.exit(0);
		}
		FileReader freader = new FileReader(fob);
		BufferedReader fbreader = new BufferedReader(freader);
		Vector<Vector<String>> vec = new Vector<Vector<String>>();
		String tempStr;
		int i = 0; 
	/*	while(i<5000){
			fbreader.readLine();
			i++;
		}*/
		i = 0;
		try{
		while((tempStr = fbreader.readLine()) != null){
			try{
			String[] tempStrArr = tempStr.split("\t");
			Vector<String> element = new Vector<String>();
			
			element.add(tempStrArr[0]);
			element.add(tempStrArr[1]);
			int size = tempStrArr.length;
			
			if(size >= 5){
				element.add(tempStrArr[4]);
				String urlString =null;// expandedUrls.getURLS(tempStrArr[4]);
				System.out.println("stage: "+ i +"url:" + urlString);
				element.add(urlString);
			}
			vec.add(i++, element);
			//break;//test
			}catch(NullPointerException ex){
				System.out.println("level inner :"+ex.getMessage());
			}finally{
				
			}
			//hashMap.setData(tempStrArr[3],  expandedUrls.getExpandedUrls(tempStrArr[3]));
		}
		}catch(IOException ioe){
			System.err.println(ioe.getMessage());
		}finally{
			for(int i1 =0 ; i1 < vec.size(); i1++){
				for(int j = 0; j < vec.elementAt(i1).size(); j++)
					pr.print(vec.elementAt(i1).elementAt(j) + "\t");
				pr.print("\n");
			}
			fbreader.close();
			pr.close();
		}
		
		
	}
	public class RejectedExecutionHandlerImpl implements RejectedExecutionHandler {
		 
			    @Override
			    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			        System.out.println(r.toString() + " is rejected");
			    }

				
			 
			}
	public static void combineDataFiles(String datafilesName,int start,int end) throws IOException{
		File tmpfb = new File(datafilesName+".txt");
		PrintWriter pr = new PrintWriter(tmpfb);
		for(int i = start; i <=end;i++){
			File tmpFile = new File(datafilesName+i+".txt");
			FileReader tmpFr = new FileReader(tmpFile);
			BufferedReader tmpBr = new BufferedReader(tmpFr);
			String str;
			while((str = tmpBr.readLine()) != null){
				pr.println(str);
			}
			tmpFr.close();
			tmpBr.close();
		}
	}
	public static void main(String arg[]) throws IOException, InterruptedException {
		//HashSet hs = new HashSet();
		
		reader = new excelReader();
		
		HttpURLConnectionExample httpUrl = new HttpURLConnectionExample();
		Scanner sc = new Scanner(System.in);
		Integer choice = new Integer(sc.nextLine());
		switch(choice.intValue()){
		case 0: writeToExcels();
			break;
		case 1:
			//writeToExcels();//option1
			expandedUrls expand = new expandedUrls();
			//expand.getBitlyUrls("http://t.co/PHwgOCYzlW", "akmsabbir", "", true);
			Pattern pat1 = Pattern.compile("[http|https]://[a-zA-Z]+.[a-zA-Z]{2}/[a-zA-Z0-9]+"); 
			Matcher matching = pat1.matcher("http://instagram.com/p/d7rzx8nCqS/");
			if(matching.find() == true)
				System.out.println(expandedUrls.getExpandedUrls("http://thndr.it/1hDffcw"));
			else
				System.out.print("Did not match");
			for(int i = 0; i < 5; i++)
				expand.openFileToReadWrite("D:\\TwitterBigData\\urls\\expandedURLS\\urlsExpandedOutput"+ i +".txt", "D:\\TwitterBigData\\urls\\expandedURLS\\ultimateExpanded\\urlsuExpandedOutput"+ i +".txt");
			break;
		case 2:
			//readExpandedUrls();//option2
			//System.out.print(expandedUrls.getURLS("http://t.co/zuejhaj1q4"));
			//webService.startServiceRequest("https://t.co/tUG7yfRCKv");
			ExecutorService execute = Executors.newFixedThreadPool(10);
			int looperLen = 0;
			for(int threadN = 0; threadN < 1; threadN++){
				Runnable workingThread = new threadPool("C:\\Binary_classifier_for_health\\des_url_meta_data0.txt", "C:\\Binary_classifier_for_health\\expandedincompleteUrls" + threadN+ ".txt", looperLen,4,3,274);
				looperLen += 274;
				execute.execute(workingThread);
				Thread.sleep(1000);
			}
			
			try {
				execute.awaitTermination(2000,TimeUnit.SECONDS);
				execute.shutdown();
			} catch (InterruptedException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			while(!execute.isTerminated()){
				System.out.println("System have not terminated so wait");
			}
			System.out.println("All thread is Finished");
			break;
		
		case 4:
			//combineDataFiles("C:\\TwitterBigData\\profileOutput\\researchOut",1,17);
			globalObjectsContext gbc = globalObjectsContext.creatorOfObjects(new multiHashedMapObj(), "C:\\TwitterBigData\\tweetMessage\\tweetContentQuitkeyWords3Update.txt", "C:\\TwitterBigData\\tweetMessage\\tweetContentQuitkeyWords4Update.txt");
		   //gbc.setFileInput();
			gbc.setFileToReadWrite();
			gbc.startFillHashMap();
			//gbc.startFillUniqueHashMap();
			gbc.writeToFile();
			//gbc.inceptionOfContextOperation();
			//readAndSort();
			break;
		case 3:
			int increment = 0;
			for(int ik = 0 ; ik < 10; ik++){
				(new myTimerTask(increment,ik)).startTask("C:\\TwitterBigData\\profileInput\\research"+ ik +".txt","C:\\TwitterBigData\\dataset\\1000_data_sets\\28384_profiles_latest_tweets_usernames_100"+ ik +".txt",2834);//3700 previously
				increment += 2834;//3700;//27476;
				Thread.sleep(1500);
			}
			System.out.println("End of Main Loop");
			break;
		case 5:
			messageParser msgParser = new messageParser("D:\\TwitterData\\userTweetContent.txt");//option3
			try {
				reader.readXcelBook("D:\\TwitterData\\userTweetContent.xls",hash);
				Enumeration<objectDes> enumObj = hash.getEnum();
				ArrayList<objectDes> tmpArray = new ArrayList<objectDes>();
				System.out.println("Size of Hash:" + hash.getHashsize());
				PrintWriter prTemp = new PrintWriter(new File("D:\\TwitterData\\outputProcessedUniqueUsers.txt"));
				int  cou = 0;
				while(enumObj.hasMoreElements()){
					objectDes tmpObj = enumObj.nextElement();
					tmpArray.add(tmpObj);
					cou++;
					prTemp.println(tmpObj.getdbId()+"\t"+ tmpObj.gettwitterId()+ "\t" + tmpObj.gettwitMsg());
					//	System.out.println(tmpObj.getdbId() +" "+tmpObj.gettwitMsg() + " "+ tmpObj.gettwitterId()+ " "+tmpObj.getDate());
				
				}
		//	System.out.println(cou);
				prTemp.close();
				msgParser.setArraylist(tmpArray);
				msgParser.parsingMsg("");
				//msgParser.calculateStatistics();
				//msgParser.print();
				System.out.print("Total "+ msgParser.Total );
				System.out.print(" FollowerCount: " + msgParser.getFollowerCount());
				System.out.print(" LinkCount: " + msgParser.getLinkCount());
			} catch (BiffException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case 8 :
			   localMultiMessageParser();
				break;
		case 6:
			/*information filtering using keywords, quit, stop, regulation, control, confined
			  information parsing, formatting, filtering */
			excelReader excelRead = new excelReader();
			try {
				//excelRead.readXcelBook("D:\\TwitterData", hash);
				
				File fReader = new File("C:\\TwitterBigData\\tweetContent\\userTweetContent2.txt");
				FileReader fR =new FileReader(fReader);
				BufferedReader br=new BufferedReader(fR);
				String str;
				br.readLine();
				int k = 0;
				int lines = 0;
				String currStr = null;
				while((str = br.readLine()) != null ){
					//System.out.print("Current line +" + lines);
					StringTokenizer tk = new StringTokenizer(str,"\t");
					if(tk.countTokens() < 4)
						continue;
					String[] tempStr = new String[4];
					int j = 0;
					while(tk.hasMoreElements())
						tempStr[j++] = tk.nextElement().toString();
					objectDes temp = new objectDes();
					/*if(tempStr[1] == null){
					//	System.out.println("I am Null");
						currStr += tempStr[0];
						continue;
					}*/
					/*if(currStr != null){
						temp.settwitMsg(tempStr[3]+currStr);
						currStr = null;
					}
					else*/
					
					temp.settwitMsg(tempStr[3]);
					temp.setdbId(new Long(tempStr[0]));
					temp.settwitterId(new Long(tempStr[2]));
					temp.setUserName(tempStr[1]);
					System.out.println(tempStr[1]+" "+ k++);
					/*if(tempStr[2] !=null)
						temp.settwitMsg(tempStr[2]);
					else
						temp.settwitMsg("None");*/
						
					//temp.settwitterId(new Long(tempStr[0]));
					//objectDes temp = new objectDes();
					//temp.settwitMsg(str);
					hash.setHashdata(new Long(lines++), temp);
				}
				System.out.println("No of Non empty Profile is: "+ lines);
				br.close();
				fR.close();
				Enumeration<objectDes> obd = hash.getEnum();
				ArrayList<objectDes> arrayLists = new ArrayList<objectDes>();
				while(obd.hasMoreElements())
					arrayLists.add(obd.nextElement());
				messageParser msgParserOp6 = new messageParser("C:\\TwitterBigData\tweetMessage\\tweetContentNeutralData.txt");//option3
				msgParserOp6.setParsing(1);
				msgParserOp6.setArraylist(arrayLists);				
				msgParserOp6.parsingMsg("C:\\TwitterBigData\\tweetMessage\\tweetContentNeutralData.txt");
				System.out.println("Total users contain keywords: "+ msgParserOp6.getTotal());
				msgParserOp6.print();
				/*File fob = new File("D:\\twitterData\\profileContainQuit.txt");
				PrintWriter pr = new PrintWriter(fob,"UTF-8");
				
				for(int i = 0;obd.hasMoreElements();i++){
				    objectDes ob = obd.nextElement();
				    if(ob.gettwitMsg().toLowerCase().contains("quit") ||ob.gettwitMsg().toLowerCase().contains("Quit")||ob.gettwitMsg().toLowerCase().contains("quitting"))
				    	pr.println(ob.getdbId()+ "\t"+ob.gettwitterId()+"\t"+ob.gettwitMsg());
				}
				pr.close();*/
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 7:
			//tweet message formatting using keywirds quit and stop
			String fileN = "twitMsg";
			int i = 0;
			ArrayList<objectDes> tempList = new ArrayList<objectDes>();
			while(true){
				File fi = new File("D:\\TwitterData\\malletQuit\\"+ fileN + i++ +".txt");
				if(!fi.exists())
					break;
				//messageParser msgParser1 = new messageParser("D:\\TwitterData\\mallet\\"+ fileN + i++);//option3
				FileReader fReader = new FileReader(fi);
				BufferedReader bf = new BufferedReader(fReader);
				String Str;
				String totalStr = "";
				while((Str = bf.readLine()) != null ){
					totalStr += Str;
				}
				objectDes obj = new objectDes();
				obj.settwitMsg(totalStr);
				tempList.add(obj);
				fReader.close();
				bf.close();
					
			}
			System.out.println(tempList.size());
			messageParsingMain(tempList);
			break;
		case 9:
			topicGeneration.setNumberofTopic(15);
			topicGeneration.initialize("D:\\TwitterData\\mallet-2.0.7\\topic-state15.txt");
			topicGeneration.topicClassification();
			topicGeneration.iterationData();
			topicGeneration.closeFiles();
			break;
		case 10:
			excelReader excelRead10 = new excelReader();
			try {
				int count = 0;
				excelRead10.readXcelBook("D:\\TwitterData\\userProfileInfo.xls", hash);
				Enumeration<objectDes> enumVar = hash.getEnum();
				while(enumVar.hasMoreElements()){
					objectDes tempOb = enumVar.nextElement();
					if(tempOb.gettwitMsg() == null)
						count++;
						
				}
				System.out.println("Total # of undefined Profile is" + count);
			} catch (BiffException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(NullPointerException ex){
				System.err.println(ex.getMessage());
			}
			break;
		case 11:
			//tweet Profile information formatting using keywords and quit in profile description 
			//tweet content and profile information formatting for mallet operation
			int i1 = 0;
			String fileNam = "twitMsg";
			ArrayList<objectDes> tempoList = new ArrayList<objectDes>();
			
				File fi = new File("C:\\Binary_classifier_for_health\\DataSet_2_supriya.txt");
				
				//messageParser msgParser1 = new messageParser("D:\\TwitterData\\mallet\\"+ fileN + i++);//option3
				FileReader fReader = new FileReader(fi);
				BufferedReader bf = new BufferedReader(fReader);
				String Str;
				String totalStr = "";
				while((Str = bf.readLine()) != null ){
					StringTokenizer str = new StringTokenizer(Str,"\t");
					int counter = 0;
					while(str.hasMoreTokens()){
						totalStr = str.nextToken();
						counter++;
						if(counter == 4)
							break;
					}
					if(counter == 4){
						objectDes obj = new objectDes();
						obj.settwitMsg(totalStr);
						tempoList.add(obj);
					}
				}
				
				fReader.close();
				bf.close();
					
			
			int index =0;
			Iterator<objectDes> itob  = tempoList.iterator();
			while(itob.hasNext()){
				objectDes tempOb =	itob.next();
				File fob = new File("C:\\Binary_classifier_for_health\\mallet_for_binary_classifier\\"+ fileNam + i1++ +".txt");
				if(!fob.exists())
					fob.createNewFile();
				PrintWriter pr = new PrintWriter(fob);
				pr.println(tempOb.twitMsg);
				pr.close();
				
			}
			break;
		case 12:
			findUniqueUsers findU = findUniqueUsers.createFindUniqueUsers(10000);
			findU.setFileName("C:\\TwitterBigData\\userRTCountRank\\userRTSRank.txt");
			/*findU.initFileReader();
			findU.startReadingFillHashOb(0);
			findU.closeFileReader();*/
			findU.initFileReader();
			findU.startReadingFillHashMap();
			findU.closeFileReader();
			String tmpHolder = "C:\\TwitterBigData\\profileOutput\\profileContentKeyWords.txt";
		    findU.doFileOperations(tmpHolder,"C:\\TwitterBigData\\profileInfoContainspromo\\userProfileRTCountingForKey.txt");
			System.out.println("Map Size = "+ findU.getSizeHashMap()+ " Object size =" + findU.getSizeHashOb());
			//findU.printHashObject("D:\\TwitterData\\quit\\uniqueUserContainingQuitTweet.txt");
			//sortOutData.getDataToSort(findU.getContainerReferenceMap(),"D:\\TwitterData\\quit\\uniqueQuit\\quitRank.txt");
			//findU.processedUserProfile("D:\\TwitterData\\userProfileProcessed.txt",sortOutData.keyList);
			
			
			break;
		case 16:
			//find out the users who tweeted with promotional keywords and quiting
			//shuffleAttribute("C:\\TwitterBigData\\profileOutput\\profileContentKeyWords.txt");
			globalObjectsContext gbOb = globalObjectsContext.creatorOfObjects(new multiHashedMapObj(), "C:\\TwitterBigData\\userRTCountRank\\userRTSRank.txt", "C:\\TwitterBigData\\profileInfoContainspromo\\any.txt");
			gbOb.setFileToReadWrite();
			gbOb.startFillHashMap();
			//shuffleAttribute("C:\\TwitterBigData\\tweetContent\\userTweetContent.txt");
			globalObjectsContext gbOb1 = globalObjectsContext.creatorOfObjects(new multiHashedMapObj(), "C:\\TwitterBigData\\profileOutput\\uniqueoutputProcessedProfile.txt", "C:\\TwitterBigData\\profileInfoContainspromoanyProfile.txt") ;
			gbOb1.setFileToReadWrite();
			gbOb1.startFillUniqueHashMap();
			gbOb1.dataComparision(gbOb.getContainer(),0);
			break;
		case 18:
			File filesReader = new File("C:\\TwitterBigData\\userRetweetingGroup\\retweetGroup.txt");
			FileReader filesR = new FileReader(filesReader);
			BufferedReader buffer = new BufferedReader(filesR);
			String tmpStringHolder = null;
			ArrayList<String> arrlist = new ArrayList<String>();
			while((tmpStringHolder = buffer.readLine()) != null){
				String stringArr[] = tmpStringHolder.split("\t");
				arrlist.add(stringArr[0]);
			}
			filesR.close();
			buffer.close();
			globalObjectsContext gbOb18 = globalObjectsContext.creatorOfObjects(new multiHashedMapObj(), "C:\\TwitterBigData\\profileOutput\\uniqueoutputProcessedProfile.txt", "C:\\TwitterBigData\\profileInfoContainspromoanyProfile.txt") ;
			gbOb18.setFileToReadWrite();
			gbOb18.startFillUniqueHashMap();
			gbOb18.doArrayTraverseOPeration(arrlist);
			break;
		case 17:
			readAndSort();
			break;
		
		case 13:
			findUniqueUsers findU13 = findUniqueUsers.createFindUniqueUsers(50000);
			findU13.setFileName("D:\\TwitterData\\userTweetContentTrimmed.txt");
			findU13.initFileReader();
			findU13.startReadingFillHashOb(1);
			findU13.closeFileReader();
			System.out.println("the size of the hasgOb: "+findU13.getSizeHashOb());
			findU13.initDifferentFileWriter("D:\\TwitterData\\quit\\uniqueUserTweetProfile.txt");
		case 14:
			// this option is written to parse and clear the messed up twitter message.parsing tweet messages.
			twitMessageParsing twitmessageParse = new twitMessageParsing();
			twitmessageParse.readDatafile();
			twitmessageParse.findTwitShortWords();
			twitmessageParse.writeProcessedData();
			//following operations for generating Hdp format
			/*System.out.println("Start of first Phase");
			twitmessageParse.preProcessingData(1);
			System.out.println("End of First Phase");
			twitmessageParse.removeElementsFromHashOb();
            System.out.println("Start of Second Phase");
		    twitmessageParse.preProcessingData(2);
		    System.out.println("End of Second Phase");*/
			break;
		case 15:
			String regularString = "[a-zA-Z0-9+*&$%?!.\"]*";
			Scanner scan = new Scanner(System.in);
			Pattern patMe = Pattern.compile("\\w+(-*|\\**)\\w+");
			
			try{
				while(scan.hasNext()){
				   String str = scan.next();
				   Matcher matchMe = patMe.matcher(str);
				   while(matchMe.find()){
					   String t =matchMe.group();
						   System.out.println("inside for [\\w+-*[\\*]*\\w+]: "+ t);
					   str = str.replace(matchMe.group(),"");
					   System.out.println(str);
					   matchMe = patMe.matcher(str);
					   
				   }
				   System.out.println("get the next token: "+ str);
				   if(str.matches(regularString)){
					   str = str.replaceAll(regularString, "No");
					   
					   System.out.println(str);
				   }
				   
				}
			}catch(IllegalArgumentException ex){
				System.err.print(ex.getMessage());
			}catch(Exception e){
				System.err.print(e.getMessage());
			}
			
			break;
			
		case 19:
			/// it is for getting complete urls from icomplete urls
	    	String readStr;
	    	String filePath = "C:\\TwitterBigData\\profileInput\\research";
	    	String[] datasetPath = {"C:\\TwitterBigData\\dataset\\expanded_urls_id_only.txt"};//{"C:\\TwitterBigData\\profileOutput\\topLowRanked120Profile.txt","C:\\TwitterBigData\\profileOutput\\topRanked60Profile.txt","C:\\TwitterBigData\\profileOutput\\randomTestProfileData.txt"};
	    	String[][] auth = new String[10][4];
	    	int local = 0;
	    	int global = 0;
	    	while(true){
	    		if(global >=10)
	    			break;
	    		FileReader  filingR = new FileReader(filePath + global + ".txt");
	    		BufferedReader filingBr = new BufferedReader(filingR);
	    		local = 0;
	    		while((readStr = filingBr.readLine()) != null){
	    			auth[global][local] = readStr.split("\t")[1];
	    			//System.out.println("auth: "+ auth[global][local]);
	    			local++;
	    			
	    		}
	    		global++;
	    		filingBr.close();
	    	}
			SearchUsers su = new SearchUsers(auth,global,datasetPath);
			su.set_run_lenght(99);// previously it was 98
			su.readDataSet();
			su.get_user_id("C:\\TwitterBigData\\profileOutput\\New folder\\uniqueoutputProcessedProfile.txt");
			su.parseDataset();
			su.start_task();
			break;
		case 20:
			//this is for testing whether username and screename are same for specific users
			 String screeName = "vapetheworld408";
			 String userName = "JaimePoeshez367";
			 String get_auth_data = "C:\\TwitterBigData\\profileInput\\research0.txt";
			 FileReader filereaders = new FileReader(get_auth_data);
			 BufferedReader bufferedreader = new BufferedReader(filereaders);
			 String readString = null;
			 while((readString = bufferedreader.readLine())!= null){
				String[] tempString = readString.split("\t");
				
			
			 }
			 
			 
		default:
			twitMessageParsing twitMsg = new twitMessageParsing();
			twitMsg.setData("hello , dear .");
			twitMsg.setPatternIndex(3);
			twitMsg.printData(0);			
			twitMsg.setData("hello dear" +"\t" +"how"+"\t"+"are you");
			twitMsg.setPatternIndex(0);
			String[] s = twitMsg.splitData();
			for(int i2 = 0 ; i2 < s.length; i2++)
				System.out.println(s[i2]);
			System.out.println("I dont know What to do");
	}
		System.out.println("End of the main");
	/*	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder[] stbuilder;
		String str;
		StringBuilder[] sb = new StringBuilder[100];
		for(int i = 0 ; i < 10; i++){
			try {
				str = br.readLine();
				sb[i] = new StringBuilder();
				sb[i].append(str);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		for(int i = 0 ; i < 10; i++){
			if(sb[i] != null)
				System.out.println(sb[i]);
		}*/
	
	}
	private static void shuffleAttribute(String string) throws IOException {
		// TODO Auto-generated method stub
		File filingOb = new File(string);
		FileReader freader = new FileReader(filingOb);
		BufferedReader br = new BufferedReader(freader);
		ArrayList<String[]> arr= new ArrayList<String[]>();
		String tmpStr;
		int count = 0;
		while((tmpStr= br.readLine())!= null){
			String str[] = tmpStr.split("\t");
			if(str.length < 4)
				continue;
			//System.out.println("String split length "+count+": "+str.length);
			arr.add(str);
			count++;
		}
		System.out.println("Total Count:" + count);
		freader.close();
		br.close();
		PrintWriter pr = new PrintWriter(filingOb.getPath()+"userTweetContent2.txt");
		try{
			for(int i = 0; i < arr.size(); i++){
				//System.out.println(arr.get(i)[1]+"\t"+arr.get(i)[0]+"\t"+ arr.get(i)[2]);
				pr.println(arr.get(i)[1]+"\t"+arr.get(i)[0]+"\t"+arr.get(i)[2]+"\t"+arr.get(i)[3]);
			}
			
		}catch(Exception ex){
			System.err.println(ex.getMessage());
		}finally{
			pr.close();
		}
	}
	public static void localMultiMessageParser() throws IOException{
		File fileT = new File("D:\\TwitterBigData\\tweetMessage\\tweetContentQuit.txt");
		  PrintWriter pr = new PrintWriter(fileT,"UTF-8");
		  File fRead = new File("D:\\TwitterBigData\\userTweetContent.txt");
		  FileReader fReader = new FileReader(fRead);
		  BufferedReader br = new BufferedReader(fReader);
		  try {
				//reader.readXcelBook("D:\\TwitterData\\userTweetContent.xls",hash);
			  
				Enumeration<objectDes> enumObj = hash.getEnum();
				ArrayList<objectDes> tmpArray = new ArrayList<objectDes>();
				int  cou = 0;
				String str = "";
				br.readLine();
				int retok = 0;
				int iterat = 0;
				while((str = br.readLine())!=null){
					objectDes tmpObj = new objectDes();
					StringTokenizer st = new StringTokenizer(str,"\t");
					int j = 0;
					if(st.countTokens() < 4){
					    retok++;
						continue;
					}else
					iterat++;
					while(st.hasMoreElements()){
						String strT = st.nextToken();
						
						if(j == 2)
							tmpObj.setdbId(new Long(strT));
						else if(j == 1)
							tmpObj.settwitterId(new Long(strT));
						else if(j == 3)
							tmpObj.settwitMsg(strT);
						else
							tmpObj.setUserName(strT);
						j++;
					}
					tmpArray.add(tmpObj);
					cou++;
					//	System.out.println(tmpObj.getdbId() +" "+tmpObj.gettwitMsg() + " "+ tmpObj.gettwitterId()+ " "+tmpObj.getDate());
				
				}
				System.out.println("Rebound count: "+ retok+"Iteration: "+ iterat);
				multiHashedMapObj multiOb = new multiHashedMapObj();
				int counter = 0;
				for(int i = 0; i < tmpArray.size(); i++){
					if(tmpArray.get(i).twitMsg.contains("quit") ||tmpArray.get(i).twitMsg.contains("Quit") ){
						counter++;
						pr.println(tmpArray.get(i).getUserName()+"\t"+tmpArray.get(i).gettwitterId()+"\t"+tmpArray.get(i).getdbId()+"\t"+tmpArray.get(i).twitMsg);
					}
				}
			
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}finally{
				
				br.close();
				pr.close();
			}
		  
	}
	public static void messageParsingMain(ArrayList<objectDes> arrayOb) throws FileNotFoundException, UnsupportedEncodingException{
			  int i = 0;
			  String fileN = "twitMsg";
			 // Pattern pattern = Pattern.compile(pat);
			  System.out.println("this size: "+arrayOb.size());
			  
			 try{
			  for( i = 0; i < arrayOb.size(); i++){
				  //System.out.println(Msg.get(i).twitMsg);
				  File fi = new File("D:\\TwitterData\\malletQuitOutput\\"+ fileN + i +".txt");
				  PrintWriter pr = new PrintWriter(fi,"UTF-8");
				  if(arrayOb.get(i) == null)continue;
				  StringTokenizer tokenizer = new StringTokenizer(arrayOb.get(i).twitMsg," ");
				// System.out.println(Msg.get(i).twitMsg);
				  String totalMsg = "";
				  while(tokenizer.hasMoreElements()){
					  String strval = tokenizer.nextElement().toString();
					  try {
				            URL url = new URL(strval);
				            // If possible then replace with anchor...
				            System.out.println("<a href=\"" + url + "\">"+ url + "</a> " );
				            continue;
				        } catch (MalformedURLException e) {
				            // If there was an URL that was not it!...
				            
				        }
					 // System.out.println(strval);
					  //Matcher match = pattern.matcher(strval.toLowerCase());
					  Pattern pat = Pattern.compile("^[^<>%$?\\//-_:# @,&() ]*$");
					  strval = strval.toLowerCase();
					  int index;
					  if(strval.contains("#")){
						  //index = strval.indexOf('#');
						  strval = strval.replace("#", "");
					  } 
					  if(strval.contains("@")){
						  strval = strval.replace("@", "");
					  }
					  if(strval.contains(":"))
						  strval = strval.replace(":", "");
					  if(strval.contains("!"))
						  strval = strval.replace("!", "");
					  totalMsg += strval;
					  totalMsg +=" ";
					  
				  }	
				  pr.println(totalMsg.trim());
				  pr.close();
				  //System.out.println(totalMsg);
			  }
			 }catch(NullPointerException nulex){
				 
			 }catch(RuntimeException rex){
				 System.out.println(rex.getMessage());
			 }
			 finally{
				 System.out.println("Iteration " + i);
			 }		  
	}
}
