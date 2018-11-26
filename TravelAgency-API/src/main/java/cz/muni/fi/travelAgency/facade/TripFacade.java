package cz.muni.fi.travelAgency.facade;

import cz.muni.fi.travelAgency.DTO.CustomerDTO;
import cz.muni.fi.travelAgency.DTO.TripDTO;

import java.time.LocalDate;
import java.util.Collection;

/**
 * Trip facade interface
 *
 * @author Rithy Ly
 */
public interface TripFacade {

    /**
     * Create new Trip
     *
     * @param trip
     */
    public void createTrip(TripDTO trip);

    /**
     * Get Trip by provided trip id
     *
     * @return
     */
    public TripDTO getTripById(long id);

    /**
     * Get all trips
     *
     * @return
     */
    public Collection<TripDTO> getAllTrips();

    /**
     * Get Trips by provided destination
     *
     * @param destination
     * @return
     */
    public Collection<TripDTO> getTripByDestination(String destination);

    /**
     * Get Trips by provided from date and to date
     *
     * @param fromDate
     * @param toDate
     * @return
     */
    public Collection<TripDTO> getTripByInterval(LocalDate fromDate, LocalDate toDate);

    /**
     * Get Trip by available slots
     *
     * @param amount
     * @return
     */
    public Collection<TripDTO> getTripBySlot(int amount);

    /**
     * Finds all trips available in the future.
     *
     * @return collection of available future trips
     */
    public Collection<TripDTO> getAvailableFutureTrip();

    /**
     * Update existing trip
     *
     * @param trip
     */
    public void updateTrip(TripDTO trip);

    /**
     * Remove Trip
     *
     * @param trip
     */
    public void removeTrip(TripDTO trip);

    /**
     * Finds all customers which have a reservation for trip.
     * @author Simona Raucinova
     */
    public Collection<CustomerDTO> getAllCustomers(TripDTO trip);
}
