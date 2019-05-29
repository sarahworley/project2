/**
 * 
 */
package edu.ncsu.csc216.travel.model.office;

import static org.junit.Assert.*;

import org.junit.Test;


/** Test for DuplicateTourExceptionTest
 * @author sarahworley
 *
 */
public class DuplicateTourExceptionTest {

	

	/**
	 * Test method for {@link edu.ncsu.csc216.travel.model.office.DuplicateTourException#DuplicateTourException(java.lang.String)}.
	 */
	@Test
	public void testDuplicateTourExceptionString() {
		DuplicateTourException ce = new DuplicateTourException("Custom exception message");
	    assertEquals("Custom exception message", ce.getMessage());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.travel.model.office.DuplicateTourException#DuplicateTourException()}.
	 */
	@Test
	public void testDuplicateTourException() {
		DuplicateTourException ce = new DuplicateTourException();
	    assertEquals("Cannot add duplicate tour", ce.getMessage());
	}

}
