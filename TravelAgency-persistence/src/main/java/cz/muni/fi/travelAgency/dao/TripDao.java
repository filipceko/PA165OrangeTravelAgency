package cz.muni.fi.travelAgency.dao;

import cz.muni.fi.travelAgency.entities.Trip;

import java.time.LocalDate;
import java.util.Collection;

/**
 * DAO class for {@link Trip} entity
 *
 * @author Simona Raucinova
 */

public interface TripDao {

    /**
     * Saves trip into DB.
     * @param trip to be saved
     */
    void create(Trip trip);

    /**
     * Finds a trip with given id.
     * @param id of trip
     * @return trip with given id
     */
    Trip findById(Long id);

    /**
     * Finds all trips in DB.
     * @return collection of trips in DB
     */
    Collection<Trip> findAll();

    /**
     * Finds all trips with given destination
     * @param destination of trips
     * @return collection of trips with given destination
     */
    Collection<Trip> findByDestination(String destination);

    /**
     * Finds all trips in interval fromDate and toDate.
     * @param fromDate day when trips start
     * @param toDate   day when trips end
     * @return collection of trips with given dates
     */
    Collection<Trip> findByInterval(LocalDate fromDate, LocalDate toDate);

    /**
     * Updates trip.
     * @param trip to be updated
     */
    void update(Trip trip);

    /**
     * Deletes trip.
     * @param trip to be deleted
     */
    void remove(Trip trip);

}
