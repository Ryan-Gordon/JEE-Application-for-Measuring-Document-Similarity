package ie.gmit.sw;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import ie.gmit.sw.database.Db4oController;
import ie.gmit.sw.document.DocumentJob;

/**
 * Processor is one of the main classes for the application. 
 * It holds a reference to the inQueue, outQueue, threadPool
 * 
 * It also holds an inner class which contains variables taken from the web.xml
 * @author ryangordon
 *
 */
public class Processor {
	//Queues 
    private static ArrayBlockingQueue<DocumentJob> inQueue;
    private static ConcurrentHashMap<Integer, Result> outQueue;
    //ThreadPool which will contain out workers
    private static ThreadPoolFactory workers;
    //Volatile variables for concurrent access by threads
    private static volatile int jobNumber = 0;
    private static volatile int workerNumber = 0;

    	/**
    	 * Initialize variables in a thread-safe way
    	 * Starts the threadpoolfactory and sets its size
    	 */
    public static synchronized void init() {
	
	try {
	    inQueue = new ArrayBlockingQueue<>(Config.numOfWorkers);
	    outQueue = new ConcurrentHashMap<>();
	    workers = new ThreadPoolFactory(Worker.class, Config.numOfWorkers);
	   
	} catch (Exception e) {
		System.out.println("Problem initialising the processor"+e.getMessage());
	}
    }

    /**
     * Gets the inQueue.
     * @return
     */
    public static ArrayBlockingQueue<DocumentJob> getInQueue() {
	return inQueue;
    }

    /**
     * Get a reference to the inqueue
     * @return
     */
    public static ConcurrentHashMap<Integer, Result> getOutQueue() {
	return outQueue;
    }
    
    /**
     * Get a reference to the jobNumber. 
     * The jobNumber is incremented every time this method is called.
     * Method is synchronized to try and make it thread safe
     * @return
     */
    public static synchronized int getJobNumber() {
	jobNumber++;
	return jobNumber;
    }

    /**
     * Get a reference to the workerNumber. 
     * The jobNumber is incremented every time this method is called.
     * Method is synchronized to try and make it thread safe
     * @return
     */
    public static synchronized int getWorkerNumber() {
	workerNumber++;
	return workerNumber;
    }
    
    /**
     * Takes in a job from the processor and starts the worker.
     * A worker is added to the pool and the job added to the inqueue.
     * 
     * The job will be handled by the worker added or any other available worker.
     * @param job
     */
    public static void processJob(DocumentJob job)
    {
    	System.out.println("Now Processing the job ");
	try {
	    workers.addWorker(); //Add worker to the pool
	    inQueue.put(job); // Add job to the queue
	} catch (Exception e) {
		System.out.println("Error adding Worker to ThreadPool: " + e.getMessage());
		
	    
	}//catch
    }
    
    /**
     * Delegate method to Config class
     * @return
     */
    public static int getHashFunctionsSize() {
	return Config.hashFunctionsSize;
    }
    
    /**
     * Delegate method to Config class
     * @return
     */
    public static int getShingleSize() {
        return Config.shingleSize;
    }
    /**
     * Delegate method to Config class
     * @return
     */
    public static Db4oController getDb() {
	return Config.db;
    }
    /**
     * Used to close the threadpoolfactory when the class goes out of scope.
     */
    public static void shutdown() {
	workers.shutDown();
    }

    @Override
    protected void finalize() throws Throwable {
    	
    //Shutdown the threadpool when this class goes out of scope.
	shutdown();
	super.finalize();
    }
    /**
     * Inner Class used to hold some configuration variables taken from the web.xml.
     * 
     * @author ryangordon
     *
     */
    public static class Config
    {
	private static int numOfWorkers;
	private static int hashFunctionsSize;
	private static int shingleSize;
	private static Db4oController db;
	
	
	/**
	 * Sets the number of workers to be used by the thread pool
	 * @param numOfWorkers
	 */
	public static void setNumOfWorkers(int numOfWorkers) {
	    Config.numOfWorkers = numOfWorkers;
	}
	
	/**
	 * Set the 
	 * @param hashFunctions
	 */
	public static void setHashFunctions(int hashFunctions) {
	    Config.hashFunctionsSize = hashFunctions;
	}
	
	/**
	 * Set number of words for shingle. Important for comparison.
	 * The bigger the shingle size the less comparisions that will be made.
	 * @param shingleSize
	 */
	public static void setShingleSize(int shingleSize) {
	    Config.shingleSize = shingleSize;
	}
	
	/**
	 * Set the db to an instance of the DB Controller.
	 * @param filename
	 * @param password
	 */
	public static void setDb(String filename, String password) {
	    Config.db = new Db4oController(filename, password);
	}
	
	
    }

}
