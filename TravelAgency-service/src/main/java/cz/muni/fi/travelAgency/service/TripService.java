package cz.muni.fi.travelAgency.service;

import cz.muni.fi.travelAgency.entities.Customer;
import cz.muni.fi.travelAgency.entities.Excursion;
import cz.muni.fi.travelAgency.entities.Trip;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * An interface that defines a service access to the {@link Trip} entity.
 *
 * @author Rithy Ly
 */
public interface TripService {
    /**
     * Finds a trip with given id.
     *
     * @param id of trip
     * @return trip with given id
     */
    Trip findById(Long id);

    /**
     * Finds all trips.
     *
     * @return collection of trips in DB
     */
    Collection<Trip> findAll();

    /**
     * Finds all trips with given destination
     *
     * @param destination of trips
     * @return collection of trips with given destination
     */
    Collection<Trip> findByDestination(String destination);

    /**
     * Finds all trips in interval fromDate and toDate.
     *
     * @param fromDate day when trips start
     * @param toDate   day when trips end
     * @return collection of trips with given dates
     */
    Collection<Trip> findByInterval(LocalDate fromDate, LocalDate toDate);

    /**
     * Finds all trips available by providing number of slots.
     *
     * @param amount number of slots available
     * @return collection of trips with given dates
     */
    Collection<Trip> findTripBySlot(int amount);

    /**
     * Create Trip.
     *
     * @param trip to be saved
     */
    void createTrip(Trip trip);

    /**
     * Deletes trip.
     *
     * @param trip to be deleted
     */
    void removeTrip(Trip trip);

    /**
     * Updates trip.
     *
     * @param trip to be updated
     */
    void updateTrip(Trip trip);

    /**
     * Finds all customers which have a reservation for trip.
     *
     * @param trip to get the customers for
     * @return Collection of customers who booked the trip
     *
     * @author Simona Raucinova
     */
    Collection<Customer> getAllCustomers(Trip trip);
}