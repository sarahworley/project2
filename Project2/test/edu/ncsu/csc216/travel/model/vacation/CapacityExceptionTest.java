package edu.ncsu.csc216.travel.model.vacation;




import static org.junit.Assert.*;


import org.junit.Test;



/**
 * Test for CapacityException
 * 
 * @author sarahworley
 *
 */
public class CapacityExceptionTest {

	

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.travel.model.vacation.CapacityException#CapacityException(java.lang.String)}.
	 */
	@Test
	public void testCapacityExceptionString() {
		CapacityException ce = new CapacityException("Custom exception message");
	    assertEquals("Custom exception message", ce.getMessage());
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.travel.model.vacation.CapacityException#CapacityException()}.
	 */
	@Test
	public void testCapacityException() {
		CapacityException ce = new CapacityException();
	    assertEquals("Not enough space in selected tour for this party.", ce.getMessage());
	}

}
