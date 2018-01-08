package ie.gmit.sw.document;

import java.util.Set;
import java.util.TreeSet;
/**
 * Document is a object model of the documents we will be processing in the application.
 * @author ryangordon
 *
 */
public class Document {
	

    // Document name
    private String title;
    // Set of minHash from every XOR hashFunction
    private Set<Integer> minHashes;
    // Set of random hashes. Must be identical in all documents compared!
    private Set<Integer> hashFunctions;

    public Document(String title) {
	super();
	this.title = title;
	minHashes = new TreeSet<>();
	hashFunctions = new TreeSet<>();
    }
    
    public Document(String title, Set<Integer> minHashes, Set<Integer> hashFunctions) {
	super();
	this.title = title;
	this.minHashes = minHashes;
	this.hashFunctions = hashFunctions;
    }
    /**
     * Getter for title
     * @return
     */
    public String getTitle() {
	return title;
    }
    /**
     * Getter for minHashes
     * @return
     */
    public Set<Integer> getMinHashes() {
	return minHashes;
    }
    /**
     * Setter for minHashes
     * @param minHashes
     */
    public void setMinHashes(Set<Integer> minHashes) {
	this.minHashes = minHashes;
    }
    /**
     * Getter for hashFunctions
     * @return
     */
    public Set<Integer> getHashFunctions() {
	return hashFunctions;
    }
    /**
     * Setter for hashFunctions
     * @param hashFunctions
     */
    public void setHashFunctions(Set<Integer> hashFunctions) {
	this.hashFunctions = hashFunctions;
    }
    /**
     * Returns the length of the hashFunctions Set
     * @return
     */
    public int getHashFunctionsSize() {
	return this.hashFunctions.size();
    }
    /**
     * Returns the length of the minHashes Set
     * @return
     */
    public int getMinHashesSize() {
	return this.minHashes.size();
    }
    /**
     * Overridden toString to show the title and minHashes
     * 
     * return String
     */
    @Override
    public String toString() {
	return String.format("Document title: %s MinHashes: %d", this.getTitle(),
		this.getMinHashesSize());
    }
}
