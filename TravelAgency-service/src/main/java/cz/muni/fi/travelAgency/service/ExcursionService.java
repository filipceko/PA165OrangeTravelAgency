package cz.muni.fi.travelAgency.service;

import cz.muni.fi.travelAgency.entities.Excursion;
import cz.muni.fi.travelAgency.entities.Trip;
import java.util.Collection;
import org.springframework.stereotype.Service;

/**
 * An interface that defines a service access to the {@link Excursion} entity.
 *
 * @author Rajivv
 */
@Service
public interface ExcursionService {

    /**
     * Get all excursions.
     * @return collection of all excursions
     */
    Collection<Excursion> getAllExcursions();

    /**
     * Created excursion.
     * @param excursion to be created
     */
    void createExcursion(Excursion excursion);

    /**
     * Updates excursion.
     * @param excursion to be updated
     */
    void updateExcurison(Excursion excursion);

    /**
     * Delete excursion.
     * @param excursion to be deleted
     */
    void deleteExcursion(Excursion excursion);

    /**
     * Retrieves excursion with given id.
     * @param excursionId id of excursion
     * @return excursion with given id
     */
    Excursion findExcursionById(Long excursionId);

    /**
     * Retrieves excursion with given destination.
     * @param destination of excursion
     * @return collection of excursions with given destination
     */
    Collection<Excursion> findExcursionByDestination(String destination);

    /**
     * Retrieves all excursions by trip with given id.
     * @param trip of the excursion
     * @return collection of excursions
     */
    Collection<Excursion> findExcursionByTrip(Trip trip);
}
