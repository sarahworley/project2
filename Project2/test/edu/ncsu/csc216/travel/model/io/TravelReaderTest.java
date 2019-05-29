package edu.ncsu.csc216.travel.model.io;

import static org.junit.Assert.*;


import org.junit.Test;

import edu.ncsu.csc216.travel.model.file_io.TravelReader;

/** test for travel reader
 * @author sarahworley
 *
 */
public class TravelReaderTest {

	

	/**
	 * test
	 */
	@Test
	public void testReadTravelData() {
		try {
			TravelReader.readTravelData("test-files/travel_data.md");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		try {
			TravelReader.readTravelData("test-files/aint-real.md");
			fail();
		} catch (Exception e) {
			//nope
		}
	}

}
