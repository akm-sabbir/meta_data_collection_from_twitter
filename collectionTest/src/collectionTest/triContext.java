package collectionTest;

public class triContext {
	private triContext(){
		triRoot = new trieString();
		initialize(triRoot);
	}
	public trieString triRoot;
	public static triContext createTricontext(){
	  return new triContext();
	}
	public void initialize(trieString  vertex){
		vertex.word = 0;
		vertex.prefix = 0;
		for (int i = 0; i < 150 - 'a' + 1; i++ )
			vertex.edge[i] = null;
	}
	trieString createVertex(){
		return new trieString();
	}
	public char leftmostChar(StringBuilder word){
		return word.charAt(0);
	}
	public StringBuilder cutleftmostChar(StringBuilder word){
		
		return word.deleteCharAt(0);
	}
	public boolean isEmpty(StringBuilder word){
		if(word.length() == 0)
			return true;
		else
			return false;
	}
	public void addWord(trieString vertex, StringBuilder word){
		if(isEmpty(word)){
			vertex.word = vertex.word + 1;
			return ;
		}else{
			
			vertex.prefix = vertex.prefix + 1;
			char c = leftmostChar(word);
			if(vertex.edge[c - 'a'] == null){
				vertex.edge[c - 'a'] = createVertex();
				initialize(vertex.edge[c]);
			}
			word = cutleftmostChar(word);
			addWord(vertex.edge[c - 'a'], word);
		}
		return;
	}
	public int countWord(trieString vertex,StringBuilder word){
		char c = leftmostChar(word);
		int returnVal;
		if(isEmpty(word))
			return vertex.word;
		else if(vertex.edge[c - 'a'] == null)
			return 0;
		else{
			word = cutleftmostChar(word);
			return returnVal = countWord(vertex.edge[c - 'a'], word);
		}
		
	}
	public int countPrefix(trieString vertex,StringBuilder word){
		char c = leftmostChar(word);
		int returnVal;
		if(isEmpty(word))
			return vertex.prefix;
		else if(vertex.edge[c - 'a'] == null)
			return 0;
		else{
			word = cutleftmostChar(word);
			return returnVal = countPrefix(vertex.edge[c - 'a'], word);
		}
	}
	public void inorderTraversal(trieString strRoot,StringBuilder str){
		
		for(int i = 0; i < 150 - 'a' + 1; i++){
			if(strRoot.edge[i] != null){
				str.append((char)(i+'a'));
				if(strRoot.word != 0)
					System.out.println("String : " + str + " Count : " + strRoot.word);
				else{
					inorderTraversal(strRoot.edge[i], str);
					str.deleteCharAt(str.length()-1);
				}
			}
		}
		return;
	}
}
