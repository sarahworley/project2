package edu.ncsu.csc216.travel.model.file_io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import edu.ncsu.csc216.travel.model.office.TourCoordinator;

/**
 * A class for writing Wolf Travel data files.
 * 
 * @author sarahworley
 *
 */
public class TravelWriter {

	/**
	 * Writes travel data to a file
	 * 
	 * @param filename file writing to
	 * 
	 */
	public static void writeTravelData(String filename){
		TourCoordinator cordinator = TourCoordinator.getInstance();
		PrintStream fileWriter = null;
		try {
			fileWriter = new PrintStream(new File(filename));
		} catch (FileNotFoundException e) {
			//nope
		}
		String[] clients = cordinator.listClients();
		Object[][] tours = cordinator.filteredTourData();
		for (int i = 0; i < clients.length; i++) {
			fileWriter.println(clients[i]);
		}
		for (int i = 0; i < tours.length; i++) {
			printTour(fileWriter, tours[i]);
			String[] res = cordinator.reservationsForATour(i);
			for (int j = 0; j < res.length; j++) {
				fileWriter.println(res[j]);
			}
		}
		fileWriter.close();
	}
	
	private static void printTour(PrintStream writer, Object[] tour) {
		writer.print("#" + tour[0] + ": ");
		writer.print(tour[1] + " ");
		writer.print(tour[2] + " ");
		writer.print(tour[3] + " ");
		writer.println(tour[4]);
	}

}
