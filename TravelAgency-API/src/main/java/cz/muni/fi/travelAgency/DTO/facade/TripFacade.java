package cz.muni.fi.travelAgency.DTO.facade;

import cz.muni.fi.travelAgency.DTO.CustomerDTO;
import cz.muni.fi.travelAgency.DTO.ExcursionDTO;
import cz.muni.fi.travelAgency.DTO.TripDTO;
import java.time.LocalDate;
import java.util.List;

public interface TripFacade {
    /**
     * Create new Trip
     * @param p
     */
    public void createTrip(TripDTO p);

    /**
     * Get Trip by provided trip id
     * @return
     */
    public TripDTO getTripById();

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
     * Get Excursion by provided trip
     * @param trip
     * @return
     */
    public List<ExcursionDTO> getExcursionByTrip(TripDTO trip);

    /**
     * Get customers by provided trip
     * @param trip
     * @return
     */
    public List<CustomerDTO> getCustomerByTripe(TripDTO trip);

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
