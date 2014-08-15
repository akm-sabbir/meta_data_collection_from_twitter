package collectionTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class profileInfo {

	public static String fileName = "D:\\TwitterData\\userProfile1.txt";
	public static String rankInfo = "D:\\TwitterData\\rankInfo.txt";
	public static FileReader fileReader = null;
	public static BufferedReader bufferFileReader = null;
	public static hashedMapobj hashmapContainer = null;
	public static File fileobj = new File(fileName);
	public static File fileranker = new File(rankInfo);
	public static PrintWriter fileWriter =null;
	public static Pattern patNum = Pattern.compile("[0-9]+");
	public static Pattern patStr = Pattern.compile("[a-zA-Z]+");
	public static Pattern patSpec = Pattern.compile("^[^<>%$?\\//-_:]*$");
	public profileInfo(hashedMapobj hashmaper){
		hashmapContainer = hashmaper;
		
	}
	public static void init(){
		try{
			fileWriter = new PrintWriter(fileranker);
			fileReader = new FileReader(fileobj);
			bufferFileReader = new BufferedReader(fileReader);
			}catch(FileNotFoundException ex){
			 System.err.println(ex.getMessage());
		}
		return;
	}
	public static void inception(){
		String tempStr;
		String prevStr;
		String currStr;
		String nextStr;
		try {
			init();
			//while(true){
			//prevStr = bufferFileReader.readLine();
				while((prevStr = bufferFileReader.readLine()) != null){
					currStr = prevStr;
					String strVal = currStr.split("\t")[0].trim();
					while(true){
						Matcher matchSpec = patSpec.matcher(strVal);
						Matcher matchStr = patStr.matcher(strVal);
						if(matchSpec.find() == true || matchStr.find() == true){
							nextStr = bufferFileReader.readLine();
							currStr =currStr + " "+ nextStr;
							nextStr = bufferFileReader.readLine();
							strVal  = nextStr.split("\t")[0].trim();
						}else{
							prevStr = currStr;
							break;
						}
					}
					String[] arrStr = prevStr.split("\t");
				//	hashmapContainer.setData(arrStr[0], arrStr[2]);
				}
			//}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
}
