package collectionTest;

import java.util.Enumeration;
import java.util.Hashtable;

public class hashedObject {

	public Hashtable<Long, objectDes> container = null;
	public int hashCount;
	public hashedObject(int size){
		
		container = new Hashtable<Long,objectDes>(size);
		hashCount = 0;
	}
	public Hashtable<Long,objectDes> getHashtable(){
		return container;
	}
	public int setHashdata(Long ob1,objectDes ob2){
		if(container.put(ob1, ob2)== null){
			hashCount++;
			return 1;
		}
		else
			return 0;
	}
	public boolean isContains(Long key){
		if(container.contains(key))
			return true;
		else
			return false;
	}
	public void clearContainer(){
		container.clear();
	}
	public objectDes getHashData(Long key){
		if(container.containsKey(key))
			return container.get(key);
		return null;
	}
	public Enumeration<objectDes> getEnum(){
		return container.elements();
	}
	public long getHashsize(){
		return container.size();
	}
}
