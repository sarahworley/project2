package edu.ncsu.csc216.travel.model.vacation;

/**Exception thrown when there is an attempt to create a reservation 
 * that would fill a tour over its capacity.
 * @author sarahworley
 *
 */
public class CapacityException extends Exception {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * CapacityExceptio constructor with a String specifying a message for the
	 * Exception object
	 * 
	 * @param message exception message to be passed along to the parent
	 */
	public CapacityException(String message) {
		super(message);

	}

	/**
	 * CapacityException constructor with the default error message
	 * 
	 */
	public CapacityException() {
		this("Not enough space in selected tour for this party.");
	}


}
