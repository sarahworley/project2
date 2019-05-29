/**
 * 
 */
package edu.ncsu.csc216.travel.model.participants;


import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.travel.model.participants.Client;
import edu.ncsu.csc216.travel.model.vacation.LandTour;
import edu.ncsu.csc216.travel.model.vacation.Reservation;
import edu.ncsu.csc216.travel.model.vacation.Tour;


/** Test for Client
 * @author sarahworley
 *
 */
public class ClientTest {
	Client c;
	Client c2;
	Client c3;
	LocalDate s;
	Tour t;
	Tour t1;
	Tour t2;
	

	/**set up
	 * @throws java.lang.Exception throws
	 */
	@Before
	public void setUp() throws Exception {
		c = new Client("a", "b");
		s = LocalDate.of(2019, 3, 27);
		t = new LandTour("Land tour 1", s, 2, 200, 15);
		t1 = new LandTour("Land tour 2", s, 2, 200, 15);
		t2 = new LandTour("Land tour 3", s, 2, 200, 15);
		c2 = new Client("a", "b");
		
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.travel.model.participants.Client#Client(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testClient() {
		new Client("a", "b");
		assertEquals("a", c.getName());
		assertEquals("b", c.getContact());
		assertEquals(c.hashCode(), c.hashCode());
		try {
			c3 = new Client(null, "b");
			fail();
		} catch (IllegalArgumentException e) {
			//nope
		}
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.travel.model.participants.Client#getReservation(int)}.
	 */
	@Test
	public void testGetReservation() {
		Reservation r = new Reservation(t, c, 1);
		c.addReservation(r);
		assertEquals(r, c.getReservation(0));
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.travel.model.participants.Client#getNumberOfReservations()}.
	 */
	@Test
	public void testGetNumberOfReservations() {
		Reservation r = new Reservation(t, c, 2);
		c.addReservation(r);
		assertEquals(1, c.getNumberOfReservations());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.travel.model.participants.Client#totalReservationCost()}.
	 */
	@Test
	public void testTotalReservationCost() {
		Reservation r = new Reservation(t, c, 2);
		c.addReservation(r);
		assertEquals(400, c.totalReservationCost(), 0);
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.travel.model.participants.Client#addReservation(edu.ncsu.csc216.travel.model.vacation.Reservation)}.
	 */
	@Test
	public void testAddReservation() {
		Reservation r = new Reservation(t, c, 1);
		c.addReservation(r);
		assertEquals(r, c.getReservation(0));
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.travel.model.participants.Client#cancelReservation(edu.ncsu.csc216.travel.model.vacation.Reservation)}.
	 */
	@Test
	public void testCancelReservation() {
		Reservation r = new Reservation(t, c, 1);
		Reservation r1 = new Reservation(t1, c, 1);
		Reservation r2 = new Reservation(t2, c, 1);
		c.addReservation(r);
		c.addReservation(r1);
		c.addReservation(r2);
		assertEquals(3, c.getNumberOfReservations());
		c.cancelReservation(r);
		assertEquals(2, c.getNumberOfReservations());
		try {
			c.getReservation(2);
			fail();
		} catch (IllegalArgumentException e) {
			// should reach
		}
	}

	/**
	 * Test method for 
	 */
	@Test
	public void testListOfReservations() {
		Reservation r = new Reservation(t, c, 1);
		c.addReservation(r);
		assertEquals(r.displayReservationTour(), c.listOfReservations()[0]);
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.travel.model.participants.Client#summaryInfo()}.
	 */
	@Test
	public void testSummaryInfo() {
		assertEquals("a (b)", c.summaryInfo());
	}
	
	
	/**
	 * Tests equals
	 */
	@Test
	public void testEquals() {
		assertTrue(c.equals(c2));
		
	}

}
