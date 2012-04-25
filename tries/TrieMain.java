package trees.tries;

import trees.tries.Trie.Node;

public class TrieMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Trie trie = new Trie();
		trie.insert("amy");
		trie.insert("ann");
		trie.insert("emma");
		trie.insert("emmana");
		trie.insert("rob");
		trie.insert("roger");
		trie.insert("rogers");
		
		
		trie.display();
		
		if(trie.search("riku")) {
			System.out.println("Found riku");;
		} else {
			System.out.println("riku not found");
		}
		
		if(trie.search("amy")) {
			System.out.println("Found amy");;
		} else {
			System.out.println("amy not found");
		}
		
	}

}
