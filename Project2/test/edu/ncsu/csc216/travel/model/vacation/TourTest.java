/**
 * 
 */
package edu.ncsu.csc216.travel.model.vacation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.time.LocalDate;

import org.junit.Test;

import edu.ncsu.csc216.travel.list_utils.SimpleArrayList;
import edu.ncsu.csc216.travel.model.participants.Client;

/**
 * Test for tour
 * 
 * @author sarahworley
 *
 */
public class TourTest {
	String name = "bob";
	String last = "bobby";
	Client c = new Client(name, last);
	Client c1 = new Client(name, last);
	Client c2 = new Client(name, last);
	LocalDate start1 = LocalDate.of(2019, 3, 27);
	LocalDate start2 = LocalDate.of(2019, 10, 20);
	LocalDate start3 = LocalDate.of(2019, 6, 4);

	Tour t1 = new LandTour("Land tour 1", start1, 2, 200, 15);
	Tour t4 = new LandTour("Land tour 1", start1, 2, 200, 15);

	Tour t2 = new EducationalTrip("Educational Trip 1", start2, 2, 70, 35);
	Tour t3 = new RiverCruise("River Cruise 1", start3, 1, 150, 40);
	Tour t5 = new RiverCruise("River Cruise 2", start3, 1, 150, 40);
	Tour t6 = new RiverCruise("River Cruise 1", start3, 3, 150, 40);

	SimpleArrayList<Reservation> reservations = new SimpleArrayList<Reservation>();

	Reservation r1 = new Reservation(t1, c, 4);
	Reservation r2 = new Reservation(t2, c1, 36);
	Reservation r3 = new Reservation(t3, c2, 4);

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.travel.model.vacation.Tour#Tour(java.lang.String, java.time.LocalDate, int, int, int)}.
	 */
	@Test
	public void testTour() {
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

		try {
			t1.setCapacity(-1);
			fail();
		} catch (CapacityException e) {
			assertEquals(15, t1.getCapacity());
		}

		// Tour t1 = new LandTour("Land tour 1", start1, 2, 200, 15);
		assertEquals("LT-Land tour 1", t1.getName());
		assertEquals(start1, t1.getStartDate());
		assertEquals(2, t1.getDuration());
		assertEquals(200, t1.getBasePrice());
		assertEquals(15, t1.getCapacity());

		// Tour t2 = new EducationalTrip("Educational Trip 1", start2, 2, 70, 35);
		assertEquals("ED-Educational Trip 1", t2.getName());
		assertEquals(start2, t2.getStartDate());
		assertEquals(2, t2.getDuration());
		assertEquals(70, t2.getBasePrice());
		assertEquals(35, t2.getCapacity());

		// Tour t3 = new RiverCruise("River Cruise 1", start3, 1, 150, 40);
		assertEquals("RC-River Cruise 1", t3.getName());
		assertEquals(start3, t3.getStartDate());
		assertEquals(1, t3.getDuration());
		assertEquals(150, t3.getBasePrice());
		assertEquals(40, t3.getCapacity());

	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.travel.model.vacation.Tour#compareTo(edu.ncsu.csc216.travel.model.vacation.Tour)}.
	 */
	@Test
	public void testCompareTo() {
		assertEquals(-1, t1.compareTo(t2));
		assertEquals(1, t2.compareTo(t1));
		assertEquals(1, t3.compareTo(t1));
		assertEquals(-1, t1.compareTo(t3));
		assertEquals(1, t5.compareTo(t3));
		assertEquals(-1, t3.compareTo(t5));
		assertEquals(1, t6.compareTo(t3));
		assertEquals(-1, t3.compareTo(t6));
		assertEquals(0, t3.compareTo(t3));

		try {
			t6.compareTo(null);
			fail();
		} catch (NullPointerException e) {
			// shouldnt reach
		}
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.travel.model.vacation.Tour#numberOfClientReservations()}.
	 */
	@Test
	public void testNumberOfClientReservations() {
		try {
			t1.createReservationFor(c, 5);
		} catch (CapacityException e) {
			fail();

		}
		assertEquals(1, t1.numberOfClientReservations());

	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.travel.model.vacation.Tour#summaryInfo()}.
	 */
	@Test
	public void testSummaryInfo() {
		assertEquals("LT-Land tour 1: 03/27/19 2 days", t1.summaryInfo());
		assertEquals("ED-Educational Trip 1: 10/20/19 2 days", t2.summaryInfo());
		assertEquals("RC-River Cruise 1: 06/04/19 1 days", t3.summaryInfo());
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.travel.model.vacation.Tour#createReservationForClient(edu.ncsu.csc216.travel.model.participant.Client, int)}.
	 * 
	 * 
	 */
	@Test
	public void testCreateReservationForClient() {
		try {
			t1.createReservationFor(c, 1000);
			fail();
		} catch (CapacityException e) {

			e.printStackTrace();
		}

	}

	/**
	 * Test for get reservation
	 */
	@Test
	public void testGetRes() {
		try {

			t1.addOldReservation(r1);

		} catch (CapacityException e) {

			fail();
		}
		assertEquals(r1, t1.getReservation(0));

	}

	/**
	 * Test for get data
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testData() {
		assertEquals(new Object[] { "LT-Land tour 1", "03/27/19", "2", "$200", "15" }, t1.getAllData());

	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.travel.model.vacation.Tour#addOldReservation(edu.ncsu.csc216.travel.model.vacation.Reservation)}.
	 */
	@Test
	public void testAddOldReservation() {
		try {
			t1.addOldReservation(r1);
			assertEquals(11, t1.spacesLeft());
		} catch (Exception e) {

			e.printStackTrace();
		}
		try {
			assertEquals(35, t2.spacesLeft());
			assertEquals(false, t2.isCapacityFixed());
			t2.addOldReservation(r2);
			// doubles
			assertEquals(11, t1.spacesLeft());
			assertEquals(true, t2.isCapacityFixed());
		} catch (Exception e) {

			// shouln't reach
		}

	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.travel.model.vacation.Tour#cancelReservation(edu.ncsu.csc216.travel.model.vacation.Reservation)}.
	 */
	@Test
	public void testCancelReservation() {
		try {
			t1.addOldReservation(r1);
			assertEquals(11, t1.spacesLeft());
		} catch (Exception e) {

			e.printStackTrace();
		}

		t1.cancelReservation(r1);
		assertEquals(15, t1.spacesLeft());

	}
	/**
	 * Test for hash code
	 */
	@Test
	public void testHash() {
		assertEquals(t1.hashCode(), t4.hashCode());

	}
	/**
	 * tests equals
	 */
	@Test
	public void testEquals() {
		assertTrue(t1.equals(t4));
		assertFalse(t1.equals(t2));
	}

}
