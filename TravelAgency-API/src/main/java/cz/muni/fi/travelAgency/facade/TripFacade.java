package cz.muni.fi.travelAgency.facade;

import cz.muni.fi.travelAgency.DTO.CheapTravelDTO;
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
     * @param trip to be created
     */
    void createTrip(TripDTO trip);

    /**
     * Get Trip by provided trip id
     *
     * @return Obtained TripDTO
     */
    TripDTO getTripById(long id);

    /**
     * Get all trips
     *
     * @return Collection of all trips
     */
    Collection<TripDTO> getAllTrips();

    /**
     * Get Trips by provided destination
     *
     * @param destination to retrieve trips to
     * @return Collection of all trips to given destination
     */
    Collection<TripDTO> getTripByDestination(String destination);

    /**
     * Get Trips that happen in given interval
     *
     * @param fromDate earliest starting date
     * @param toDate   latest return date
     * @return Collection ot trips
     */
    Collection<TripDTO> getTripByInterval(LocalDate fromDate, LocalDate toDate);

    /**
     * Get Trip by available slots
     *
     * @param amount of slots that should be available
     * @return Collection of trips with enough available slots
     */
    Collection<TripDTO> getTripBySlot(int amount);

    /**
     * Finds all trips available in the future.
     *
     * @return collection of available future trips
     */
    Collection<TripDTO> getAvailableFutureTrip();

    /**
     * Update existing trip
     *
     * @param trip trip to update
     */
    void updateTrip(TripDTO trip);

    /**
     * Remove Trip
     *
     * @param trip to be removed
     */
    void removeTrip(TripDTO trip);

    /**
     * Retrieves a biggest sub-set of non-fully booked trips and excursions available
     * for given amount of money
     *
     * @param money available amount
     * @return DTO carrying selected Trips and Excursions
     */
    CheapTravelDTO tripsForMoney(Double money);

    /**
     * Finds all customers which have a reservation for trip.
     *
     * @author Simona Raucinova
     */
    Collection<CustomerDTO> getAllCustomers(TripDTO trip);
}
