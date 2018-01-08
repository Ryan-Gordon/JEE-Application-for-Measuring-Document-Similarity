package ie.gmit.sw;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * ThreadPoolFactory - Created a Fixed Thread pool of Workers which will process tasks.
 * The pool has a fixed size, when the size is exceeded threads must wait (blocked) until there is space.
 * 
 * Workers execute tasks for document comparison.
 * @author ryangordon
 *
 */
public class ThreadPoolFactory {
    private ExecutorService executor;
    private Worker worker;
    /**
     * Constructor for the ThreadPoolFactory - Creates the thread pool of n size.
     * @param workerClass
     * @param n
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public ThreadPoolFactory(Class workerClass, int n) throws InstantiationException, IllegalAccessException {
    		executor = Executors.newFixedThreadPool(n, new ThreadFactory() {
    			
		    @Override
		    public Thread newThread(Runnable r) {
			return new Thread(r);
	    }
	});
	worker = (Worker) workerClass.newInstance();
    }
    /**
     * Create an instance of type Worker
     * add the worker to the executor and run
     */
    public void addWorker() 
    {
    		Worker newWorker = new Worker();
    		executor.execute(newWorker);
    }
    /**
     * Close the threadpool
     */
    public void shutDown() {
	executor.shutdown();
    }
    
    @Override
    protected void finalize() throws Throwable {
    	//Used to close the thread pool when class goes out of scope
	shutDown();
	super.finalize();
    }
}
