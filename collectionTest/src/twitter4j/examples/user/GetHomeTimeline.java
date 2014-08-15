package twitter4j.examples.user;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import twitter4j.MediaEntity;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.SymbolEntity;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.URLEntity;
import twitter4j.User;

public class GetHomeTimeline {
	private static int counter = 0;
	public static void getMediaEntitiesForTweets(Twitter fac, ArrayList<String> userList, String desFileName){
		System.out.println("start collecting media entities");
		PrintWriter pr_media = null;
		Status user = null;
		MediaEntity[] media_entities = null;
		SymbolEntity[] symbol_entities = null;
		try{
			pr_media = new PrintWriter(new BufferedWriter(new FileWriter(desFileName,true)));
		
		 for(int i = 0; i < userList.size(); i++){
			try{
				user = fac.showStatus(Long.parseLong(userList.get(i).trim()));
				if(user == null){
					System.out.println("this tweet message is no longer valid");
					continue;
				}
				media_entities = user.getMediaEntities();
				symbol_entities = user.getSymbolEntities();
				pr_media.write(user.getUser().getScreenName() + "\t" + user.getUser().getName()+"\t");
				for(int j = 0 ; j < media_entities.length; j++){
					pr_media.write(media_entities[j].getDisplayURL()+"\t"+media_entities[j].getExpandedURL()+"\t"+ media_entities[j].getMediaURL()+"\t"+media_entities[j].getMediaURLHttps()+"\t");
				}
				for(int j =0; j < symbol_entities.length; j++)
					pr_media.write(symbol_entities[j].getText() + symbol_entities[j].getStart() +"\t"+ symbol_entities[j].getEnd());
				pr_media.write("\n");
			}catch(TwitterException twexception){
				System.out.println("twitter throws an exception : " + twexception.getMessage());
			}
		 }
		}catch(IOException ioexception){
			System.out.println(" input output exception so: " + ioexception.getMessage());
		}finally{
			pr_media.close();
		}
		System.out.println("end of collecting media entities");
		return;
	}
	public static void startOperatingMetaData(Twitter fac,ArrayList<String> userList,String destFile){
		System.out.println("start Operating on meta Data");
		PrintWriter pr = null;
		try{
			pr = new PrintWriter(new BufferedWriter( new FileWriter(destFile,true)));
		}catch(IOException ex){
	             System.err.print(ex.getMessage());		
		}
		if(fac == null){
			System.out.println("Twitter factory object creation failed");
			return;
		}
		URLEntity[] url_entities = null;
		Status user = null;
		System.out.println("size of user list"+userList.size());
		
			for(int i = 0; i < userList.size(); i++){
			 try{
				//System.out.println("element to be served" + (new Integer(counter++)).toString());
				System.out.println("item no:" + userList.get(i));
				user = fac.showStatus(Long.parseLong(userList.get(i).trim()));
				if(user == null){
					System.out.println("tweet message has been deleted so no way to retrieve any information regarding this\n");
					continue;
				}
				url_entities = user.getURLEntities();
				if(url_entities == null){
					System.out.println("there is no url entities ");
					return;
				}
				System.out.println("url lenght "+url_entities.length);
				if(url_entities.length == 0){
						//if(url_en == null)
					    System.out.println("Empty Urls continue for" + counter);
						continue;
				}
	    		//if(url_entities.length != 0)
				for(URLEntity url_entity: url_entities){
					if(user.getUser().getScreenName() != null)
						pr.write(user.getUser().getScreenName()+"\t");
					if(user.getUser().getName() != null)
						pr.write(user.getUser().getName()+"\t");
					
					if(url_entity.getDisplayURL() != null)
						pr.write(url_entity.getDisplayURL()+"\t");
					if(url_entity.getExpandedURL()!= null)
						pr.write(url_entity.getExpandedURL());
					pr.write("\n");
				}
				//pr.write("\n");
			 }catch(TwitterException twitExcep){
					System.out.println("element to be served" + (new Integer(counter++)).toString());
					twitExcep.printStackTrace();
					//System.err.println("failed to search tweets: "+twitExcep.getStatusCode()+" "+ twitExcep.getCause().toString());
			  }finally{
					
			  }
		   }
			pr.close();
		
		return;
	}
	public static void startOperating(Twitter fac,String[] userList,String destFileName) throws TwitterException{
		System.out.println("We are executing\n");
		if(fac == null)
			return;
		System.out.println(destFileName);
		Twitter twitter = fac;
		ResponseList<User> users=null;
		Paging paging=null;
		//User user = twitter.verifyCredentials();
		//for(int i = 0; i < 3; i++){
			//for(String item: userList)
				//System.out.println(item);
		try{
		    users = fac.lookupUsers(userList);
			paging = new Paging(1,200);
		}catch(TwitterException twitMsg){
			System.err.println(twitMsg.getMessage());
		}finally{
			
		}
		List<Status> statuses;
		PrintWriter prw = null;
		try {
				try {
					prw = new PrintWriter(new BufferedWriter(new FileWriter(destFileName,true)));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
		}
		System.out.println("Writing user information");
			for(User user: users){
				try{
					statuses = twitter.getUserTimeline(user.getScreenName(), paging);
					prw.write(user.getScreenName()+"\t"+user.getName()+"\t");
					for(Status status  : statuses){
					
						String str = status.getText();
						while(str.contains("\t"))
							str =	str.replace("\t", " ");
						while(str.contains("\n"))
							str = str.replace("\n"," ");
						prw.write(str+" "+"TimeeeeerDate:"+" " + status.getCreatedAt() + " ID: " +status.getId() );
						//status.get
						prw.write("\t");
			
					}
				}catch(TwitterException twe){
					System.err.println(twe.getMessage());
				}finally{
					prw.write("Nexxxt user\n\n\n");
				}
				
			
			}
			prw.close();
		//}
	
	}
}
