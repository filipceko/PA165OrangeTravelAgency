package cz.muni.fi.travelAgency.facade;

import cz.muni.fi.travelAgency.DTO.ExcursionCreateDTO;
import cz.muni.fi.travelAgency.DTO.ExcursionDTO;
import java.util.Collection;

/**
 *
 * @author Rajivv
 */
public interface ExcursionFacade {

    /**
     * create new excursion.
     * @param excursionCreateDTO
     */
    public Long createExcursion(ExcursionCreateDTO excursionCreateDTO);

    /**
     * Retrieves excursion with given id.
     * @param id id of excursion
     * @return excursionDTO with given id
     */
    public ExcursionDTO findExcursionById(Long id);

    /**
     * Get all excursions.
     * @return collection of all excursions
     */
    public Collection<ExcursionDTO> getAllExcurisons();

    /**
     * Retrieves excursionDTO with given destination.
     * @param destination of excursion
     * @return collection of all excursions with given destination
     */
    public Collection<ExcursionDTO> findExcursionByDestination(String destination);

    /**
     * Retrieves all excursions by TripId.
     * @param tripId of the excursion
     * @return collection of all excursions by Trip
     */
    public Collection<ExcursionDTO> findExcursionByTripId(Long tripId);
    
    /**
     * Updates excursionDTO.
     * @param excursion to be updated
     */
    void updateExcursion(ExcursionDTO excursion);

    /**
     * Remove Excursion.
     * @param excursionId to be removed
     */
    public void removeExcursion(Long excursionId);

}
