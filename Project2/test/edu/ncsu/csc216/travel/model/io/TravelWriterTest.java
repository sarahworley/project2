/**
 * 
 */
package edu.ncsu.csc216.travel.model.io;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import edu.ncsu.csc216.travel.model.file_io.TravelWriter;
import edu.ncsu.csc216.travel.model.office.TourCoordinator;
import edu.ncsu.csc216.travel.model.vacation.CapacityException;
import edu.ncsu.csc216.travel.model.office.DuplicateTourException;
import edu.ncsu.csc216.travel.model.office.DuplicateClientException;
import edu.ncsu.csc216.travel.model.vacation.Reservation;



/**
 * Tets for travel writer
 * 
 * @author sarahworley
 *
 */
public class TravelWriterTest {

	
	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.travel.model.file_io.TravelWriter#writeTravelData(java.lang.String)}.
	 */
	@Test
	public void testWriteTravelData() {
		try {
			TourCoordinator tc = TourCoordinator.getInstance();
			tc.flushLists();
			tc.addNewClient("a", "b");
			tc.addNewTour("Land Tour", "c", LocalDate.now(), 2, 100, 20);
			tc.addNewReservation(0, 0, 5);
			TravelWriter.writeTravelData("test-files/travel_data_out.md");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		
	}
	
	/**
	 * ts test
	 */
	@Test
	public void tesTS() {
		TourCoordinator tc = TourCoordinator.getInstance();
		String fileName = "test-files/yolo.md";
		try {
			
			tc.flushLists();
			  tc.addNewTour("ED", "TripR3", LocalDate.of(2019, 12, 12), 2, 500, 6);
			  tc.addNewTour("LT", "TripL1", LocalDate.of(2020, 2, 1), 1, 1000, 5);
			} catch (DuplicateTourException e) {	
				//nope
			}
			try {
				tc.addNewClient("AC", "ac");
				tc.addNewClient("BC", "bc");
				tc.addNewClient("CC", "cc");
			} catch (DuplicateClientException e) {
				fail("DuplicateClientException thrown when no duplicate clients were added");
			}
			Reservation[] codes = {null, null, null, null};
			try {
				codes[0] = tc.addNewReservation(1, 0, 1);
				codes[1] = tc.addNewReservation(1, 1, 1);
				codes[2] = tc.addNewReservation(1, 1, 1);
				codes[3] = tc.addNewReservation(0, 1, 3);
			} catch (CapacityException e) {
				fail(e.getMessage());
			} 
			TravelWriter.writeTravelData(fileName);
	}

}
