package collectionTest;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

public class hashedMapobj {
	public HashMap<String, Long> hashedMapcontainer = null;
	//public HashMap<String, String> hashMapContainerStr = null;
	public long totalObj;
	public char co;
	public hashedMapobj(long size){
		int rsize = (int)(((double)size)/0.75);
		hashedMapcontainer = new HashMap<String,Long>( rsize);
		totalObj = 0;
	}
	public hashedMapobj(long size, char c){
		//hashMapContainerStr = new HashMap<String,String>();
		co = c;
	}
	public hashedMapobj() {
		// TODO Auto-generated constructor stub
	}
	public long getObjectcounter(){
		return totalObj;
	}
	public void setData(String key, Long obj){
		try{
		if(!hashedMapcontainer.containsKey(key)){
				hashedMapcontainer.put(key, obj);
	            totalObj++;
		}
		else{
			Long tmpInt =  hashedMapcontainer.get(key);
			long tmpVal = tmpInt.intValue();
			tmpVal++;
			hashedMapcontainer.put(key, tmpVal);
		}
		}catch(NullPointerException ex){
			System.err.println(ex.getMessage());
		}
	}
	public int getCount(String str){
		return hashedMapcontainer.get(str).intValue();
	}
	public Collection<Long> getCollections(){
		return hashedMapcontainer.values();
	}
	public int getSize(){
		return hashedMapcontainer.size();
	}
	public int get(String key) {
		// TODO Auto-generated method stub
	  return	hashedMapcontainer.get(key).intValue();
	
	}
	public boolean isContain(String key){
		try{
			if(hashedMapcontainer.containsKey(key))
				return true;
			else
				return false;
		}catch(NullPointerException ex){
			System.err.println("Error generated here is: "+ ex.getMessage());
			return false;
		}
	}
	public Set<Entry<String,Long>> getEnumset(){
		return hashedMapcontainer.entrySet();
	}
	public void clearContainer(){
		hashedMapcontainer.clear();
	}
	
	public void removeData(String key){
		hashedMapcontainer.remove(key);
		
		totalObj--;
	}
}
