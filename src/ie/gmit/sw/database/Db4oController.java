package ie.gmit.sw.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.db4o.ObjectSet;
import com.db4o.ext.Db4oException;

import ie.gmit.sw.document.Document;


/**
 * Db4o Controller is used to encapsulate saving and retrieving objects from the Db4o Database.
 * Saving and retrieving is done by creating an instance of HashStore and delegating tasks to it.
 * 
 * @author ryangordon
 *
 */
public class Db4oController {
	String fileName;
    String password;

    public Db4oController(String fileName, String password) {
	super();
	this.fileName = fileName;
	this.password = password;
    }

    /**
     * Get all Objects from the db of type Document.
     * 
     * As a part of trying to implement a singleton I tried to finalize the db4o object 
     * to allow other threads to get an instance of Db4o
     * 
     * @return ArrayList of documents
     * @throws IOException
     */
    public List<Document> getDocuments() throws IOException {
	List<Document> documents = new ArrayList<>();
	try {
	    Db4oHashStore db4o = new Db4oHashStore(fileName, password);
	    ObjectSet<Object> results = db4o.getObjects(Document.class);
	    //Iterate over the set and try to add a casted instance of the object to the ArrayList
	    for (Object document : results) {
		documents.add((Document) document);
	    }
	    db4o.closeDb();
	    /*
	    try {
			db4o.finalize();
		} catch (Throwable e) {
			
			e.printStackTrace();
		}
		*/
	   
	} catch (Db4oException db4oExp) {
		System.out.println("Db4o Error: " + db4oExp.getMessage());
		db4oExp.printStackTrace();
	}//catch
	
	return documents;
    }//getDocuments
    /**
     * Gets a handle on the DB through the DB4o object using credentials. 
     * Attempts to store a document in the db and close the connection.
     * 
     * As a part of trying to implement a singleton I tried to finalize the db4o object 
     * to allow other threads to get an instance of Db4o
     * @param document
     * @throws IOException
     */
    public void storeDocument(Document document) throws IOException {
	try {
	    Db4oHashStore db4o = new Db4oHashStore(fileName, password);
	    db4o.storeObject(document);
	    db4o.closeDb();
	    System.out.println("Document saved.");
	    /*
	    try {
			db4o.finalize();
		} catch (Throwable e) {
			
			e.printStackTrace();
		}//catch
		*/
	} catch (Db4oException db4oExp) {
		System.out.println("Db4o Error: " + db4oExp.getMessage());
		db4oExp.printStackTrace();
	}//catch
    }//storeDocument
}


