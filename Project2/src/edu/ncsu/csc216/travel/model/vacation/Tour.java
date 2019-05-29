package edu.ncsu.csc216.travel.model.vacation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import edu.ncsu.csc216.travel.list_utils.SimpleArrayList;
import edu.ncsu.csc216.travel.model.participants.Client;

/**
 * An abstract class representing a Wolf Travel tour. Every tour maintains a
 * list of reservations for that tour.
 * 
 * @author sarahworley
 *
 */
public abstract class Tour implements Comparable<Tour> {
	/** tour name */
	private String name;
	/** tour start */
	private LocalDate start;
	/** tour duration */
	private int duration;
	/** tour capacity */
	private int capacity;
	/** tours fixed capacity */
	private boolean capacityFixed;
	/** tour base price */
	private int basePrice;
	/** tours number of participants */
	private int numParticipants;

	private SimpleArrayList<Reservation> res;

	/**
	 * Tour constructor should throw an IllegalArgumentException if any parameters
	 * are not valid
	 * 
	 * @param name      of tour
	 * @param start      of tour
	 * @param duration  of tour
	 * @param basePrice of tour
	 * @param capacity  of tour
	 * @throws CapacityException
	 */
	public Tour(String name, LocalDate start, int duration, int basePrice, int capacity) {
		
		if (name == null ||  name.contains(":") || start == null || duration < 1 || basePrice < 0 || capacity < 1) {
			throw new IllegalArgumentException();
		}
		String trimmed = name.trim();
		if (trimmed.equals("")) {
			throw new IllegalArgumentException();
		}
		this.name = trimmed;
		this.start = start;
		this.duration = duration;
		this.basePrice = basePrice;
		this.capacity  = capacity;
		if(this.getName().equals("ED")) {
			this.capacityFixed = false;
		}
		if (this.getClass().getName().equals( "edu.ncsu.csc216.travel.model.vacation.EducationalTrip")) {
			this.capacityFixed = false;
		} else {
			this.capacityFixed = true;
		}
		this.res = new SimpleArrayList<Reservation>();
		numParticipants =  0;
	}

