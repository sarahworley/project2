package edu.ncsu.csc216.travel.model.office;

import java.time.LocalDate;
import java.util.Observable;

import edu.ncsu.csc216.travel.list_utils.SimpleArrayList;
import edu.ncsu.csc216.travel.list_utils.SortedLinkedListWithIterator;
import edu.ncsu.csc216.travel.model.file_io.TravelReader;
import edu.ncsu.csc216.travel.model.file_io.TravelWriter;
import edu.ncsu.csc216.travel.model.participants.Client;
import edu.ncsu.csc216.travel.model.vacation.CapacityException;
import edu.ncsu.csc216.travel.model.vacation.EducationalTrip;
import edu.ncsu.csc216.travel.model.vacation.LandTour;
import edu.ncsu.csc216.travel.model.vacation.Reservation;
import edu.ncsu.csc216.travel.model.vacation.RiverCruise;
import edu.ncsu.csc216.travel.model.vacation.Tour;

/**
 * Implements TravelManager and represents the person coordinating tours,
 * clients, and reservations. The TourCoordinator maintains a sorted list of
 * tours and a list of clients.
 * 
 * @author sarahworley
 *
 */
public class TourCoordinator extends Observable implements TravelManager {
	private String filename;

	/** tour by kind */
	private String kindFilter;
	/** if data is saved */
	private boolean dataNotSaved;
	/** tour duration filter */
	private int durationMinFilter;
	/** tour duration filter */
	private int durationMaxFilter;

	/** list of tours */
	private SortedLinkedListWithIterator<Tour> tours;

	/** lists of clients */
	private SimpleArrayList<Client> customer;

	/** single instance of tour coordinator */
	private static TourCoordinator instance;

	private TourCoordinator() {
		this.tours = new SortedLinkedListWithIterator<Tour>();
		this.customer = new SimpleArrayList<Client>();
		this.durationMinFilter = 0;
		this.durationMaxFilter = Integer.MAX_VALUE;
		this.kindFilter = null;
		this.dataNotSaved = true;
	}

	/**
	 * Creates a tour according to the given parameters.
	 * 
	 * @param kind      kind of tour (River Cruise, Land Tour, Educational Trip)
	 * @param name      tour name
	 * @param startDate tour start date
	 * @param duration  length of tour in days
	 * @param basePrice minimum per-person price
	 * @param capacity  maximum number of tour participants
	 * @return the newly created tour
	 * @throws IllegalArgumentException if any parameters are not valid
	 * @throws DuplicateTourException   if a duplicate tour has already been added
	 */
	@Override
	public Tour addNewTour(String kind, String name, LocalDate startDate, int duration, int basePrice, int capacity)
			throws DuplicateTourException {
		Tour tour;
		switch (kind.substring(0, 1)) {
		case "L":
			tour = new LandTour(name, startDate, duration, basePrice, capacity);
			break;
		case "E":
			tour = new EducationalTrip(name, startDate, duration, basePrice, capacity);
			break;
		default:
			tour = new RiverCruise(name, startDate, duration, basePrice, capacity);
			break;
		}
		if (tours.contains(tour)) {
			throw new DuplicateTourException();
		}
		tours.add(tour);
		setChanged();
		notifyObservers(this);

		return tour;
	}

	/**
	 * Gets single instance of tourCoordinator
	 * 
	 * @return instance
	 */
	public static TourCoordinator getInstance() {
		if (instance == null) {
			instance = new TourCoordinator();
		}
		return instance;

	}

	/**
	 * Creates a new client with the given parameters.
	 * 
	 * @param contact  client contact name
	 * @param userName client user name
	 * @return newly created client
	 * @throws IllegalArgumentException if contact is null or blank
	 * @throws DuplicateClientException if new client is the same as an existing
	 *                                  client
	 */
	@Override
	public Client addNewClient(String userName, String contact) throws DuplicateClientException {
		if (contact == null || contact.equals("")) {
			throw new IllegalArgumentException();
		}

		Client client = new Client(userName, contact);
		if (customer.contains(client)) {
			throw new DuplicateClientException();
		}
		customer.add(client);
		dataNotSaved = true;
		setChanged();
		notifyObservers(this);
		return client;
	}

