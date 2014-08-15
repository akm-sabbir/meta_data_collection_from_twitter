/*
 * Copyright 2007 Yusuke Yamamoto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package twitter4j.examples.user;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.plaf.SliderUI;

import collectionTest.hashedMapobj;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Search users with the specified query.
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public final class SearchUsers extends TimerTask {
    /**
     * Usage: java twitter4j.examples.user.SearchUsers [query]
     *
     * @param args message
     */
	private String AuthKey;
	private String AuthSecret;
	private String AuthToken;
	private String AuthTokenSecret;
	private String outputFilePath;
	private Twitter twitterFac;
	private String[] userList;
	private String[][] AuthInfo;
	private String[] dataSetPath;
	private int range;
	private int run_lenght;
	private ArrayList<ArrayList<String>> data;
	private String[] desPath;
	private hashedMapobj mapper;
	private PrintWriter configuration_file_writer ;
	private String configuration_file_dir = "C:\\TwitterBigData\\dataset\\configuration_file.txt";
	private final long ONCE_PER_HOUR = 1000*16*60;
	public SearchUsers(){
		
	}
	public SearchUsers(String a,String b,String c,String d){
		AuthKey = a;
		AuthSecret = b;
		AuthToken = c;
		AuthTokenSecret = d;
	}
	public Twitter getTwitterObject(){
		
		 ConfigurationBuilder cb = new ConfigurationBuilder();
    	 cb.setDebugEnabled(true);
    	 cb.setOAuthConsumerKey(AuthKey);
    	 cb.setOAuthConsumerSecret(AuthSecret);
    	 cb.setOAuthAccessToken(AuthToken);
    	 cb.setOAuthAccessTokenSecret(AuthTokenSecret);
    	 TwitterFactory tf = new TwitterFactory(cb.build());
    	 Twitter twitter =  tf.getInstance();
    	 return twitter;
	}
	public SearchUsers(String arr[][],int range,String[] datasetPath){
		AuthInfo = arr;
		this.range = range;
		this.desPath = new String[4];
		//this.data = new String[4][1000]String;
		this.data = new ArrayList<ArrayList<String>>();
		this.dataSetPath = datasetPath;
		this.mapper = new hashedMapobj(50000);
	}
	public void set_run_lenght(int val){
		this.run_lenght = val;
		return;
	}
	public void readDataSet() throws FileNotFoundException{
		File fileOb;
		FileReader filereader;
		BufferedReader bufferreader;
		Path p;
		for(int j =0; j < dataSetPath.length; j++){
			 p = Paths.get(dataSetPath[j]);
			 desPath[j] = p.getParent().toString();
		}
		for(int i = 0 ; i < dataSetPath.length; i++){
			fileOb = new File(dataSetPath[i]);
			filereader = new FileReader(fileOb);
			bufferreader = new BufferedReader(filereader);
			String str;
			try {
				int j = 0;
				ArrayList<String> tempData = new ArrayList<String>();
				while((str = bufferreader.readLine()) != null){
					tempData.add(str);
				}
				data.add(tempData);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void parseDataset(){
		for(int i =0; i < data.size(); i++){
			ArrayList<String> tempArr = data.get(i);
			for (int j = 0 ;j < tempArr.size(); j++)
				tempArr.set(j,tempArr.get(j).split("\t")[0]);// previuosly it was 0
		}
	}
	public void get_user_id(String source_path) throws FileNotFoundException{
		File fileOb = new File(source_path);
		FileReader fr = new FileReader(fileOb);
		BufferedReader br = new BufferedReader(fr);
		String str;
		try {
			while((str = br.readLine()) != null){
				String[] arr = str.split("\t");
				mapper.setData(arr[1],new Long( Long.parseLong(arr[0])));
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
		}
	}
	public void set_tweeter_factory() throws NumberFormatException, IOException{	
		int dest = 0;
		int k = 0;
		int lastData = 0;
		for(int i_j =0; i_j < data.size(); i_j++)
			System.out.println(data.get(i_j).size());
		//System.out.println(data.get(1).size());
		//System.out.println(data.get(2).size());
		System.out.println("size of data:"+data.size());
		//while(true){
		 BufferedReader br = new BufferedReader(new FileReader(configuration_file_dir));
		 String temp_str = br.readLine();
		 try{
			 System.out.println("stored data: " + temp_str);
			 lastData =  Integer.parseInt(temp_str);
		 }catch(NumberFormatException ex){
			 //bw.close();
			 br.close();
			 System.err.println(ex.getLocalizedMessage().toString());
			 this.cancel();
			 return;
		 }
		 if(lastData >= data.get(k).size()){
			 lastData = 0;
			 k++;
		 }
		 if(k >= data.size()){
			 this.cancel();
			 br.close();
			 //bw.close();
			 return;
			 //break;
		 }
		 for(int i = 0 ; i <  this.range; i++){
			ArrayList<String> tempData = new ArrayList<String>() ;
			int j = 0,l;
			for(j = lastData,l = 0 ;  j < (lastData + this.run_lenght) && j < data.get(k).size(); j++,l++){
				//System.out.println(mapper.get(data.get(i).get(j)));
				//System.out.println(data.get(i).get(j));
				//tempData[l] = (long)mapper.get(data.get(i).get(j));
				//System.out.println(k +" " +j);
				tempData.add(data.get(k).get(j));
				//System.out.println(tempData[l]);
			}
			//System.out.println("Auth Info "+ AuthInfo[i][0]);
			//System.out.println("Auth Key " + AuthInfo[i][1]);
			//System.out.println("Auth Secret " + AuthInfo[i][2]);
			//System.out.println("Auth Secret Token "+ AuthInfo[i][3]);
			if(lastData >=data.get(k).size())
				break;
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true);
			cb.setOAuthConsumerKey(AuthInfo[i][0]);
			cb.setOAuthConsumerSecret(AuthInfo[i][1]);
			cb.setOAuthAccessToken(AuthInfo[i][2]);
			cb.setOAuthAccessTokenSecret(AuthInfo[i][3]);
			TwitterFactory twitFac = new TwitterFactory(cb.build());
			//twitterFac  = twitFac.getInstance();
			try {
				 System.out.println("size of list structure: "+ tempData.size());
				 // this is for getting the complete urls, user names and screen names
				   GetHomeTimeline.startOperatingMetaData(twitFac.getInstance(), tempData, /*desPath[i]*/"C:\\TwitterBigData\\dataset\\"+"des_url_meta_expanded_data"+(new Integer(k)).toString()+".txt");
				// this is for getting complete urls and media urls from twitter
				   // this is for only getting the media entities
				// GetHomeTimeline.getMediaEntitiesForTweets(twitFac.getInstance(), tempData, "C:\\Binary_classifier_for_health\\"+"des_url_media_symbol_entities"+(new Integer(k)).toString()+".txt");
				//GetHomeTimeline.startOperating(twitFac.getInstance(),tempData,desPath[i]+"\\dest"+(new Integer(k)).toString()+".txt");
				//LookupUsers.mainLookups(twitFac,tempData);
				System.out.println("last write "+lastData);
				
			}catch(Exception ex){
				ex.printStackTrace();
			}
			/*catch(NullPointerException np){
				System.out.println("Java null pointer exception "+ np.getMessage());
			} 
			catch (Exception e) {
			// TODO Auto-generated catch block
				System.out.println("GetHometimeline throws exception " + e.getMessage());
				//e.printStackTrace();
			}*/finally{
				lastData += this.run_lenght;
				br.close();
			}
		 }
		 /*long currentTime = System.currentTimeMillis();
		 while((System.currentTimeMillis() - currentTime) < 16*60*1000){
			 
		 }*/
		 PrintWriter bw = new PrintWriter(new FileWriter(configuration_file_dir));
		 bw.println(lastData);
         bw.close();
		//}
		return;
	}
	public void set_user_list(String[] arr){
		this.userList = arr;
	}
	public void set_Consumer_key(String key){
		this.AuthKey = key;
	}
	public void set_consumer_key_secret(String key){
		this.AuthSecret = key;
	}
	public void set_consumer_token(String key){
		this.AuthToken = key;
	}
	public void set_consumer_token_secret(String key){
		this.AuthTokenSecret = key;
	}
	public void get_tweets_from_time_line(){
		
		return;
	}
	public void printAuthenticationData(){
		System.out.println("Auth Key: "+ AuthKey);
		System.out.println("Auth Secret: "+ AuthSecret);
		System.out.println("AuthToken: "+ AuthToken);
		System.out.println("Auth Toekn Secret: "+ AuthTokenSecret);
		return;
	}
	public void setPath(String output){
		outputFilePath = output;
	}
    public void mainSearch(Vector<String> args) {
        if (args.size() < 1) {
            System.out.println("Usage: java twitter4j.examples.user.SearchUsers [query]");
            return;
           // System.exit(-1);
        }
        String[] workStr = new String[args.size()/2];
        
        try {
        	 ConfigurationBuilder cb = new ConfigurationBuilder();
        	 cb.setDebugEnabled(true);
        	 cb.setOAuthConsumerKey(AuthKey);
        	 cb.setOAuthConsumerSecret(AuthSecret);
        	 cb.setOAuthAccessToken(AuthToken);
        	 cb.setOAuthAccessTokenSecret(AuthTokenSecret);
        	 TwitterFactory tf = new TwitterFactory(cb.build());
        	 Twitter twitter =  tf.getInstance();
            
            int page = 1;
            File fobj = new File(outputFilePath);
            if(!fobj.exists())
            	fobj.createNewFile();
            FileWriter fw = new FileWriter(fobj,true);
            PrintWriter br = new PrintWriter(fw);
    		StringBuilder[] stbuilder;
    		String[] str = new String[]{};
    		//StringBuilder[] sb = new StringBuilder[100];
    		
            ResponseList<User> users = null;
              int i = 0;
              System.out.println("Size of the vector: "+ args.size());
            do {
            	for(int j = 0; j < args.size()/2; i++)
            		workStr[j++] = args.elementAt(i);
                users = twitter.lookupUsers(workStr);//(args.elementAt(i),1);
                if(users.size() != 0){
                 for (User user : users) {
                    //if (user.getStatus() != null) {
                      //  System.out.println("@" + user.getScreenName() + " - " + user.getDescription());
                    //} else {
                        // the user is protected
                	try{
                		//if(users.size() != 0){
                			br.println(user.getId() +"\t"+user.getScreenName() +"\t" + user.getDescription());
                			
                			//br.print("\n");
                		//}
                			//System.out.println("@" + user.getScreenName()  +" - "+ user.getDescription());
                	}catch(NullPointerException io){
                		io.printStackTrace();
                	}catch(Exception ex){
                		
                	}finally{
                			//br.close();
                	}
                    //}
                 }
                }
          
            } while (users.size() != 0 && i < args.size());
            br.close();
            System.out.println("done.");
            //System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search users: " + te.getMessage());
           /// System.exit(-1);
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void start_task() throws IOException{
    	File file_ob = new File(configuration_file_dir);
    	if(!file_ob.exists()){
    		file_ob.createNewFile();
    		file_ob.setWritable(true);
    		file_ob.setReadable(true);
    	}
    	try{
    		configuration_file_writer = new PrintWriter(new FileWriter(file_ob));
    		configuration_file_writer.println(new Integer(87312).toString());
    		configuration_file_writer.flush();
    		configuration_file_writer.close();
    	}catch(IOException ex){
    		System.out.println(ex.getMessage().toString());
    	}finally{
    		configuration_file_writer.close();
    	}
    	
    		try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	
    	try{
    		Timer time = new Timer();
    		time.schedule(this, 1000, ONCE_PER_HOUR);
    	}catch(Exception ex){
    		System.err.println("this is an error what r you gonna do\n");
    	}
    }
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			set_tweeter_factory();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
