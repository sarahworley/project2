package edu.ncsu.csc216.travel.model.office;
/** Exception thrown when there is an attempt 
 * to add a duplicate client to the list of clients.
 * @author sarahworley
 *
 */
public class DuplicateClientException extends Exception {
	
	

	/**
	 * DuplicateClientException constructor with a String specifying a message for the
	 * Exception object
	 * 
	 * @param message exception message to be passed along to the parent
	 */
	public DuplicateClientException(String message) {
		super(message);

	}

	/**
	 * DuplicateClientException constructor with the default error message
	 * 
	 */
	public DuplicateClientException() {
		this("Client is already registered.");
	}


}
