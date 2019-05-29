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


/** Test for EducationalTrip
 * @author sarahworley
 *
 */
public class EducationalTripTest {
	LocalDate start1 = LocalDate.of(2019, 3, 27);
	LocalDate start2 = LocalDate.of(2019, 10, 20);
	LocalDate start3 = LocalDate.of(2019, 6, 4); 
	String name = "bob";
	String last = "bobby";
	Client c = new Client(name, last);

	
	
	Tour t2 = new EducationalTrip("Educational Trip 1", start2, 2, 70, 35);
	Reservation r1 = new Reservation(t2, c, 4);

	/**
	 * Test method for {@link edu.ncsu.csc216.travel.model.vacation.EducationalTrip#getName()}.
	 */
	@Test
	public void testGetName() {
		assertEquals("ED-Educational Trip 1", t2.getName());
		Tour t0 = null;
		try {
			t0 = new EducationalTrip(null, start1, 2, 200, 15);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(t0);
		}
		try {
			t0 = new EducationalTrip("", start1, 2, 200, 15);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(t0);
		}
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.travel.model.vacation.EducationalTrip#costFor()}.
	 */
	@Test
	public void testCostFor() {
		try {
			t2.addOldReservation(r1);
		} catch (CapacityException e) {
			fail();
		}
		
		assertEquals(4900 , t2.costFor(t2.getBasePrice()), 0);
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.travel.model.vacation.EducationalTrip#EducationalTrip(java.lang.String, java.time.LocalDate, int, int, int)}.
	 */
	@Test
	public void testEducationalTrip() {
		Tour t3 = new EducationalTrip("Educational Trip 1", start2, 2, 70, 35);
		assertEquals("ED-Educational Trip 1", t2.getName());
		assertEquals(start2, t3.getStartDate());
		assertEquals(2, t3.getDuration());
		assertEquals(70, t3.getBasePrice());
		assertEquals(35, t3.getCapacity());
	}

}
