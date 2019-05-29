/**
 * 
 */
package edu.ncsu.csc216.travel.model.office;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.travel.model.participants.Client;
import edu.ncsu.csc216.travel.model.vacation.CapacityException;
import edu.ncsu.csc216.travel.model.vacation.LandTour;



/**
 * Test for Tour cooridinator
 * 
 * @author sarahworley
 *
 */
public class TourCoordinatorTest {
	
	
	private TourCoordinator tc;
	
	
	
	/**
	 * set up
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		tc = TourCoordinator.getInstance();
		tc.flushLists();
		
	}
	
	/**
	 * test
	 * 
	 *
	 */
	@Test
	public void testTour() {
		try {
			tc.addNewClient("a", "b");
			tc.addNewTour("Land Tour", "c", LocalDate.now(), 2, 100, 20);
			tc.addNewReservation(0, 0, 5);
			tc.setFilters("L", 0, 10);
			assertEquals(1, tc.reservationsForATour(0).length);
			assertEquals(1, tc.filteredTourData().length);
			tc.setFilters("E", 0, 10);
			assertEquals(0, tc.filteredTourData().length);
			tc.setFilters("L", 4, 10);
			assertEquals(0, tc.filteredTourData().length);
			tc.setFilters("L", 0, 1);
			assertEquals(0, tc.filteredTourData().length);
			tc.setFilters("L", 0, 10);
			tc.cancelTour(0);
			
		} catch (DuplicateClientException | DuplicateTourException | CapacityException e) {
			fail();
			e.printStackTrace();
		}
	}
	
	/**
	 * tests reservation
	 */
	@Test
	public void testReservation() {
		try {
			tc.addNewClient("a", "b");
			tc.addNewTour("Land Tour", "c", LocalDate.now(), 2, 100, 20);
			try {
				tc.addNewReservation(0, 0, 50);
				fail();
			} catch (CapacityException e) {
				//nope
			}
			tc.addNewReservation(0, 0, 5);
			assertEquals(500., tc.totalClientCost(0), 0);
			assertEquals(1, tc.reservationsForAClient(0).length);
			assertEquals(1, tc.listClients().length);
			tc.cancelReservation(0, 0);
		} catch (DuplicateClientException | DuplicateTourException | CapacityException e) {
			fail();
			e.printStackTrace();
		}
	}
	
	/**
	 * tests old reservation
	 */
	@Test
	public void testOldReservation() {
		try {
			Client c = new Client("a", "b");
			LandTour tour = new LandTour("c", LocalDate.now(), 2, 100, 20);
			tc.addOldReservation(c, tour, 5, 9);
			try {
				tc.addOldReservation(null, null, 0, 0);
			} catch (IllegalArgumentException e) {
				//nope
			}
		} catch (CapacityException e) {
			fail();
			e.printStackTrace();
		}
	}
	
	/**
	 * test
	 * 
	 *
	 */
	@Test
	public void testFlush() {
		try {
			tc.addNewClient("a", "b");
			tc.addNewTour("Land Tour", "c", LocalDate.now(), 2, 100, 20);
			tc.addNewReservation(0, 0, 5);
			tc.flushLists();
		} catch (DuplicateClientException | DuplicateTourException | CapacityException e) {
			fail();
			e.printStackTrace();
		}
	}
	
	
	

}
