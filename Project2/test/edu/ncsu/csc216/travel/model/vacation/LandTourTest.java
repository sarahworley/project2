/**
 * 
 */
package edu.ncsu.csc216.travel.model.vacation;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.time.LocalDate;


import org.junit.Test;

import edu.ncsu.csc216.travel.model.participants.Client;


/**Test for land tour
 * @author sarahworley
 *
 */
public class LandTourTest {
	LocalDate start1 = LocalDate.of(2019, 3, 27);
	String name = "bob";
	String last = "bobby";
	Client c = new Client(name, last);
	Tour t1 = new LandTour("Land tour 1", start1, 2, 200, 15);
	Reservation r1 = new Reservation(t1, c, 4);

	/**
	 * Test method for {@link edu.ncsu.csc216.travel.model.vacation.LandTour#getName()}.
	 */
	@Test
	public void testGetName() {
		Tour t0 = null;
		try {
			t0 = new LandTour(null, start1, 2, 200, 15);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(t0);
		}
		try {
			t0 = new LandTour("", start1, 2, 200, 15);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(t0);
		}
		
		try {
			t0 = new LandTour("Land tour 1", start1, -5, 200, 15);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(t0);
		}
		try {
			t0 = new LandTour("Land tour 1", start1, 5, -200, 15);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(t0);
		}
		try {
			t0 = new LandTour("Land tour 1", start1, 5, 200, -15);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(t0);
		}
		
		
		assertEquals("LT-Land tour 1", t1.getName());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.travel.model.vacation.LandTour#costFor()}.
	 */
	@Test
	public void testCostFor() {
		try {
			t1.addOldReservation(r1);
		} catch (CapacityException e) {
			fail();
		}
		
		assertEquals(40000 , t1.costFor(t1.getBasePrice()), 0);
	
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.travel.model.vacation.LandTour#LandTour(java.lang.String, java.time.LocalDate, int, int, int)}.
	 */
	@Test
	public void testLandTour() {
		
		assertEquals("LT-Land tour 1", t1.getName());
		assertEquals(start1, t1.getStartDate());
		assertEquals(2, t1.getDuration());
		assertEquals(200, t1.getBasePrice());
		assertEquals(15, t1.getCapacity());
	}

}
