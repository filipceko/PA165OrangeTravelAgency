package cz.muni.fi.travelAgency.facade;

import cz.muni.fi.travelAgency.DTO.CheapTravelDTO;
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
    void createTrip(TripDTO trip);

    /**
     * Get Trip by provided trip id
     *
     * @return
     */
    TripDTO getTripById(long id);

    /**
     * Get all trips
     *
     * @return
     */
    Collection<TripDTO> getAllTrips();

    /**
     * Get Trips by provided destination
     *
     * @param destination
     * @return
     */
    Collection<TripDTO> getTripByDestination(String destination);

    /**
     * Get Trips by provided from date and to date
     *
     * @param fromDate
     * @param toDate
     * @return
     */
    Collection<TripDTO> getTripByInterval(LocalDate fromDate, LocalDate toDate);

    /**
     * Get Trip by available slots
     *
     * @param amount
     * @return
     */
    Collection<TripDTO> getTripBySlot(int amount);

    /**
     * Update existing trip
     *
     * @param trip
     */
    void updateTrip(TripDTO trip);

    /**
     * Remove Trip
     *
     * @param trip
     */
    void removeTrip(TripDTO trip);

    /**
     * Retrieves a biggest sub-set of non-fully booked trips and excursions available
     * for given amount of money
     * @param money available amount
     * @return DTO carrying selected Trips and Excursions
     */
    CheapTravelDTO tripsForMoney(Double money);
}
