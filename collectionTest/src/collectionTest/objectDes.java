package collectionTest;

import java.util.Date;

public class objectDes {
	public long dbId;
	public long twitterId;
	public long memberCounter;
	public String twitMsg;
	public String userId;
	public String userName;
	public java.util.Date date;
	public int rank;
	public objectDes(){
		memberCounter = 0;
	}
	public void setUserId(String str){
		userId = str;
	}
	public void setRank(int val){
		rank = val;
	}
	public int getRank(){
		return rank;
	}
	public String getUserId(){
		return userId;
	}
	public void setUserName(String str){
		userName = str;
	}
	public String getUserName(){
		return userName;
	}
	public void setMembercounter(){
		memberCounter = 0;
		return;
	}
	public long getdbId(){
		return dbId;
	}
	public long gettwitterId(){
		return twitterId;
	}
	public String gettwitMsg(){
		return twitMsg;
	}
	public Date getDate(){
		return date;
	}
	public void setdbId(long id){
		this.dbId = id;
	}
	public void settwitterId(long id){
		this.twitterId = id;
	}
	public void settwitMsg(String str){
		this.twitMsg = str;
	}
	public void setDate(java.util.Date dt){
		this.date = dt;
	}
}
