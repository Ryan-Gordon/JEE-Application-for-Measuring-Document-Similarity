package ie.gmit.sw;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import ie.gmit.sw.document.DocFacade;
import ie.gmit.sw.document.Document;
import ie.gmit.sw.document.DocumentJob;
/**
 * Worker is a Thread class implementing Runnable.
 * It is used to :
 * 1. Take in any pending jobs on the inQueue
 * 2. Break document into shingles 
 * 3. Compare document against stored documents.
 * 4. Store document
 * @author ryangordon
 *
 */
public class Worker implements Runnable{

	private static ArrayBlockingQueue<DocumentJob> inQueue;
    private static ConcurrentHashMap<Integer, Result> outQueue;
    private int workerNumber;
    private DocumentJob job;
    private Document document;
    private List<Document> comparisonDocs;
    private Result results;
    
    
    @Override
    public void run() {
    	//Get the queues and the workerNumber from the Processor before starting
	workerNumber = Processor.getWorkerNumber();
	inQueue = Processor.getInQueue();
	outQueue = Processor.getOutQueue();
	try {
	    job = inQueue.take(); // Take a job from the queue
	    document = new Document(job.getTitle()); //get document from the Job
	    
	    System.out.println(
		    String.format("Worker number %d start work on job number: %d", workerNumber, job.getJobNumber()));
	    //Instantiate our Facade for document processing
	    DocFacade minHash = new DocFacade(Processor.getDb());
	    try {
	    	
		comparisonDocs = minHash.retreiveDocuments(); //Get documents to compare
		
		Set<String> words = minHash.getWords(job.getDocument());
		Set<Integer> hashes = minHash.getHashes(words, Processor.getShingleSize());
		
		if (comparisonDocs.isEmpty()) {
		    document.setHashFunctions(minHash.getHashFunctions(Processor.getHashFunctionsSize()));
		} else {
		    document.setHashFunctions(comparisonDocs.get(0).getHashFunctions());
		}
		document.setMinHashes(minHash.getMinHashes(hashes, document.getHashFunctions()));
		
		//Initialize the Result class
		results = new Result(job.getJobNumber(), job.getTitle());
		//Add the results
		results.addResults(minHash.compareDocument(document, comparisonDocs));
		
		System.out.println("Putting job on out queue");
		outQueue.put(job.getJobNumber(), results);
		
		if (!minHash.isAlreadySaved())
		{
		    minHash.storeDocument(document);
		}
	    } catch (IOException e) {
	    		System.out.println("Error encountered +e.getMessage()");
	    }
	} catch (InterruptedException e) {
	    System.out.println(String.format("Worker number: %d processing job number: %d, Document: %s caused error: %s",
		    workerNumber, job.getJobNumber(), job.getTitle(), e.getMessage()));
	}
	/* Was used as a part of trying to make the Singleton pattern work for DB
	finally {
		try {
			finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
    }
    
}


