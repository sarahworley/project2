package edu.ncsu.csc216.travel.model.vacation;

import edu.ncsu.csc216.travel.model.participants.Client;

/**
 * Represents a client’s reservation for a tour.
 * 
 * @author sarahworley
 *
 */
public class Reservation {

	/** reservation code generator */
	static int codeGenerator = 0;
	/** Reservation max */
	static int maxCode = 999999;
	/** reservation confirmation code */
	private String confirmationCode;
	/** number in reservation */
	private int numInParty;
	/** reservation cost */
	//private double cost;

	private Tour theTour;

	private Client theClient;

	/**
	 * Creates a “temporary” reservation with the parameter information
	 * 
	 * @param tour       to be reserved
	 * @param c          client reserving
	 * @param num number in party
	 */
	public Reservation(Tour tour, Client c, int num) {
		if(tour ==  null || c == null || num <= 0) {
			throw new IllegalArgumentException();
		}
		this.theTour = tour;
		this.theClient = c;
		this.numInParty = num;
		this.confirmationCode = String.format("%06d", codeGenerator);
		setCodeGenerator(codeGenerator);
		

	}

	/**
	 * Creates a “temporary” reservation with the parameter information with
	 * confirmation code
	 * 
	 * @param tour             to be reserved
	 * @param c                client
	 * @param num       number in party
	 * @param confirm confirmation code
	 */
	public Reservation(Tour tour, Client c, int num, int confirm) {
		if(tour ==  null || c == null || confirm > maxCode ||  confirm < 0 || num <= 0) {
			throw new IllegalArgumentException();
		}	
		this.theTour = tour;
		this.theClient = c;
		this.numInParty = num;	
		this.confirmationCode = String.format("%06d", confirm);
		setCodeGenerator(confirm);
		
	}
	
	private void setCodeGenerator (int k) {
		if (k >= maxCode) {
			codeGenerator = 0;
		} else if (k >= codeGenerator) {
			codeGenerator = k + 1;
		}
	}

	/**
	 * Get tour
	 * 
	 * @return returs tour
	 */
	public Tour getTour() {
		return this.theTour;
	}

	/**
	 * Gets client of reservation
	 * 
	 * @return client of reservation
	 */
	public Client getClient() {
		return this.theClient;
	}

	/**
	 * Gets number of people in a reservations party
	 * 
	 * @return number of people in a reservations party
	 */
	public int getNumInParty() {
		return this.numInParty;
	}

	/**
	 * Returns the reservation confirmation code
	 * 
	 * @return the reservation confirmation code
	 */
	public String getConfirmationCode() {
		
		return this.confirmationCode;
	}

	/**
	 * Gets the reservations cost
	 * 
	 * @return the reservations cost
	 */
	public double getCost() {
		return this.theTour.costFor(getNumInParty());
	}

	/**
	 * Cancels a reservation
	 * 
	 */
	public void cancel() {
		theClient.cancelReservation(this);
		theTour.cancelReservation(this);
		

	}

	/**
	 * Displays reservations for a tour
	 * 
	 * @return reservations for a tour
	 */
	public String displayReservationTour() {
		return getConfirmationCode() + " " + getNumInParty() + " "  + theTour.summaryInfo();
	}

	/**
	 * Displays reservations for a Client
	 * 
	 * @return reservations for a Client
	 */
	public String displayReservationClient() {
		return getConfirmationCode() + " " + getNumInParty() + " " + theClient.summaryInfo();
	}

	/**
	 * Resets the code generator to zero
	 */
	public static void resetCodeGenerator() {
		codeGenerator = 0;

	}

	/*
	 * eclipse generated hashcode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((confirmationCode == null) ? 0 : confirmationCode.hashCode());
		return result;
	}

	/*
	 *eclipse generated equals
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reservation other = (Reservation) obj;
		if (confirmationCode == null) {
			if (other.confirmationCode != null)
				return false;
		} else if (!confirmationCode.equals(other.confirmationCode))
			return false;
		return true;
	}

}
