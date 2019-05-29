package edu.ncsu.csc216.travel.model.vacation;

import java.time.LocalDate;

/**
 * A concrete class extending Tour representing a land tour.
 * 
 * @author sarahworley
 *
 */
public class LandTour extends Tour {
	/** tours prefix name */
	static String prefix  = "LT";
	


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
	public LandTour(String name, LocalDate date, int duration, int basePrice, int capacity) {
		super(name, date, duration, basePrice, capacity) ;

	}

	/**
	 * returns tour cost
	 */
	@Override
	public double costFor(int num) {
		double cost = 0;
		if (num == 1) {
			 cost = getBasePrice() * 1.25;
		} else {
			cost = getBasePrice() * num;
		}
		return cost;
	}

	/**
	 * Returns tour name
	 * @return name 
	 */
	public String getName() {
		
		return prefix + "-" + super.getName();
	}

}
