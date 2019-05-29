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

/**
 * test for river cruise
 * 
 * @author sarahworley
 *
 */
public class RiverCruiseTest {
	LocalDate start3 = LocalDate.of(2019, 6, 4);
	Tour t3 = new RiverCruise("River Cruise 1", start3, 1, 150, 40);
	String name = "bob";
	String last = "bobby";
	Client c = new Client(name, last);

	Reservation r1 = new Reservation(t3, c, 4);

	
	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.travel.model.vacation.RiverCruise#getName()}.
	 */
	@Test
	public void testGetName() {
		Tour t0 = null;
		try {
			t0 = new RiverCruise(null, start3, 2, 200, 15);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(t0);
		}
		try {
			t0 = new RiverCruise("", start3, 2, 200, 15);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(t0);
		}
	
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.travel.model.vacation.RiverCruise#costFor()}.
	 */
	
	
	@Test
	public void testCostFor() {
		try {
			t3.addOldReservation(r1);
		} catch (CapacityException e) {
			fail();
		}
		
		assertEquals(22500 , t3.costFor(t3.getBasePrice()), 0);
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.travel.model.vacation.RiverCruise#RiverCruise(java.lang.String, java.time.LocalDate, int, int, int)}.
	 */
	@Test
	public void testRiverCruise() {
		
		assertEquals("RC-River Cruise 1", t3.getName());
		assertEquals(start3, t3.getStartDate());
		assertEquals(1, t3.getDuration());
		assertEquals(150, t3.getBasePrice());
	}

}
