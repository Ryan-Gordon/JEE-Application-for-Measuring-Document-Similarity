package ie.gmit.sw.database;

import java.io.IOException;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.query.Query;
import com.db4o.ta.TransparentActivationSupport;
import com.db4o.ta.TransparentPersistenceSupport;

import xtea_db4o.XTeaEncryptionStorage;
/**
 * 
 * Allows for a connection to the  Embedded DB4o db.
 * 
 * Uses XTEA Encryption for the password to the DB.
 * I attempted to implement a Singleton for this class however I encountered problems when 
 * accessing the db from multiple threads. I eventually dropped this pattern to retain functionality.
 * 
 * @author ryangordon 
 *
 */
public class Db4oHashStore {
		//Used for trying to adopt Singleton pattern
		private static volatile Db4oHashStore instance;
		private static Object mutex = new Object();
		
		//DB object
		private ObjectContainer db;
		//Params for DB
	    private String fileName;
	    private String password;

	    public Db4oHashStore(String fileName, String password) throws IOException{

		this.fileName = fileName;
		this.password = password;
		
		// Configurations
		EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		config.common().add(new TransparentActivationSupport());
		config.common().add(new TransparentPersistenceSupport());
		config.common().updateDepth(7);

		// Use the XTea lib for encryption.
		config.file().storage(new XTeaEncryptionStorage(this.password));

		// Open a local database
		this.db = Db4oEmbedded.openFile(config, this.fileName);
	    }
	    /**
	     * Attempt to get a handle on an instance of the class.
	     * synchronized mutex lock is used to try and make it thread safe
	     * @param fileName
	     * @param password
	     * @return
	     * @throws IOException
	     */
	    public static Db4oHashStore getInstance(String fileName, String password) throws IOException {
	    
	    	Db4oHashStore result = instance;
	    		if (result == null) {
	    			synchronized (mutex) {
	    				result = instance;
	    				if (result == null)
	    					instance = new Db4oHashStore(fileName, password);
	    			}
	    		}
			return instance;
		}
	    
	    /**
	     * get all objects from the db of the provided Class type
	     * @param objectClass
	     * @return a Set of objects of type <T>
	     */
	    @SuppressWarnings("rawtypes")
	    public ObjectSet<Object> getObjects(Class objectClass) {
		Query query = this.db.query();
		query.constrain(objectClass);
		return query.execute();
	    }

	    /**
	     * Attempts to store the object in the DB
	     * Then commits the change if any
	     * @param object
	     */
	    public void storeObject(Object object) {
	    		this.db.store(object);
	    		this.db.commit();
	    }

	    /**
	     * Attempts to manually close the DB connection.
	     */
	    public void closeDb() {
		this.db.close();
	    }

	    @Override
	    protected void finalize() throws Throwable {
	    	//Close db when class goes out of scope
		closeDb();
		super.finalize();
	    }

}
