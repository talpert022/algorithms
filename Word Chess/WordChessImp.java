import java.io.*;
import java.util.*;

public class WordChessImp implements WordChess{
	
	private static final String[] No_Path = {};
	
	/* 
		This method creates the graph, which contains all of the words in the 
		dictionary that have the length of the startword, and all of the words
		that each vertex is linked to 
	*/
	public Graph createGraph(String[] dictionary, String startWord) {
		
		int length = startWord.length();
		List<String> lines = new ArrayList<String>();
		Vertex v1, v2;
		
		for(int i = 0; i < dictionary.length; i++){
			if (dictionary[i].length() == length)
				lines.add(dictionary[i]);
		}
		
		// Adds all words of the correct length
		Graph adjWords = new Graph();
		for (int i = 0; i<lines.size(); i++){
			adjWords.addVertex(lines.get(i));
		}
		// Adds connected words for each word in the graph
		for(int i = 0; i<adjWords.getSize(); i++){
			v1 = adjWords.getVertex(i);
			List<Vertex> vis = new ArrayList<Vertex>();
			for(int j = 0; j<lines.size(); j++){
				v2 = adjWords.getVertex(j);
				if(is_Valid(v1.getWord(), v2.getWord()) && !vis.contains(v2)){
					adjWords.addEdge(v1,v2);
					vis.add(v2);
				}	
			}
		}	
		return adjWords;
	}
	
	/*
		Function to determine whether a word differs by one character 
		from another word
	*/
	private boolean is_Valid(String word1, String word2)
	{
		int same = 0;
		for(int i = 0; i < word1.length(); i++) {
			if(word1.charAt(i) != word2.charAt(i))
				same++;
		}
		if (same == 1)
			return true;
		else
			return false;
	}
	//reverses array, used in line 107 of final path
	private String[] reverseArray(String[] arr)
	{
		for (int i = 0; i < arr.length/2; i++)
		{
			String temp = arr[i];
			arr[i] = arr[arr.length-i-1];
			arr[arr.length-i-1] = temp;
		}
		return arr;
	}
	/*
		findPath uses a breadth first search to determine 
		the shortest path between two words 
	*/
	public String[] findPath(String[] dictionary, String startWord, String endWord){
		
		Graph adjWords = new Graph();
		adjWords = createGraph(dictionary, startWord);
		
		Map<Vertex, Boolean> vis = new HashMap<Vertex, Boolean>();
		Map<Vertex, Vertex> prev = new HashMap<Vertex, Vertex>();
		
		List<String> path = new LinkedList<String>();
		Queue<Vertex> q = new LinkedList<Vertex>();
		Vertex current = adjWords.getVertex(startWord);
		Vertex finish = adjWords.getVertex(endWord);
		q.add(current);
		vis.put(current, true);
		while(!q.isEmpty()){
			current = q.remove();
			if (current.same(finish))
				break;
			else{
					for(Vertex v : current.getList()){
						if(!vis.containsKey(v)){
							q.add(v);
							vis.put(v, true);
							prev.put(v, current);
					}
				}
			}
		}
		if (!current.same(finish)){
			return No_Path;
		}
		for(Vertex v = finish; v != null; v = prev.get(v)){
			path.add(v.getWord());
		}
		String[] final_path = path.toArray(new String[path.size()]);
		final_path = reverseArray(final_path);
		return final_path;
	}
/*
	Graph class is a list of Vertexes, represeted in an ArrayList
*/
class Graph {
	
	private List<Vertex> adjVertices;
	
	Graph(){
		this.adjVertices = new ArrayList<Vertex>();
	}
	
	// getVertex has two methods, returning the vertex in the graph given 1. the word variable 
	// in the vertex, and 2. its position in the adjVertices array
	
	Vertex getVertex(String word){
		for(int i = 0; i<adjVertices.size(); i++){
			if ((adjVertices.get(i).getWord()).equals(word))
				return adjVertices.get(i);
		}
		return new Vertex("A");
			
	}
	
	Vertex getVertex(int i){
		return adjVertices.get(i);
	}
	
	void addVertex(String word) {
		adjVertices.add(new Vertex(word));
	}
	
	// Adds an edge between two vertexes
	
	void addEdge(Vertex v1, Vertex v2) {
		int index1 = adjVertices.indexOf(v1);
		adjVertices.get(index1).getList().add(v2);
	}
	// returns the size of the graph
	int getSize(){
		return adjVertices.size();
	}
	// returns adjacent vertices of a particular vertex
	List<Vertex> getAdjVertices(Vertex v) {
		int index = adjVertices.indexOf(v);
		return adjVertices.get(index).getList();
	}
		
}

/*
	A vertex has two variable, the word it represents, and all words linked to it,
	that differ by one character
*/

class Vertex {
	String word;
	List<Vertex> adjList;
	//constructor 
	Vertex(String word1) {
		this.word = word1;
		this.adjList = new ArrayList<Vertex>();
	}

	public String getWord(){
		return this.word;
	}
	
	public List<Vertex> getList(){
		return this.adjList;
	}
	// compares if two vertexes are the same 
	public boolean same(Vertex v){
		if ((this.word).equals(v.getWord()))
			return true;
		else
			return false;
	}
}
}
