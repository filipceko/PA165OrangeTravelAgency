package cz.muni.fi.travelAgency.facade;

import cz.muni.fi.travelAgency.DTO.TripDTO;
import java.time.LocalDate;
import java.util.List;

public interface TripFacade {
    /**
     * Create new Trip
     * @param trip
     */
    public void createTrip(TripDTO trip);

    /**
     * Get Trip by provided trip id
     * @return
     */
    public TripDTO getTripById(long id);

    /**
     * Get all trips
     * @return
     */
    public List<TripDTO> getAllTrips();

    /**
     * Get Trips by provided destination
     * @param destination
     * @return
     */
    public List<TripDTO> getTripByDestination(String destination);

    /**
     * Get Trips by provided from date and to date
     * @param fromDate
     * @param toDate
     * @return
     */
    public List<TripDTO> getTripByInterval(LocalDate fromDate, LocalDate toDate);

    /**
     * Get Trip by available slots
     * @param trip
     * @return
     */
    public List<TripDTO> getAvailableSlots(int amount);

    /**
     * Update existing trip
     * @param trip
     */
    public void updateTrip(TripDTO trip);

    /**
     * Remove Trip
     * @param trip
     */
    public void removeTrip(TripDTO trip);
}
