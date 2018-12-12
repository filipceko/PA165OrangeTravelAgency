package cz.muni.fi.travelAgency.facade;

import cz.muni.fi.travelAgency.DTO.ExcursionCreateDTO;
import cz.muni.fi.travelAgency.DTO.ExcursionDTO;
import cz.muni.fi.travelAgency.DTO.ExcursionEditDTO;

import java.util.Collection;

/**
 * Excursion facade interface
 *
 * @author Rajivv
 */
public interface ExcursionFacade {

    /**
     * create new excursion handed over and update's it's ID.
     *
     * @param excursionDTO to be created
     */
    Long createExcursion(ExcursionCreateDTO excursionDTO);

    /**
     * Retrieves excursion with given id.
     *
     * @param id id of excursion
     * @return excursionDTO with given id
     */
    ExcursionDTO findExcursionById(Long id);

    /**
     * Retrieves all excursions.
     *
     * @return collection of all excursions
     */
    Collection<ExcursionDTO> getAllExcursions();

    /**
     * Retrieves excursionDTO with given destination.
     *
     * @param destination of excursion
     * @return collection of all excursions with given destination
     */
    Collection<ExcursionDTO> findExcursionByDestination(String destination);

    /**
     * Retrieves all excursions by TripId.
     *
     * @param tripId of the excursion
     * @return collection of all excursions by Trip
     */
    Collection<ExcursionDTO> findExcursionByTripId(Long tripId);

    /**
     * Updates excursionDTO.
     *
     * @param excursionDTO to be updated
     */
    void updateExcursion(ExcursionEditDTO excursionDTO);

    /**
     * Remove Excursion.
     *
     * @param excursionDTO to be removed
     */
    void deleteExcursion(ExcursionDTO excursionDTO);

}
