package cz.muni.fi.travelAgency.dao;

import cz.muni.fi.travelAgency.entities.Trip;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

/**
 * Dao class for {@link Trip} entity
 *
 * @author Simona Raucinova
 */

public interface TripDao {

    /**
     * Saves trip into DB.
     * @param trip to be saved
     */
    public void create(Trip trip);

    /**
     * Finds a trip with given id.
     * @param id of trip
     * @return trip with given id
     */
    public Trip findById(Long id);

    /**
     * Finds all trips in DB.
     * @return list of trips in DB
     */
    public List<Trip> findAll();

    /**
     * Finds all trips with given destination
     * @param destination of trips
     * @return list of trips with given destination
     */
    public List<Trip> findByDestination(String destination);

    /**
     * Finds all trips starting on fromDate and ending on toDate.
     * @param fromDate day when trip starts
     * @param toDate day when trip ends
     * @return list of trips with given dates
     */
    public List<Trip> findByToDateAndFromDate(LocalDate fromDate, LocalDate toDate);

    /**
     * Updates trip.
     * @param trip to be updated
     */
    public void update (Trip trip);

    /**
     * Deletes trip.
     * @param trip to be deleted
     */
    public void delete(Trip trip);

}
