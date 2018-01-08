package ie.gmit.sw.document;

import java.io.BufferedReader;

/**
 * Document Job is a model used to hold the jobNumber and document to be used by other classes.
 * @author ryangordon
 *
 */
public class DocumentJob{
	
	private int jobNumber;
    private String title;
    private BufferedReader document;

    public DocumentJob(int jobNumber, String title, BufferedReader document) {
	super();
	this.jobNumber = jobNumber;
	this.title = title;
	this.document = document;
    }
    /**
     * Gets the jobNumber for this job
     * @return
     */
    public int getJobNumber() {
	return jobNumber;
    }
    /**
     * Sets the jobNumber for this job
     * @param jobNumber
     */
    public void setJobNumber(int jobNumber) {
	this.jobNumber = jobNumber;
    }
    /**
     * Gets the title of the document for this job
     * @return
     */
    public String getTitle() {
	return title;
    }
    /**
     * Sets the title of this job
     */
    public void setTitle(String title) {
	this.title = title;
    }
    /**
     * Gets the document for this job
     */
    public BufferedReader getDocument() {
	return document;
    }
    /**
     * @param document sets the document for this Job
     */
    public void setDocument(BufferedReader document) {
	this.document = document;
    }
}
