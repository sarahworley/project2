package edu.ncsu.csc216.travel.model.participants;



import edu.ncsu.csc216.travel.list_utils.SimpleArrayList;

import edu.ncsu.csc216.travel.model.vacation.Reservation;

/**
 * Represents a client of Wolf Travel. Every client maintains a list of
 * reservations for that client.
 * 
 * @author sarahworley
 *
 */
public class Client {
	/** Client name */
	private String name;
	/** Client contact */
	private String contact;
	/** Client reservations */
	private SimpleArrayList<Reservation> myReservations;

	/**
	 * Constructor for client
	 * 
	 * @param name    client name
	 * @param contact clients contact
	 */
	public Client(String name, String contact) {
		if (name == null || contact == null || name.equals("")  || contact.equals("")) {
			throw new IllegalArgumentException();
		}

		String trimmedContact = contact.trim();
		String trimmedName = name.trim();
		if (trimmedContact.equals("") || trimmedContact.startsWith("*")  || trimmedName.equals("") || trimmedName.substring(0, 1).matches("\\d")) {
			throw new IllegalArgumentException();
		}

		this.name = trimmedName;
		this.contact = trimmedContact;
		this.myReservations = new SimpleArrayList<Reservation>();
	}

	/**
	 * Returns the clients name
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns clients contact
	 * 
	 * @return the contact
	 */
	public String getContact() {
		return contact;
	}

	/**
	 * Returns the reservations for the client
	 * 
	 * @param index index of reservation
	 * @return the myReservations
	 */
	public Reservation getReservation(int index) {
		if (index < 0 || index >= myReservations.size()) {
			throw new IllegalArgumentException();
		}
		
		return myReservations.get(index);
	}

	/**
	 * Number of reservations for a client
	 * 
	 * @return Number of reservations for a client
	 */
	public int getNumberOfReservations() {
		return myReservations.size();
	}

	/**
	 * Total cost of reservation
	 * 
	 * @return Total cost of reservation
	 */
	public double totalReservationCost() {
		double sum = 0;
		for (int i = 0; i < myReservations.size(); i++) {
			sum += myReservations.get(i).getCost();
		}
		return sum;
	}

	/**
	 * Adds a reservation
	 * 
	 * @param res reservation to add
	 */
	public void addReservation(Reservation res) {
		if (!res.getClient().equals(this)) {
			throw new IllegalArgumentException();
		}
		myReservations.add(res);
	}

	/**
	 * Cancels a reservation
	 * 
	 * @param res reservation to cancel
	 */
	public void cancelReservation(Reservation res) {
		if (!myReservations.contains(res)) {
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < myReservations.size(); i++) {
			if (myReservations.get(i).equals(res)) {
				myReservations.remove(i);
				//return;
			}
		}
		
	}

	/**
	 * Client tour summary info
	 * 
	 * @return summary info
	 */
	public String summaryInfo() {
		String s = this.getName() + " (" + this.getContact() + ")";
		return s;
	}

	/**
	 * Returns a simplearray list of reservations
	 * 
	 * @return list of reservations
	 */
	public String[] listOfReservations() {
		String[] list = new String[myReservations.size()];
		for (int i = 0; i < myReservations.size(); i++) {

			list[i] = myReservations.get(i).displayReservationTour();
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		if (contact == null) {
			if (other.contact != null)
				return false;
		} else if (!contact.toLowerCase().equals(other.contact.toLowerCase()))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.toLowerCase().equals(other.name.toLowerCase()))
			return false;
		return true;
	}

	/*
	 * eclipse generated hash code
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contact == null) ? 0 : contact.toLowerCase().hashCode());

		result = prime * result + ((name == null) ? 0 : name.toLowerCase().hashCode());
		return result;
	}

}
