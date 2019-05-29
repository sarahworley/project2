package edu.ncsu.csc216.travel.model.vacation;

import java.time.LocalDate;

import edu.ncsu.csc216.travel.model.participants.Client;

/**
 * A concrete class extending Tour representing an educational type tour.
 * 
 * @author sarahworley
 *
 */
public class EducationalTrip extends Tour {

	/** tours prefix name */
	static String prefix = "ED";
	
	

	/**
	 * Constructor for tour
	 * 
	 * @param name      tour name
	 * @param date      tour date
	 * @param duration  tour duration
	 * @param basePrice tour base price
	 * @param capacity  tour capacity
	 * @throws CapacityException
	 */
	public EducationalTrip(String name, LocalDate date, int duration, int basePrice, int capacity) {
		super(name, date, duration, basePrice, capacity);
		

	}

	/*
	 * returns tour cost
	 * 
	 */
	@Override
	public double costFor(int num) {
		System.out.print(num);
		double cost = 0;
		cost = num * getBasePrice();
		return cost;
	}

	
	/* 
	 * Gets name
	 */
	@Override
	public String getName() {
		
		return prefix + "-" + super.getName();
	}

	/**
	 * Creates a new reservation for a client
	 * 
	 * @param c   client to created the reservation for
	 * @param res reservation being created
	 * @return reservation created
	 */
	public Reservation createReservationFor(Client c, int res)  throws CapacityException {
		
			
		return super.createReservationFor(c, res);
		
	}

}
