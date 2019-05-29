/**
 * 
 */
package edu.ncsu.csc216.travel.model.vacation;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.travel.model.participants.Client;

/**
 * Test for reservation
 * 
 * @author sarahworley
 *
 */
public class ReservationTest {
	
	Client c;
	Client c2;
	Client c3;
	LocalDate s;
	Tour t;
	Tour t1;
	Tour t2;

	/**
	 * set up
	 * 
	 * @throws java.lang.Exception
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
	 * Test method for
	 * {@link edu.ncsu.csc216.travel.model.vacation.Reservation#Reservation(edu.ncsu.csc216.travel.model.vacation.Tour, edu.ncsu.csc216.travel.model.participant.Client, int)}.
	 */
	@SuppressWarnings("unused")
	@Test
	public void testReservationTourClientInt() {
		Reservation r = new Reservation(t, c, 1);
		assertEquals(c, r.getClient());
		assertEquals(t, r.getTour());
		assertEquals(250, r.getCost(), 0);
		
		Reservation r1 = new Reservation(t, c, 1, 1);
		
		try {
			Reservation r3 = new Reservation(t, c, 1, 1000000);
			fail();
			
		} catch (IllegalArgumentException e) {
			//nope
		}
	}

	
	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.travel.model.vacation.Reservation#cancel()}.
	 */
	@Test
	public void testCancel() {
		Reservation r = new Reservation(t, c, 1);
		Reservation r2 = new Reservation(t1, c, 1);
		//r.cancel();
		
		try {
			t.addOldReservation(r);
			t1.addOldReservation(r2);
			
		} catch (CapacityException e) {
			fail();
		}
		
		r.cancel();
		assertEquals(1 , c.getNumberOfReservations());
		
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.travel.model.vacation.Reservation#displayReservationTour()}.
	 */
	@Test
	public void testDisplayReservationTour() {
		Reservation r = new Reservation(t, c, 1);
		assertEquals(r.getConfirmationCode() + " " + r.getNumInParty() + " " + t.summaryInfo() , r.displayReservationTour());
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.travel.model.vacation.Reservation#displayReservationClient()}.
	 */
	@Test
	public void testDisplayReservationClient() {
		Reservation r = new Reservation(t, c, 1);
		assertEquals(r.getConfirmationCode() + " " + r.getNumInParty() + " " + c.summaryInfo(), r.displayReservationClient());
	}
	
	/**
	 * Tetss hashcode
	 */
	@Test
	public void testHash() {
		Reservation r = new Reservation(t, c, 1);
		//Reservation r1 = new Reservation(t, c, 1, 1);
		assertEquals(r.hashCode(), r.hashCode());
		
	}
	
	/**
	 * tests equals
	 */
	@Test
	public void testEquals() {
		Reservation r = new Reservation(t, c, 1, 1);
		Reservation r2 = new Reservation(t, c, 1, 1);
		assertEquals(r, r2);
		
	}

	

}
