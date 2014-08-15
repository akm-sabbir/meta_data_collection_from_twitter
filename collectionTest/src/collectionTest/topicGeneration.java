package collectionTest;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

public class topicGeneration {
	
	public static PrintWriter pr ;
	public static File fileT;
	public static File fileOutput;
	public static BufferedReader br;
	public static int numberOfTopic;
	public static FileReader rReader;
	public static multiHashedMapObj multiMap;
	public static String fileName;
	public static void setNumberofTopic(int setData){
		numberOfTopic = setData;
	}
	public static int getTopicNumber(){
		return numberOfTopic;
	}
    public static void initialize(String fileN) throws FileNotFoundException{
    	fileName = fileN;
    	multiMap = new multiHashedMapObj();
    	
    //	fileOutput = new File("D:\\TwitterData\\topics"+ "output"+getTopicNumber()+".txt");
   
    	try {
    		fileT = new File(fileName);
        	rReader = new FileReader(fileT);
		//	pr = new PrintWriter(fileOutput);
			br = new BufferedReader(rReader);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	public static void topicClassification() throws IOException{
		String str ;
		int i = 0;
		while(i++ < 3)
			br.readLine();
		while((str = br.readLine()) != null){
			StringTokenizer st = new StringTokenizer(str," ");
			String[] strArr = new String[7];
			int j = 0;
			while(st.hasMoreElements())
				strArr[j++] = st.nextToken();
			//for(int k = 0; k < strArr.length; k++ )
			globalObjects gb = globalObjects.createObjNoParam();
			System.out.println(strArr[4]+"\t"+strArr[5]);
			gb.setString(strArr[4]);
			multiMap.put(strArr[5], gb);
		} 
		return;
	}
	
	public static void iterationData() throws FileNotFoundException{
		fileOutput = new File("D:\\TwitterData\\topics\\"+ "output"+getTopicNumber()+".txt");
		   
			pr = new PrintWriter(fileOutput);
	
		System.out.println(multiMap.getSize());
		for(int i = 0; i < getTopicNumber(); i++)
			pr.print("Topic#"+i+"\t");
		pr.println();
		//ArrayList<Iterator<Entry<String, Objects>>> lists = new ArrayList<Iterator<Entry<String, Objects>>>();
		ArrayList<Iterator<Objects>> lists = new ArrayList<Iterator<Objects>>();
		for(int i = 0; i < getTopicNumber(); i++){
			Set<Objects> tempSet = multiMap.getData((new Long(i)).toString());
			
			lists.add(tempSet.iterator());
		}
		while(true){
			int edges = 0;
			for(int i = 0 ; i < lists.size(); i++){
				Iterator<Objects> tempIt = lists.get(i);
				if(tempIt.hasNext()){
					Objects ob = tempIt.next();
					tempIt.remove();
					globalObjects gb = (globalObjects)ob;
					pr.print(gb.getString()+"\t");
				}else{
					pr.print("empty"+"\t");
					edges++;
				}
				
			
			}
			pr.println();
			if(edges == lists.size())
				break;
		}
		//while(!multiMap.isEmpty()){
		 /*Set<Map.Entry<String, Objects>> sets = multiMap.getSets();
		    Iterator<Entry<String, Objects>> i = sets.iterator();
		    while (i.hasNext()) {

		        Map.Entry<String, List> me = (Map.Entry) i.next();

		        for(int j = 0 ; j< me.getValue().size(); j++ )
		        {
		            System.out.println(me.getKey() +" : " +me.getValue().get(j));
		        }
		    }*/
		//}
	}
	public static void closeFiles(){
		try {
			rReader.close();
			pr.close();
			br.close();	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		
		}
		
	}
}