	/**
	 * Compares two tours
	 * 
	 * @param t tour to compare
	 * @return int
	 */
	public int compareTo(Tour t) {
		if (t == null) {
			throw new NullPointerException();
		}

		int date = this.getStartDate().compareTo(t.getStartDate());
		int name1 = this.getName().compareTo(t.getName());

		int dur = ((Integer) this.getDuration()).compareTo(t.getDuration());

		if (date < 0) {
			return -1;
		} else if (date > 0) {
			return 1;
		} else if (name1 < 0) {
			return -1;
		} else if (name1 > 0) {
			return 1;
		} else if (dur < 0) {
			return -1;
		} else if (dur > 0) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * Sets the tours capacity
	 * 
	 * @param newCap new capacity
	 */
	protected void setCapacity(int newCap) throws CapacityException {
		if (newCap < 0 || isCapacityFixed() || newCap < numParticipants) {
			throw new CapacityException();
		}
		this.capacity = newCap;
		fixCapacity();
	}

	/**
	 * Returns the number of client reservations for a tour
	 * 
	 * @return client reservations for a tour
	 */
	public int numberOfClientReservations() {

		return res.size();
	}

	/**
	 * Returns how many spaces are left for a tour
	 * 
	 * 
	 * @return spots available on tour
	 */
	public int spacesLeft() {

		return this.capacity - numParticipants;
	}

	/**
	 * Returns a tours capacity
	 * 
	 * @return capacity
	 */
	public int getCapacity() {
		return this.capacity;
	}

	/**
	 * Returns true if the tours capacity is fixed or not
	 * 
	 * @return true
	 */
	public boolean isCapacityFixed() {
		return capacityFixed;
	}

	/**
	 * fixes tour capacity
	 *
	 */
	public void fixCapacity() {
		if (!isCapacityFixed()) {
			capacityFixed = true;
		}
	}

	/**
	 * Returns the tours start Date
	 * 
	 * @return start Date
	 */
	public LocalDate getStartDate() {
		return this.start;
	}

	/**
	 * Returns the tours end date
	 * 
	 * @return end date
	 */
	public LocalDate getEndDate() {
		return getStartDate().plusDays(duration - 1);
	}

	/**
	 * Returns the tours duration
	 * 
	 * @return tours duration
	 */
	public int getDuration() {
		return this.duration;
	}

	/**
	 * Returns the tours base price
	 * 
	 * @return the tours base price
	 */
	public int getBasePrice() {
		return this.basePrice;
	}

	/**
	 * Returns String form of tour name
	 * 
	 * @return String form of tour name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns tour reservation
	 * 
	 * @param index of reservation to return
	 * @return tour reservation
	 */
	public Reservation getReservation(int index) {
		if(index < 0 || index >= res.size()) {
			throw new IllegalArgumentException();
		}
		return res.get(index);
	}

	/**
	 * Returns a string form of tour info
	 * 
	 * @return string form of tour info
	 */
	public String summaryInfo() {
		// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm dd yyyy");

		return getName() + ": " + getStartDate().format(DateTimeFormatter.ofPattern("MM/dd/yy")) + " " + duration
				+ " days";
	}

	/**
	 * Returns all data in an object Array
	 * 
	 * @return all data in an object Array
	 */
	public Object[] getAllData() {
		String cap = ((Integer) getCapacity()).toString();
		if (!isCapacityFixed()) {
			cap += "*";
		}

		return new Object[] { getName(), getStartDate().format(DateTimeFormatter.ofPattern("MM/dd/yy")),
				((Integer) getDuration()).toString(), "$" + ((Integer) getBasePrice()).toString(),
				cap };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + duration;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		return result;
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
		Tour other = (Tour) obj;
		if (duration != other.duration)
			return false;
		if (getName() == null) {
			if (other.getName() != null)
				return false;
		} else if (!(getName()).toUpperCase().equals(other.getName().toUpperCase()))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		return true;
	}

	/**
	 * Returns a String array of reservations
	 * 
	 * @return String array of reservations
	 */
	public String[] listOfReservations() {
		String[] list = new String[res.size()];
		for (int i = 0; i < res.size(); i++) {
			list[i] = res.get(i).displayReservationClient();
		}
		return list;
	}

	/**
	 * Gets the cost for a tour
	 * 
	 * @param num of people in tour
	 * @return the price
	 */
	abstract double costFor(int num);

	/**
	 * Creates a new reservation for a client
	 * 
	 * @param c   client to created the reservation for
	 * @param num reservation being created
	 * @return reservation created
	 */
	public Reservation createReservationFor(Client c, int num) throws CapacityException {
		if (num < 1) {
			throw new IllegalArgumentException();
		}
		Reservation resAdd = new Reservation(this, c, num);
		return addOldReservation(resAdd);
	}

	/**
	 * Adds an old reservation
	 * 
	 * @param resAdd old reservation to add
	 * @return resevation added
	 */
	public Reservation addOldReservation(Reservation resAdd) throws CapacityException {
		if (resAdd.getTour() != this) {
			throw new IllegalArgumentException();
		}
		int num = resAdd.getNumInParty();
		if (this.spacesLeft() < num && !isCapacityFixed()) {
			this.setCapacity(2 * getCapacity());
		}
		if (this.spacesLeft() >= num) {
			resAdd.getClient().addReservation(resAdd);
			res.add(resAdd);
			numParticipants += num;

		} else {

			throw new CapacityException();
		}

		return resAdd;
	}

	/**
	 * Cancels a reservation
	 * 
	 * @param resToRemove reservation to cancel
	 */
	public void cancelReservation(Reservation resToRemove) {
		numParticipants -= resToRemove.getNumInParty();
		int remove = res.indexOf(resToRemove);
		res.remove(remove);

	}
}
