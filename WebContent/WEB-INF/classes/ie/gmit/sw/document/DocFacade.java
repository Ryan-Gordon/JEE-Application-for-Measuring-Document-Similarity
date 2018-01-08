package ie.gmit.sw.document;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import ie.gmit.sw.database.Db4oController;
/**
 * DocFacade is a class used for 2 purposes.
 * 1. The storage and retrevial of documents from the DB
 * 2. Delegating document operations to an instance of DocumentParser
 * 
 * @author ryangordon
 *
 */
public class DocFacade {

    private Db4oController db;
    private DocumentParser pd;

    public DocFacade(Db4oController db) {
	this.db = db;
	pd = new DocumentParser();
    }

    /**
     * Retreive all the Document.class objects from database and process them into List.
     * @return List of Document objects retrieved from database via D4oController.
     * @throws IOException 
     */
    public List<Document> retreiveDocuments() throws IOException {
	return this.db.getDocuments();
    }
    
    /**
     * Store a Document object to database via Db4Controller class
     * @param document Document object to be saved, uploaded and processed document from UploadHandler servlet
     * @throws IOException 
     */
    public void storeDocument(Document document) throws IOException {
	this.db.storeDocument(document);
    }

    /**
     * Process BufferedReader document into separate words
     * @param document File submitted to UploadHandler servlet
     * @return Set of document words separated by space (" ") 
     * @throws IOException
     */
    public Set<String> getWords(BufferedReader document) throws IOException {
	return this.pd.getWords(document);
    }

    /**
     * Process Set of Words into Set of Hash Codes.
     * @param words Set of document words
     * @param shingleSize Integer. Number of words per shingle. 
     * @return Set of hash codes for every shingle in the document
     */
    public Set<Integer> getHashes(Set<String> words, int shingleSize) {
	return this.pd.getHashes(words, shingleSize);
    }

    /**
     * Get hashFunctions, 
     * @param numOfHashes Integer. Size of the Hash function set.
     * @return hashFunctions Set of size numOfHashes of randomly generated Integers.
     */
    public Set<Integer> getHashFunctions(int numOfHashes) {
	return pd.getHashFunctions(numOfHashes);
    }

    /**
     * Generate Set of Integers representing hash code with minimum value for every XOR operation,
     * stores only one minimum value hash code for every hashFunction. This is set by shingles value in web.xml
     * When Set of hash code is generated from words a hash code with minimum value is stored separately.
     * This saves O(n) for looping over Set again. 
     * @param hashes Set of integers/hash codes for every word in the document
     * @param hashFunctions Randomly generated integers. MUST be same for every document for the comparing to work.
     * @return Set of Integers, the hash code representation of the document. This is how document content is stored.
     */
    public Set<Integer> getMinHashes(Set<Integer> hashes, Set<Integer> hashFunctions) {
	Set<Integer> minHashes = pd.getMinHashes(hashes, hashFunctions);
	minHashes.add(pd.getMinHash());
	return minHashes;
    }
    
    /**
     * Method to compare document (Set of hashes) against the rest of the documents already stored in the database.
     * @param document Uploaded Document object in UploadHandler servlet
     * @param documents Set of Document objects, retrieved from persistent storage.  
     * @return Results object populated with HashMap of document to value of percentage similarity between uploaded document and document form Set of already stored documents
     */
    public HashMap<String, String> compareDocument(Document document, List<Document> documents) {
	return pd.compareDocument(document, documents);
    }

    /**
     * When comparing document against the rest of documents, boolean is turned true, if document has a counterpart
     * with the same name and 100 % similarity already stored inside database. This is to prevent storing duplicate documents.
     * @return Boolean that indicates if uploaded document with the same name and 100% similarity is already stored in database.
     */
    public boolean isAlreadySaved() {
	return pd.isAlreadySaved();
    }

}


