package collectionTest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;

public class sortOutData {

	public static hashedMapobj map  = null;
	public static TreeMap<String,Long> sorted_map = null;
	public static PrintWriter prw = null;
	public static String[] keyList;
    public static void getDataToSort(hashedMapobj args,String filename) throws IOException {

        map = args;
        prw = new PrintWriter(new FileWriter(new File(filename)));
        ValueComparator bvc =  new ValueComparator(map);
        sorted_map = new TreeMap<String,Long>(bvc);
        //System.out.println("unsorted map: "+map);
        sorted_map.putAll(map.hashedMapcontainer);
       // Collections container =  (Collections) map.getCollections();
        //Collections.sort(container, new sortData());
      //  prw.println(sorted_map);
        int k = 0; 
        System.out.println(" Size of the sortedMap: "+sorted_map.size());
        keyList = new String[sorted_map.size()+1];
        for(Map.Entry<String,Long> entry : sorted_map.entrySet()) {
        	  
        	  //System.out.println(" Iteration steps: "+ k);
        	  String key = entry.getKey();
        	  keyList[k++]=key;
        	  Long value = entry.getValue();
        	//  System.out.println("index No" + k +" "+key + " => " + value);
        	  prw.println(key + " => " + value);
        }
        
       prw.close();
    }
}
class sortData implements Comparator<Long>{
	public sortData(){
		
	}
	 @Override
	    public int compare(Long o1, Long o2) {
	        return (o1 > o2 ? -1 : (o1 == o2 ? 0 : 1));
	    }
}
class ValueComparator implements Comparator<String> {

    hashedMapobj base;
    public ValueComparator(hashedMapobj map) {
        this.base = map;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.    
    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}