	/**
	 * Sets filters on the kinds of tours under consideration
	 * 
	 * @param kind kind of tour ("River Cruise", "Land Tour", "Educational Trip")
	 * @param min  minimum tour duration
	 * @param max  maximum tour duration
	 * @throws IllegalArgumentException if min > max
	 */
	@Override
	public void setFilters(String kind, int min, int max) {
		if (min > max) {
			throw new IllegalArgumentException();
		}
		this.kindFilter = kind;
		this.durationMinFilter = min;
		this.durationMaxFilter = max;

	}

	/**
	 * Adds a new reservation for a client on a tour with the given number of
	 * participants.
	 * 
	 * @param clientIndex       location of the client in the list of clients
	 * @param filteredTourIndex location of the tour in the list of tours restricted
	 *                          to the current filters
	 * @param numInParty        number of participants for this reservation
	 * @return the newly added reservation
	 * @throws IllegalArgumentException if either index is invalid
	 * @throws CapacityException        if the tour does not have the capacity to
	 *                                  accommodate the reservation
	 */
	@Override
	public Reservation addNewReservation(int clientIndex, int filteredTourIndex, int numInParty)
			throws CapacityException {
		SimpleArrayList<Tour> filteredTours = filtered();
		if (clientIndex < 0 || clientIndex >= customer.size() || filteredTourIndex < 0
				|| filteredTourIndex >= filteredTours.size()) {
			throw new IllegalArgumentException();
		}
		Tour tour = filteredTours.get(filteredTourIndex);
		Client c = customer.get(clientIndex);
		Reservation res = tour.createReservationFor(c, numInParty);
		setChanged();
		notifyObservers(this);
		return res;
	}

	/**
	 * Adds an existing reservation for a given client and tour
	 * 
	 * @param c          Client for the reservation
	 * @param t          Tour for the reservation
	 * @param numInParty Size of the party for the reservation
	 * @param confCode   Confirmation code for the reservation
	 * @return the reservation
	 * @throws CapacityException        if the reservation could not be established
	 *                                  because of lack of capacity
	 * @throws IllegalArgumentException if any reservation data are not valid
	 *                                  (client/trip don't exist etc)
	 */
	@Override
	public Reservation addOldReservation(Client c, Tour t, int numInParty, int confCode) throws CapacityException {
		if (c == null || t == null || !customer.contains(c)) {
			throw new IllegalArgumentException();
		}
		Reservation res = new Reservation(t, c, numInParty, confCode);
		t.addOldReservation(res);
		setChanged();
		notifyObservers(this);
		return res;
	}

	/**
	 * Cancels a reservation on the list of reservations for a particular client.
	 * 
	 * @param clientIndex      location of the client in the list of clients
	 * @param reservationIndex location of the reservation in the client's list of
	 *                         reservations
	 * @return the cancelled trip
	 * @throws IllegalArgumentException if either index is invalid
	 */
	@Override
	public Reservation cancelReservation(int clientIndex, int reservationIndex) {
		if (clientIndex < 0 || clientIndex >= customer.size() || reservationIndex < 0) {
			throw new IllegalArgumentException();

		}
		Client c = customer.get(clientIndex);
		Reservation res = c.getReservation(reservationIndex);
		res.cancel();
		setChanged();
		notifyObservers(this);
		dataNotSaved = true;
		return res;
	}

	/**
	 * Cancels a tour and all reservations for this tour
	 * 
	 * @param filteredTourIndex location of the tour in the list of tours restricted
	 *                          to the current filters
	 * @return the canceled Tour
	 */
	@Override
	public Tour cancelTour(int filteredTourIndex) {
		SimpleArrayList<Tour> filteredTours = filtered();
		if (filteredTourIndex >= filteredTours.size() || filteredTourIndex < 0) {
			throw new IllegalArgumentException();
		}
		Tour toRemove = filteredTours.get(filteredTourIndex);

		while (0 < toRemove.numberOfClientReservations()) {
			toRemove.getReservation(0).cancel();
		}
		tours.remove(tours.indexOf(toRemove));
		setChanged();
		notifyObservers(this);
		return toRemove;
	}

