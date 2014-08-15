package collectionTest;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

import org.apache.commons.collections4.MultiMap;

import com.google.common.collect.*;
public class multiHashedMapObj extends Objects{
   public HashMultimap<String, Objects> multihashmap;
   
   private String fileName;
   public multiHashedMapObj(){
	   multihashmap  = HashMultimap.create();
	   fileName = null;
   }
   public void put(String key, Objects value){
	   multihashmap.put(key, value);
	   return;
   }
   public void setFileName(String fileNames){
	   this.fileName = fileNames;
	   return;
   }
   public String getFileName(){
	   return this.fileName;
   }
   public boolean isContain(String key){
	   
	   if(multihashmap.containsKey(key))
		   return true;
	   return false;
   }
   public  boolean isContainVal(Objects value){
	   if(multihashmap.containsValue(value))
		   return true;
	   return false;
   }
   public boolean isEmpty(){
	   if(multihashmap.isEmpty())
		   return true;
	   return false;
   }
   public boolean removeData(String key){
	   if(isContain(key)){
		   multihashmap.removeAll(key);
		   return true;
	   }
	   return false;
   }
   public boolean removeDataKeyVal(String key,Objects value){
	   if(multihashmap.containsEntry(key, value)){
		   multihashmap.remove(key, value);
		   return true;
	   }
	   return false;
   }
   public Set<Objects> getData(String key){
	 
	  return  multihashmap.get(key);
   }
   public int getSize(){
	   return multihashmap.size();
   }
   public Set<Map.Entry<String, Objects>> getSets(){
	   return multihashmap.entries();
   }
   public void print(){
	   
	   for(Map.Entry<String, Objects> em : multihashmap.entries()){
		   //System.out.print(em.getKey() );
		   ((globalObjects)em.getValue()).print();
	   }
   }
   public ArrayList<String> getKeys(){
	   ArrayList<String> arr = new ArrayList<String>(multihashmap.size());
	   for(Map.Entry<String, Objects> em : multihashmap.entries()){
		   arr.add(em.getKey());
	   }
	   return arr;
   }
}
