package edu.ncsu.csc216.travel.model.office;
/** Exception thrown when there is an attempt to 
 * add a duplicate tour to the list of tours.
 * @author sarahworley
 *
 */
public class DuplicateTourException extends Exception {
	
	

	/**
	 * eclipse generated
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * DuplicateTourException constructor with a String specifying a message for the
	 * Exception object
	 * 
	 * @param message exception message to be passed along to the parent
	 */
	public DuplicateTourException(String message) {
		super(message);

	}

	/**
	 * DuplicateTourException constructor with the default error message
	 * 
	 */
	public DuplicateTourException() {
		this("Cannot add duplicate tour");
	}

}