	/**
	 * Gets the total cost of a client's reservations
	 * 
	 * @param clientIndex location of the client in the list of clients
	 * @return total cost of this client's reservations
	 * @throws IllegalArgumentException if clientIndex is out of range
	 */
	@Override
	public double totalClientCost(int clientIndex) {
		if (clientIndex < 0 || clientIndex >= customer.size()) {
			throw new IllegalArgumentException();

		}
		Client client = customer.get(clientIndex);
		// customer.get(customer.indexOf(client));
		double cost = client.totalReservationCost();
		return cost;
	}

	/**
	 * Get an array that lists all clients for the TripCoordinator
	 * 
	 * @return the array, with each client represented as a String
	 */
	@Override
	public String[] listClients() {
		String[] clients = new String[customer.size()];
		for (int i = 0; i < customer.size(); i++) {
			clients[i] = customer.get(i).summaryInfo();
		}
		return clients;

	}

	private SimpleArrayList<Tour> filtered() {
		SimpleArrayList<Tour> acc = new SimpleArrayList<Tour>();

		for (int i = 0; i < tours.size(); i++) {
			Tour t = tours.get(i);
			if ((this.kindFilter == null || this.kindFilter.equals("")
					|| t.getName().startsWith(this.kindFilter.substring(0, 1)))
					&& (t.getDuration() >= this.durationMinFilter) && (t.getDuration() <= this.durationMaxFilter)) {
				acc.add(t);
			}
		}
		return acc;
	}

	/**
	 * Get the list of tour data filtered according to filter settings
	 * 
	 * @return tour data list (each row represents a trip)
	 */
	@Override
	public Object[][] filteredTourData() {
		SimpleArrayList<Tour> filteredTours = filtered();
		Object[][] acc = new Object[filteredTours.size()][];
		for (int i = 0; i < filteredTours.size(); i++) {
			acc[i] = filteredTours.get(i).getAllData();
		}
		return acc;
	}

	/**
	 * String of Reservations for a given trip
	 * 
	 * @param clientIndex index of client in client list
	 * @return array of strings, one for each reservation
	 * @throws IllegalArgumentException if clientIndex is out of range
	 */
	@Override
	public String[] reservationsForAClient(int clientIndex) {
		if (clientIndex < 0 || clientIndex >= customer.size()) {
			throw new IllegalArgumentException();

		}
		Client client = customer.get(clientIndex);

		return client.listOfReservations();
	}

	/**
	 * String of Reservations for a given trip
	 * 
	 * @param filteredTourIndex index
	 * @return array of strings, one for each reservation
	 * @throws IllegalArgumentException if clientIndex is out of range
	 */
	@Override
	public String[] reservationsForATour(int filteredTourIndex) {

		SimpleArrayList<Tour> filteredTours = filtered();
		if (filteredTourIndex < 0 || filteredTourIndex >= filteredTours.size()) {
			throw new IllegalArgumentException();
		}
		Tour t = filteredTours.get(filteredTourIndex);

		return t.listOfReservations();
	}

	/**
	 * Loads file with trip/client/reservation info.
	 * 
	 * @param filename name of file to read.
	 * @throws IllegalArgumentException if the file contains any irregularities or a
	 *                                  read error occurs
	 */
	@Override
	public void loadFile(String filename) {
		TravelReader.readTravelData(filename);
		this.filename = filename;
		setChanged();
		notifyObservers(this);

	}

	/**
	 * Writes current Trips and Clients to file.
	 * 
	 * @param filename file to write.
	 * @throws IllegalArgumentException if file error occurs while attempting to
	 *                                  write
	 */
	@Override
	public void saveFile(String filename) {
		TravelWriter.writeTravelData(filename);
		dataNotSaved = false;
		this.filename = filename;

	}

	/**
	 * Returns the last used filename.
	 * 
	 * @return last used filename, or null if there is none
	 */
	@Override
	public String getFilename() {
		return filename;
	}

	/**
	 * Flushes lists
	 */
	public void flushLists() {
		customer = new SimpleArrayList<Client>();
		tours = new SortedLinkedListWithIterator<Tour>();
		this.durationMinFilter = 0;
		this.durationMaxFilter = Integer.MAX_VALUE;
		this.kindFilter = null;
		dataNotSaved = true;
		setChanged();
		notifyObservers(this);
	}

	/**
	 * If data should be saved
	 * 
	 * @return true
	 */
	public boolean dataShouldBeSaved() {
		return !dataNotSaved;
	}

}
