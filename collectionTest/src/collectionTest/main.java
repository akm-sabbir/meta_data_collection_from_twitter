package collectionTest;
import httpRequestandSend.HttpURLConnectionExample;

import java.io.BufferedInputStream;

import scheduler.myTimerTask;
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
import java.util.*;

import javax.net.ssl.HttpsURLConnection;

import jxl.read.biff.BiffException;

public class main {

	public static excelReader reader;
	public static String nameFile = "D:\\TwitterData\\hashtags.xls";
	public static String outnameFile = "D:\\TwitterData\\output.xls";
	public static hashedObject hash = new hashedObject(32000);
	public static String[] files = {"D:\\TwitterData\\userNames.txt","D:\\TwitterData\\outputUrls1.txt","D:\\TwitterData\\outputuserMentions1.txt","D:\\TwitterData\\outputhashTags1.txt"};
	public static String[] outputFiles = {"D:\\TwitterData\\userNamesRanks.txt","D:\\TwitterData\\outputUrlsRank.txt","D:\\TwitterData\\outputuserMentionsRank.txt","D:\\TwitterData\\outputhashTagsRank.txt"};
	public static int[] indexArr = {0,4,3,4}; // how many columns
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
			writer.mainStart("D:\\TwitterData\\userProfile.xls", "D:\\TwitterData\\userProfile.txt");

			//writer.mainStart("D:\\TwitterData\\RetweetGroup.xls","D:\\TwitterData\\retweetGroup.txt");
		} catch (FileNotFoundException e3) {
		// TODO Auto-generated catch block
			e3.printStackTrace();
		}
	}
	public static void readAndSort(){
		
		for(int i = 0 ; i < 1; i++){
			hashedMapobj hashSet = new hashedMapobj(60000);
			BufferedReader bfr = null;
			File fobj = new File(files[i]);
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
			try {
				while((tmpStr = bfr.readLine())!= null){
				
					String[] strArr = tmpStr.split("\t");
					System.out.println("Stage #: "+ kl++);
					try{
						if(hashSet.isContain(strArr[indexArr[i]]) == true){
							int val = hashSet.get(strArr[indexArr[i]]);
							val++;
							hashSet.setData(strArr[indexArr[i]], new Long(val));
						}else{
							hashSet.setData(strArr[indexArr[i]], new Long(1));
						}
					}catch(NullPointerException ex){
						System.out.print(ex.getMessage());
					}
				}
				bfr.close();
				sortOutData.getDataToSort(hashSet,outputFiles[i]);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				
			}
				
		}
		return;
	}
	public static void readExpandedUrls() throws IOException{
		
		File fob = new File("D:\\TwitterData\\outputUrls1.txt");
		File fouput = new File("D:\\TwitterData\\outputExpandedUrls1.txt");
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
		try{
		while((tempStr = fbreader.readLine()) != null && i < 5000){
			try{
			String[] tempStrArr = tempStr.split("\t");
			Vector<String> element = new Vector<String>();
			element.add(tempStrArr[3]);
			String urlString = expandedUrls.getExpandedUrls(tempStrArr[3]);
			//System.out.println("url:" + urlString);
			element.add(urlString);
			vec.add(i++, element);
			//break;//test
			}catch(NullPointerException ex){
				
			}finally{
				
			}
			//hashMap.setData(tempStrArr[3],  expandedUrls.getExpandedUrls(tempStrArr[3]));
		}
		}catch(IOException ioe){
			System.err.println(ioe.getMessage());
		}finally{
			for(int i1 =0 ; i1 < vec.size(); i1++){
				for(int j = 0; j < vec.elementAt(i1).size(); j++)
					pr.print(vec.elementAt(i1).elementAt(j) + " ");
				pr.print("\n");
			}
			fbreader.close();
			pr.close();
		}
		
		
	}
	public static void main(String arg[]) throws IOException {
		//HashSet hs = new HashSet();
		reader = new excelReader();
		
		HttpURLConnectionExample httpUrl = new HttpURLConnectionExample();
		//option1//writeToExcels();
		readExpandedUrls();
		//options//messageParser msgParser = new messageParser();
		//globalObjectsContext gbc = globalObjectsContext.creatorOfObjects(new multiHashedMapObj(), "D:\\TwitterData\\userProfile1.txt", "D:\\TwitterData\\userProfileProcessed.txt");
		//gbc.setFileToReadWrite();
		//gbc.startFillHashMap();
		//gbc.writeToFile();
		//gbc.inceptionOfContextOperation();
		//option2//readAndSort();
       //option3//	myTimerTask.startTask();
		/*try {
			reader.readXcelBook(nameFile,hash);
			Enumeration<objectDes> enumObj = hash.getEnum();
			ArrayList<String> tmpArray = new ArrayList<String>();
			int  cou = 0;
			while(enumObj.hasMoreElements()){
				objectDes tmpObj = enumObj.nextElement();
				tmpArray.add(tmpObj.gettwitMsg());
				cou++;
				//System.out.println(tmpObj.getdbId() +" "+tmpObj.gettwitMsg() + " "+ tmpObj.gettwitterId()+ " "+tmpObj.getDate());
				
			}
			//System.out.print(cou);
			
			msgParser.setArraylist(tmpArray);
			msgParser.parsingMsg();
			msgParser.calculateStatistics();
			System.out.print("HashTagCount: "+ msgParser.getHashtagCount() );
			System.out.print(" FollowerCount: " + msgParser.getFollowerCount());
			System.out.print(" LinkCount: " + msgParser.getLinkCount());
		} catch (BiffException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
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
}
