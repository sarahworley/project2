/**
 * 
 */
package edu.ncsu.csc216.travel.model.office;

import static org.junit.Assert.*;

import org.junit.Test;


/** Test for DuplicateClientExceptionTest
 * @author sarahworley
 *
 */
public class DuplicateClientExceptionTest {

	

	/**
	 * Test method for {@link edu.ncsu.csc216.travel.model.office.DuplicateClientException#DuplicateClientException(java.lang.String)}.
	 */
	@Test
	public void testDuplicateClientExceptionString() {
		DuplicateClientException ce = new DuplicateClientException("Custom exception message");
	    assertEquals("Custom exception message", ce.getMessage());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.travel.model.office.DuplicateClientException#DuplicateClientException()}.
	 */
	@Test
	public void testDuplicateClientException() {
		DuplicateClientException ce = new DuplicateClientException();
	    assertEquals("Client is already registered.", ce.getMessage());
	}

}
