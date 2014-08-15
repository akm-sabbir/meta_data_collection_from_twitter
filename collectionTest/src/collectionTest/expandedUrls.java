package collectionTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.ResponseCache;
import java.net.URL;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class expandedUrls {
	
	public void openFileToReadWrite(String pathName,String pathNameOutput) throws IOException{
	File fileObjectForRead = new File(pathName);
	Vector<Vector<String>> vec = new Vector<Vector<String>>();
	FileReader	fr = new FileReader(fileObjectForRead);
	BufferedReader	br = new BufferedReader(fr);
	PrintWriter	pr = new PrintWriter(new File(pathNameOutput));
	Pattern pat = Pattern.compile("(http://(shm.ag|yhoo.it|flip.it|shop.pe|bbc.in|adf.ly|fb.me|shar.es|bit.ly|t.co|lnkd.in|tcrn.ch|youtu.be|on.fb.me|sco.lt|goo.gl|ow.ly|shm.ag|reut.rs|wp.me|awe.sm|bloom.bg|cbc.sh|voc.tv|sk.mu|t5a.co)\\S*)\\b");
	Pattern pat1 = Pattern.compile("http://[a-zA-Z]+.[a-zA-Z]{2}/[a-zA-Z0-9]+"); 
		//expandUrl = new expandedUrls();
		try {
			
			int  i = 0;
			String tempStr="";
			String tempS = br.readLine();
			//while(i++ < looper && tempS != null) tempS = br.readLine();
			System.out.println("this is startof thread: "+i);
			if(tempS == null ){
				throw new NullPointerException("its a null Object");
			}
			System.out.println("this is initiation:" + tempS);
			i= 0;
			while((tempStr = br.readLine()) != null){
				//System.out.println("Stage 1");
				try{
					String[] tempStrArr = tempStr.split("\t");
					Vector<String> element = new Vector<String>();
					if(tempStrArr.length < 4)
						continue;
					element.add(tempStrArr[0]);
					element.add(tempStrArr[1]);
					int size = tempStrArr.length;
					//System.out.println("Stage 2");
					//if(size == 3){
						String urlString;
						element.add(tempStrArr[2]);
						//synchronized (expandedUrls.class) {
						Matcher matching  = pat1.matcher(tempStrArr[3]);
						if(matching.find() == true){
							urlString = getExpandedUrls(tempStrArr[3]);
							Matcher mat = pat.matcher(urlString);
							if(mat.find())
								continue;
							System.out.println("stage: "+ i +"url:" + urlString);
							element.add(urlString);
					    }else 
					    	element.add(tempStrArr[3]);
		/*			}else if(size == 4){
						Matcher mat = pat.matcher(tempStrArr[3]);
						if(mat.find())
							continue;
						element.add(tempStrArr[2]);
						element.add(tempStrArr[3]);
					}*/
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
						pr.print(vec.elementAt(i1).elementAt(j) + "\t");
					pr.print("\n");
				}	
				br.close();
				pr.close();
			
		}
		
	
	}
	
	public String getBitlyUrls(String urls, String login, String key, boolean xml){
		    URL url;
		    String reqUri =
			        String.format("http://api.bit.ly/v3/expand?" +
			        "login=%s&apiKey=%s&format=%s&shortUrl=%s",
			        "akmsabbir", "R_d67289e2339a372c375200966d56a98f", "txt", urls);
			System.out.println(reqUri);
		    
		    BufferedReader reader = null;
		    String stringBuild = "";	
			try {
				
				url = new URL(reqUri);
			    reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
			    int i = 0; 
			    for (String line; (line = reader.readLine()) != null;) {
			       stringBuild = line;
			       System.out.println(stringBuild);
			    }
			}catch(IOException ex){
				System.out.println(ex.getMessage());
			}catch(NullPointerException ex){
				System.out.println(ex.getMessage());
			}
			finally {
			    if (reader != null) try { reader.close(); } catch (IOException ignore) {}
			}	
			return stringBuild;
}
	public String getURLS(String urls) throws UnsupportedEncodingException, IOException{
		String fixSuffix = "&api_key={1afe72ed1ad8fc5f30018bbddbf30a70}&format=xml";
        String fixPrefix ="http://api.unshort.me/unshorten?r="; 
		URL url = new URL(fixPrefix + urls);
		BufferedReader reader = null;
	
		String stringBuild = "";	
		try {
		    reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
		    int i = 0; 
		    for (String line; (line = reader.readLine()) != null;) {
		       stringBuild = line;
		       
		    }
		}catch(IOException ex){
			System.out.println(ex.getMessage());
		}catch(NullPointerException ex){
			System.out.println(ex.getMessage());
		}
		finally {
		    if (reader != null) try { reader.close(); } catch (IOException ignore) {}
		}
		//System.out.println(stringBuild);
		int indexStart;
		int indexEnd;
		if(stringBuild.length() >= 8){
			indexStart = stringBuild.indexOf("resolvedURL") + "requestedURL".length()+3;
			indexEnd = stringBuild.indexOf("requestedURL") -4;
			return stringBuild.substring(indexStart,indexEnd);
		}
		else
			return stringBuild;
		
		//System.out.print(indexStart + " "+ indexEnd);
		
	}

	public static String getExpandedUrls(String shortUrls) throws IOException{
		System.out.println(shortUrls);
		String expandedURL =null;
		Pattern pat = Pattern.compile("(http://(tmblr.co|shm.ag|yhoo.it|flip.it|shop.pe|bbc.in|adf.ly|fb.me|shar.es|bit.ly|t.co|lnkd.in|tcrn.ch|youtu.be|on.fb.me|sco.lt|goo.gl|ow.ly|shm.ag|reut.rs|wp.me|awe.sm|bloom.bg|cbc.sh|voc.tv|sk.mu|t5a.co)\\S*)\\b");
		try{
			int i = 0;
			while(true){
				URL url = new URL(shortUrls);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY); //using proxy may increase latency
				
				connection.setInstanceFollowRedirects(false);
				connection.connect();
				expandedURL = connection.getHeaderField("Location");
				connection.getInputStream().close();
				
				if(expandedURL == null ){
					expandedURL = shortUrls;
					break;
				}else if(expandedURL.contains("shm.ag") == true){
					 expandedURL = "http://www.dle-cig.com/";
					 return expandedURL;
				 }
				
				System.out.println("Expanded "+ expandedURL);
				Matcher mat = pat.matcher(expandedURL);
				
				  if(i++ < 3)
					shortUrls = expandedURL;
				  else
					break;
			}
		    return expandedURL;	
		}catch(MalformedURLException ex){
			//shortUrls = "malFormed";
			System.err.println("Exception Msg :" + ex.getMessage());
		}catch(IOException ioe) {
		     System.out.println("Can not connect to the URL");
		 }finally{
		//	shortUrls = "malFormed";
			
			System.err.println("Inside finally Exception Msg :");
		}
		return shortUrls;    	    
	}
}
