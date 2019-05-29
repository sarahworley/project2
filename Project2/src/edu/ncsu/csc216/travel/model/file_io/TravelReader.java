package edu.ncsu.csc216.travel.model.file_io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;

import edu.ncsu.csc216.travel.model.office.DuplicateClientException;
import edu.ncsu.csc216.travel.model.office.DuplicateTourException;
import edu.ncsu.csc216.travel.model.office.TourCoordinator;
import edu.ncsu.csc216.travel.model.participants.Client;
import edu.ncsu.csc216.travel.model.vacation.CapacityException;
import edu.ncsu.csc216.travel.model.vacation.Tour;

/**
 * A class for reading Wolf Travel data files
 * 
 * @author sarahworley
 *
 */
public class TravelReader {
	
	
	/**
	 * Reads travel data from file
	 * 
	 * @param filename file reading from
	 */
	
	public static void readTravelData(String filename) {
		TourCoordinator tc = TourCoordinator.getInstance();
		 
		Scanner s = null;
		try {
			s = new Scanner(new FileInputStream(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		boolean parsingClients = true;
		Tour tour = null;

		while (s.hasNext()) {
			String line = s.nextLine();
			if (line.startsWith("#")) {
				// example string "#LT-Bicycling the Pacific Coast:  09/09/02  10   $2000  8"
				parsingClients = false;
				line = line.substring(1); // drop the "#"
				String[] split = line.split(":");
				String name = split[0];
				String[] args = split[1].trim().split("\\s+");
				int capacity;
				String cap = args[3];
				if (cap.endsWith("*")) {
					capacity = Integer.parseInt(cap.substring(0, cap.length() - 1)) / 2;
				} else {
					capacity = Integer.parseInt(cap);
				}
				String[] date = args[0].split("/");
				int m = Integer.parseInt(date[0]);
				int d = Integer.parseInt(date[1]);
				int y = Integer.parseInt(date[2]);
				if (y < 2000) {
					y += 2000;
				}
				try {
					tour = tc.addNewTour(name.substring(0, 2),
							name.substring(3),
							LocalDate.of(y, m, d), 
							Integer.parseInt(args[1]),
							Integer.parseInt(args[2].substring(1)),
						    capacity);
				} catch (DuplicateTourException e) {
					throw new IllegalArgumentException();
				}
				
				
			} else if (parsingClients) {
				// example string "Family Adventures (919-239-0000)"
				String[] info = clientInfo(line);
				try {
					tc.addNewClient(info[0], info[1]);
				} catch (DuplicateClientException e) {
					throw new IllegalArgumentException();
				}
			} else {
				// example string "000010   1 John Doe (John Doe)"
				int confirm = Integer.parseInt(line.substring(0, 6)); // first 6 chars are confirm
				line = line.substring(6);
				
				Scanner clientScanner = new Scanner(line);
				int numInParty = clientScanner.nextInt();
				String[] info = clientInfo(clientScanner.nextLine().trim());
				clientScanner.close();
				Client client = new Client(info[0], info[1]);
				try {
					tc.addOldReservation(client, tour, numInParty, confirm);
				} catch (CapacityException e) {
					throw new IllegalArgumentException();
				} 
			}
		}
		
	}
	
	
	private static String[] clientInfo(String line) {
		String[] client = line.split("\\(");
		String contactLine = client[1].trim();
		String contact = contactLine.substring(0, contactLine.length() - 1);
		String name = client[0].trim();
		return new String [] {name , contact};
		
		
	}
}
