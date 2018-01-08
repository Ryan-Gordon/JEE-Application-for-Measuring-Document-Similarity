package ie.gmit.sw;

import java.util.HashMap;
import java.util.Set;
/**
 * Result Class is a model which holds details of the result from the comparison.
 * @author ryangordon
 */
public class Result {
	private int jobNumber; //jobNumber for this Result object
    private String title;//title for this Result object
    private HashMap<String, String> docsResults;//the results themselves for this Result object

    public Result(int jobNumber, String documentName) {
	super();
	this.jobNumber = jobNumber;
	this.title = documentName;
	docsResults = new HashMap<>();
    }
    /**
     * Gets the jobNumber
     * @return
     */
    public int getJobNumber() {
	return jobNumber;
    }
    /**
     * Gets the titles
     * @return
     */
    public String getTitle() {
	return title;
    }
    /**
     * Adds results to the map
     *  
     */
    public void addResults(HashMap<String, String> results) {
	docsResults.putAll(results);
    }
    /**
     * Gets the documents in the Map as a Set
     * @return Set
     */
    public Set<String> getDocuments() {
	return docsResults.keySet();
    }
    /**
     * Gets the amount of results
     * @return int
     */
    public int getResultsCount() {
	return docsResults.size();
    }

    /**
     * Takes in a title param and returns similarity results for this title.
     * @param title
     * @return String result
     */
    public String getResult(String title) {
	return docsResults.get(title);
    }

}
