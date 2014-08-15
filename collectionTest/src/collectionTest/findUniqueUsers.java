package collectionTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileLockInterruptionException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.map.HashedMap;

import com.google.common.collect.Multiset.Entry;

public class findUniqueUsers {
	private String fileName; 
	private String outputFileName;
	private File fileObj;
	private File fileWriterObj;
	private PrintWriter pr;
	private BufferedReader br;
	private FileReader fr;
	private hashedObject hash;
	private hashedMapobj hashMap;
	private Pattern pat = Pattern.compile("[0-9]+");
	private Pattern pat1 = Pattern.compile("[a-zA-Z]+");
	private Pattern pat2 = Pattern.compile("^[^<>%$?\\//-_:]*$");
	private findUniqueUsers(){
		
	}
	public int createHashObject(int size){
		try{
			hash = new hashedObject(size);
			return 1;
		}catch(NullPointerException e){
			e.printStackTrace();
		}finally{
			
		}
		
			return 0;
		
	}
	public int createHashMap(int size){
		try{
			hashMap = new hashedMapobj(size);
			return 1;
		}catch(NullPointerException e){
			e.printStackTrace();
		}finally{
			
		}
		
			return 0;
		
	}
	public int createSortOutData(){
		try{
			//sorting = new sortOutData();
			return 1;
		}catch(NullPointerException e){
			e.printStackTrace();
			
		}
	    return 0;
	}
	public static findUniqueUsers createFindUniqueUsers(int size){
		findUniqueUsers findUser =  new findUniqueUsers();
		findUser.createHashObject(size);
		findUser.createHashMap(size);
		findUser.setFileName(null);
		return findUser;
	}
    public void init(){
    	
    }
    public void setFileWriterObj(String str){
    	outputFileName = str;
    }
	public void setFileName(String str){
	   this.fileName = str;
	}
	public String getFileName(){
		return this.fileName;
	}
	public int initFileReader(){
		if(getFileName() == null)
			return 0;
		try{
		fileObj = new File(getFileName());
		if(!fileObj.exists())
			return 0;
		else if(!fileObj.canRead() )
			return 0;
		fr = new FileReader(fileObj);
		br = new BufferedReader(fr);
		}catch(FileNotFoundException ex){
			//System.err.println(ex.getMessage());
			ex.printStackTrace();
		}catch(NullPointerException nullex){
			//System.err.println(nullex.getMessage());
			nullex.printStackTrace();
		}catch(Exception ex){
			//System.err.println(ex.getMessage());
			ex.printStackTrace();
		}finally{
			
		}
	    return 1;
	}
	public void closeFileReader(){
		try{
			fr.close();
			br.close();
		}catch(FileLockInterruptionException ex){
			
		}catch(FileNotFoundException ex){
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
	public void closeFileWriter(){
		try{
			pr.close();
		}catch(NullPointerException e){
			e.printStackTrace();
		}
	}
	public void initDifferentFileWriter(String str){
		try{
			pr = new PrintWriter(new FileWriter(new File(str)));
		}catch(Exception ex){
			System.err.print(ex.getMessage());
		}
	}
	public void initFileWriter(){
		try{
			br.ready();
			System.out.println("First Close the Reader Stream");
			return;
		}catch(IOException ex){
			
		}finally{
			try{
				pr = new PrintWriter(fileObj);
			}catch(IOException e){
				
			}
		}
	}
	public String[] stringTokenizer(String str,String delimiter,int size){
		StringTokenizer stk = new StringTokenizer(str,delimiter);
		String[] tmpStr = new String[size];
		int i = 0;
		while(stk.hasMoreElements()){
			tmpStr[i++] = stk.nextToken();
		}
		return tmpStr;
	}
	public void startReadingFillHashOb(int idKey){
		String str = null;
		
		try{
			while( ( str = br.readLine() ) != null){
				String[] strArr = stringTokenizer(str, "\t", 4);
				try{
					System.out.println(strArr[idKey]);
					if(!hash.isContains(new Long(strArr[idKey].trim()))){
						objectDes tempObj = new objectDes();
						Matcher mat = pat.matcher(strArr[idKey]);
						Matcher mat1 = pat1.matcher(strArr[idKey]);
						Matcher mat2 = pat2.matcher(strArr[idKey]);
						if(mat.find() && !mat1.find() && mat2.find())
							tempObj.setdbId((new Long(strArr[idKey])).longValue());
						
						mat = pat.matcher(strArr[1]);
						mat1 = pat1.matcher(strArr[1]);
						mat2 = pat2.matcher(strArr[1]);
						if(mat.find() && !mat1.find() && mat2.find())
							tempObj.settwitterId((new Long(strArr[1])).longValue());
						else
							tempObj.setUserName(strArr[1]);
						if(strArr[2] != null)
							tempObj.settwitMsg(strArr[2]);
						hash.setHashdata( (new Long(strArr[idKey].trim())),tempObj);
					}
				}catch(NumberFormatException e){
					e.printStackTrace();
				}
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}finally{
			closeFileReader();
		}
	}
	public void startReadingFillHashMap(){
		String str = null;
		try{
			while( ( str = br.readLine() ) != null){
				String[] strArr = stringTokenizer(str, " ", 3);//\t, 4
				try{
					if(hashMap == null)
						System.out.println("I am null");
					//System.out.println("Id is "+strArr[0]);
					hashMap.setData(strArr[0], new Long(strArr[2])); // should hav been 1
					/*if(!hashMap.isContain(strArr[0].trim())){
						hashMap.setData(strArr[0], new Integer(1));
					}else{
						Integer itVal = hashMap.get(strArr[0].trim());
						int val = itVal.intValue();
						hashMap.setData(strArr[0], new Integer(++val));
					}*/
				}catch(NumberFormatException e){
					e.printStackTrace();
				}catch(NullPointerException nule){
					nule.printStackTrace();
				}
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}finally{
			closeFileReader();
		}
	
		
	}
	public void doFileOperations(String filename,String output) throws IOException{
		File filereader = new File(filename);
		FileReader fr = new FileReader(filereader);
		BufferedReader br = new BufferedReader(fr);
		PrintWriter pr = new PrintWriter(new File(output));
		String tmpString;
		while((tmpString = br.readLine()) != null){
			String[] strArr = stringTokenizer(tmpString, "\t", 3);//\t, 4
			if(hashMap.isContain(strArr[1]))
				pr.println(strArr[1]+"\t"+strArr[0]+"\t"+hashMap.get(strArr[1]));
		}
		pr.close();
	}
	public hashedMapobj getContainerReferenceMap(){
		return hashMap;
	}
	public hashedObject getContainerReferenceObj(){
		return hash;
	}
	public void getStatistics(){
		
	}
	public long getSizeHashOb(){
		return hash.getHashsize();
	}
	public long getSizeHashMap(){
		return hashMap.getSize();
	}
	public void printHashObject(String str){
		Enumeration<objectDes> enumerate = hash.getEnum();
		initDifferentFileWriter(str);
		while(enumerate.hasMoreElements()){
			objectDes tmpOb = enumerate.nextElement();
			pr.println(tmpOb.dbId +"\t"+ tmpOb.twitterId+"\t"+ tmpOb.twitMsg);			
		}
		pr.close();
	}
	public void printHashSet(){
		int i = 0 ; 
		Set<java.util.Map.Entry<String, Long>> sets = hashMap.getEnumset();
		for(java.util.Map.Entry<String, Long> key : sets){
			if(key.getValue() != null)
				System.out.println(key.getKey() +" " +  key.getValue()+"index No: "+ i++);
		}
	}
	public void clearHashMap(){
		hashMap.clearContainer();
	}
	public void clearHashObject(){
		hash.clearContainer();
	}
	public void processedUserProfile(String str,String[] args){
		//setFileName(str);
		//closeFileReader();
		//initFileReader();
		//clearHashObject();
		//createHashObject(20000);
		//startReadingFillHashOb();
		
		//System.out.println("Size of the hashObject: "+ hash.getHashsize());
		//int i = 0;
		//Set<java.util.Map.Entry<String, Integer>> sets = hashMap.getEnumset();
		//for(java.util.Map.Entry<String, Integer> key : sets){
			//if(hash.isContains(new Long(key.getKey())))
			//	System.out.println(key.getKey() +" " +  key.getValue()+"index No: "+ i++);
		//}
		
		BufferedReader newBr;
		int i = 0;
		try {
			newBr = new BufferedReader(new FileReader(new File(str)));
			String tempStr = null;
			System.out.println("Size of HashMap is: "+ hashMap.getSize());
			initDifferentFileWriter("D:\\TwitterData\\quit\\uniqueQuit\\quitContaingProfile.txt");
			ArrayList<String[]> doubleArr = new ArrayList<String[]>(hashMap.getSize()+1);
			
			//String[] doubleArr = new String[hashMap.getSize()];
			for(int k = 0; k < hashMap.getSize()+1; k++)
				doubleArr.add(new String[4]);
			//System.out.println("Inside " + args.length);
			while((tempStr = newBr.readLine())!= null){
				String[] strArr = stringTokenizer(tempStr, "\t", 3);
				if(hashMap.isContain(strArr[0].trim())){
					
					for(int i1 = 0; i1 < args.length-1; i1++){
					//	System.out.println("Inside " + args[i1] +" "+strArr[0].trim());
						if(args[i1].trim().equals(strArr[0].trim())){
							int l = 0;
				//			System.out.println("Inside " + strArr.length);
							for(l = 0; l < strArr.length - 1; l++)
								doubleArr.get(i1)[l] = strArr[l];
							doubleArr.get(i1)[l++] = new Long(hashMap.get(strArr[0].trim())).toString();
							doubleArr.get(i1)[l] = strArr[l-1];
							
						}
					}
					//pr.println(strArr[0]+"\t"+ strArr[1]+"\t"+ hashMap.get(strArr[0].trim())+"\t"+strArr[2]);
					//hashMap.removeData(strArr[0].trim());
				}
				//else
					//System.out.println("Not Found: "+ strArr[0].trim());
			}
			
			newBr.close();
			i = 0;
			messageParser mp = new messageParser("D:\\TwitterData\\quit\\uniqueQuit\\quitContaingProfile.txt");
			mp.setParsing(1);
		    ArrayList<objectDes> listArr = new ArrayList<objectDes>(1000);
		    try{
		      for(int k = 0; k < doubleArr.size(); k++){
				//for(int l = 0 ; l < doubleArr[k].length; l++)
				 if(doubleArr.get(k)[0] != null){
					 objectDes obj = new objectDes();
					 obj.setdbId(new Long(doubleArr.get(k)[0]).longValue());
					 //if(doubleArr.get(k)[1]!= null)
						 obj.setUserId(doubleArr.get(k)[1]);
					 obj.setRank(new Integer(doubleArr.get(k)[2]).intValue());
					 obj.settwitMsg(doubleArr.get(k)[3]);
					 listArr.add(obj);
					pr.println(doubleArr.get(k)[0]+"\t"+ doubleArr.get(k)[1]+"\t"+ doubleArr.get(k)[2]+"\t"+doubleArr.get(k)[3]);
				 }
			  }
		    }catch(NullPointerException ex){
		    	System.err.println(ex.getMessage());
		    }
			pr.close();
			mp.setArraylist(listArr);
			mp.parsingMsg("D:\\TwitterData\\quit\\uniqueQuit\\quitContaingKeywordsProfile.txt");
			mp.print();
	     
			System.out.println("Size of HashMap: "+ hashMap.getSize());
			/*Set<java.util.Map.Entry<String, Integer>> sets = hashMap.getEnumset();
			for(java.util.Map.Entry<String, Integer> key : sets){
				if(hash.isContains(new Long(key.getKey())))
					System.out.println(key.getKey() +" " +  key.getValue()+"index No: "+ i++);
			}*/
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			 
		}
		
		
		
	}
}
