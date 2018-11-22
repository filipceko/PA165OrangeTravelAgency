package cz.muni.fi.travelAgency.service;

import cz.muni.fi.travelAgency.entities.Customer;
import cz.muni.fi.travelAgency.entities.Trip;
import java.time.LocalDate;
import java.util.Collection;

/**
 * An interface that defines a service access to the {@link Trip} entity.
 * @author Rithy Ly
 */

public interface TripService {
    /**
     * Finds a trip with given id.
     * @param id of trip
     * @return trip with given id
     */
    public Trip findById(Long id);

    /**
     * Finds all trips.
     * @return collection of trips in DB
     */
    public Collection<Trip> findAll();

    /**
     * Finds all trips with given destination
     * @param destination of trips
     * @return collection of trips with given destination
     */
    public Collection<Trip> findByDestination(String destination);

    /**
     * Finds all trips in interval fromDate and toDate.
     * @param fromDate day when trips start
     * @param toDate   day when trips end
     * @return collection of trips with given dates
     */
    public Collection<Trip> findByInterval(LocalDate fromDate, LocalDate toDate);

    /**
     * Finds all trips available by providing number of slots.
     * @param amount number of slots available
     * @return collection of trips with given dates
     */
    public Collection<Trip> findByAvailableSlots(int amount);

    /**
     * Create Trip.
     * @param trip to be saved
     */
    public void createTrip(Trip trip);

    /**
     * Deletes trip.
     * @param trip to be deleted
     */
    public void removeTrip(Trip trip);

    /**
     * Updates trip.
     * @param trip to be updated
     */
    public void updateTrip(Trip trip);

    /**
     * Finds all customers which have a reservation for trip.
     */

    public Collection<Customer> getAllCustomers(Trip trip);

}