package edu.ncsu.csc216.travel.model.vacation;

import java.time.LocalDate;

/** A concrete class extending Tour representing a river cruise type tour.
 * @author sarahworley
 *
 */
public class RiverCruise extends Tour {
	
	/** tours prefix name */
	static String prefix = "RC" ;
	
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
	public RiverCruise(String name, LocalDate date, int duration, int basePrice, int capacity)   {
		super(name, date, duration, basePrice, capacity);
		
	}

	/**
	 * returns tour cost
	 */
	@Override
	public double costFor(int num) {
		double cost = 0;
		if (num % 2 == 0) {
			cost = num * getBasePrice();
		} else {
			cost = (num * getBasePrice()) + (.5 * getBasePrice());
		}
		return cost;
	}
	
	/* 
	 * Gets name
	 */
	@Override
	public String getName() {
		return prefix + "-" + super.getName();
	}

}
