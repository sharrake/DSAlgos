package trees.tries;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Trie {

	///// Node Starts here
	public static class Node {
		private char content;
		private List<Node> children;
		private boolean isEndOfWord;
		private int numWordsWithPrefix;
		
		public Node(char c) {
			c = Character.toLowerCase(c);
			this.content = c;
			this.children = new LinkedList<Node>();
			this.isEndOfWord = false;
			this.numWordsWithPrefix = 0;
		}
		
		public boolean isLeafNode() {
			if(this.children.size() == 0) {
				return true;
			} else {
				return false;
			}
		}
		public char getContent() {
			return content;
		}

		public List<Node> getChildren() {
			return children;
		}
//
//		public void setChildren(List<Node> children) {
//			this.children = children;
//		}

		public void setContent(char content) {
			this.content = content;
		}

		public boolean isEndOfWord() {
			return isEndOfWord;
		}


		public void setEndOfWord(boolean isEndOfWord) {
			this.isEndOfWord = isEndOfWord;
		}


		public int getNumWordsWithPrefix() {
			return numWordsWithPrefix;
		}

		public void setNumWordsWithPrefix(int numWordsWithPrefix) {
			this.numWordsWithPrefix = numWordsWithPrefix;
		}


		public Node subNode(char c) {
			c = Character.toLowerCase(c);
			
			if(this.children != null) {
				for (Node child : this.children) {
					if(child.content == c) {
						return child;
					}
				}
			}
			
			return null;
		}
		
		public Node addSubNode(char c) {
			c = Character.toLowerCase(c);
			boolean isAdded = false;
			Node newNode = null;
			
			if(subNode(c) != null) {
				isAdded = false; // as the node already exists
			} else {
				ListIterator<Node> iter = children.listIterator();
				while (iter.hasNext()) {
					if(iter.next().getContent() > c) {
						iter.previous();
						newNode = new Node(c);
						iter.add(newNode);
						isAdded = true;
						break;
					}
				}
				
				if(!isAdded) {
					newNode = new Node(c);
					children.add(newNode);
				}
				
			}
			return newNode;
		}
		
		
		@Override
		public String toString() {
			String description = "Content = " + content + "\n";
			description += "isEndOfWord = " + isEndOfWord + "\n";
			description += "Prefixes = " + numWordsWithPrefix + "\n";
			description += "Children = ";
			
			ListIterator<Node> iter = children.listIterator();
			while (iter.hasNext()) {
				description += iter.next().content + ",";
			}
			
			return description;
		}
	}
	///// Node Ends here
	
	
	
	// Trie 
	private Node rootNode;
	
	//Construct a trie
	public Trie() {
		rootNode = new Node('\0');
	}

	public Node getRootNode() {
		return rootNode;
	}
	
	public void insert(String word) {
		
		Node currNode = rootNode;
		if( word.length() == 0) {
			currNode.setEndOfWord(true);
			return;
		}
		
		for(int i= 0 ; i < word.length() ;++i) {
			char curChar = word.charAt(i);
			
			Node subNode = currNode.subNode(curChar);
			
			if(subNode != null) {
				currNode = subNode;
			} else {
				currNode = currNode.addSubNode(curChar);
			}

			currNode.setNumWordsWithPrefix(currNode.getNumWordsWithPrefix()+1);
			//if last char
			if(i == word.length() -1) {
				currNode.setEndOfWord(true);
			}
		}
		
		
		
	}
	
	public boolean search(String word) {
		Node currNode = rootNode;
		boolean found = true;
		
		for (int i = 0 ; i < word.length() ;i++) {

			char curChar = word.charAt(i);
			Node subNode = currNode.subNode(curChar);
			
			if(subNode == null) {
				found = false;
				break;
			} else {
				currNode = subNode;
			}
			
		}

		// if curNode is eow or not and completed
		if(found && !currNode.isEndOfWord ) {
			found = false;
		}

		return found;
	}

//	public void delete(String word) {
//		
//	}
	
	public void display() {
		List<String> allStrings  = getAllSuffixes(this.rootNode);
		
		for (String word : allStrings) {
			System.out.println(word);
		}
	}
	
	public static List<String> getAllSuffixes(Node root) {
		
		List<String> allStrings = new ArrayList<String>();
		
		if(root.isEndOfWord()) {
			String str = Character.toString(root.getContent());
			allStrings.add(str);
			if(!root.isLeafNode()) {
				String curChar = Character.toString(root.getContent());
				ListIterator<Node> iter = root.getChildren().listIterator();
				while(iter.hasNext()) {
					List<String> suffices = getAllSuffixes(iter.next());
					allStrings.addAll(addPrefix(curChar, suffices));
				}
			}
		} else {
			String curChar = Character.toString(root.getContent());
			ListIterator<Node> iter = root.getChildren().listIterator();
			while(iter.hasNext()) {
				List<String> suffices = getAllSuffixes(iter.next());
				allStrings.addAll(addPrefix(curChar, suffices));
			}
		}
			
		return allStrings;
	}
	
	private static List<String> addPrefix(String prefix, List<String> suffices) {
		List<String> newStrings =  new ArrayList<String>();
		
		for (String suffix : suffices) {
			newStrings.add(prefix + suffix);
		}
		
		return newStrings;
	}

}
