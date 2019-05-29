package edu.ncsu.csc216.travel.model.office;

import java.time.LocalDate;

import edu.ncsu.csc216.travel.model.vacation.CapacityException;
import edu.ncsu.csc216.travel.model.vacation.Reservation;
import edu.ncsu.csc216.travel.model.vacation.Tour;
import edu.ncsu.csc216.travel.model.participants.Client;

/**
 * TravelManager specifies the behaviors necessary to manage trips, clients, and
 * reservations. Every class that implements TravelManager must have lists of clients
 * and trips, each of which has its own list of reservations. The list of trips can
 * be filtered.
 * 
 * @author Jo Perry
 */
public interface TravelManager { 

   /**
    * Creates a tour according to the given parameters.
    * @param kind  kind of tour (River Cruise, Land Tour, Educational Trip) 
    * @param name  tour name
    * @param startDate  tour start date
    * @param duration  length of tour in days
    * @param basePrice  minimum per-person price
    * @param capacity  maximum number of tour participants
    * @return the newly created tour
    * @throws IllegalArgumentException if any parameters are not valid
    * @throws DuplicateTourException if a duplicate tour has already been added
    */
   Tour addNewTour(String kind, String name, LocalDate startDate, int duration, 
         int basePrice, int capacity) throws DuplicateTourException;
   
   /**
    * Creates a new client with the given parameters.
    * @param contact  client contact name
    * @param userName  client user name
    * @return newly created client
    * @throws IllegalArgumentException if contact is null or blank
    * @throws DuplicateClientException if new client is the same as an existing client
    */
   Client addNewClient(String contact, String userName) throws DuplicateClientException;
   
   /**
    * Sets filters on the kinds of tours under consideration
    * @param kind  kind of tour ("River Cruise", "Land Tour", "Educational Trip")  
    * @param min  minimum tour duration
    * @param max  maximum tour duration
    * @throws IllegalArgumentException if min > max
    */
   void setFilters(String kind, int min, int max);
   
   /**
    * Adds a new reservation for a client on a tour with the given number of participants.
    * @param clientIndex  location of the client in the list of clients
    * @param filteredTourIndex location of the tour in the list of tours restricted to the current filters
    * @param numInParty  number of participants for this reservation
    * @return the newly added reservation
    * @throws IllegalArgumentException if either index is invalid
    * @throws CapacityException if the tour does not have the capacity to accommodate the reservation
    */
   Reservation addNewReservation(int clientIndex, int filteredTourIndex, int numInParty) throws CapacityException ;
   
   /**
    * Adds an existing reservation for a given client and tour
    * @param c  Client for the reservation
    * @param t Tour for the reservation
    * @param numInParty Size of the party for the reservation
    * @param confCode Confirmation code for the reservation
    * @return the reservation
    * @throws CapacityException if the reservation could not be established because of lack of capacity
    * @throws IllegalArgumentException if any reservation data are not valid (client/trip don't exist etc)
    */
   Reservation addOldReservation(Client c, Tour t, int numInParty, int confCode) throws CapacityException ;
   
   /**
    * Cancels a reservation on the list of reservations for a particular client.
    * @param clientIndex   location of the client in the list of clients
    * @param reservationIndex location of the reservation in the client's list of reservations
    * @return the cancelled trip
    * @throws IllegalArgumentException if either index is invalid
    */
   Reservation cancelReservation(int clientIndex, int reservationIndex);
   
   /**
    * Cancels a tour and all reservations for this tour
    * @param filteredTourIndex location of the tour in the list of tours restricted to the current filters
    * @return the canceled Tour
    */
   Tour cancelTour(int filteredTourIndex);
   
   /**
    * Gets the total cost of a client's reservations
    * @param clientIndex  location of the client in the list of clients
    * @return total cost of this client's reservations
    * @throws IllegalArgumentException if clientIndex is out of range
    */
   double totalClientCost(int clientIndex);
   
   /** 
    * Get an array that lists all clients for the TripCoordinator
    * @return the array, with each client represented as a String
    */
   String[] listClients();
   
   /**
    * Get the list of tour data filtered according to filter settings
    * @return tour data list (each row represents a trip)
    */
   Object[][] filteredTourData();
   
   /** 
    * String of Reservations for a given trip
    * @param clientIndex index of client in client list
    * @return array of strings, one for each reservation
    * @throws IllegalArgumentException if clientIndex is out of range
    */
   String[] reservationsForAClient(int clientIndex);
   
   /** 
    * String of Reservations for a given tour
    * @param filteredTourIndex location of the tour in the list when filtering is applied
    * @return array of strings, one for each reservation
    * @throws IllegalArgumentException if filteredTripIndex is out of range
    */
   String[] reservationsForATour(int filteredTourIndex);
   
   /**
    * Loads file with trip/client/reservation info. 
    * @param filename name of file to read.
    * @throws IllegalArgumentException if the file contains any irregularities or a read error occurs
    */
   void loadFile(String filename);
    
   /**
    * Writes current Trips and Clients to file. 
    * @param filename file to write.
    * @throws IllegalArgumentException if file error occurs while attempting to write
    */
   void saveFile(String filename);
    
   /**
    * Returns the last used filename.
    * @return last used filename, or null if there is none
    */
   String getFilename();
}