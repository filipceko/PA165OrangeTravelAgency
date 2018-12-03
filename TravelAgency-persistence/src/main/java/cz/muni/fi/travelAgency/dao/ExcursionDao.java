package cz.muni.fi.travelAgency.dao;

import cz.muni.fi.travelAgency.entities.Excursion;

import java.util.Collection;

/**
 * DAO class for {@link Excursion} entity
 *
 * @author Rajivv
 */
public interface ExcursionDao {

    /**
     * Stores excursion into the DB
     *
     * @param excursion to be stored
     * @throws IllegalArgumentException                      if input is NULL
     * @throws javax.validation.ConstraintViolationException if parameter violates constraints
     */
    void create(Excursion excursion);

    /**
     * Retrieves excursion with given ID
     *
     * @param id of the excursion
     * @return excursion with given ID or NULL if none found
     * @throws IllegalArgumentException if id is NULL
     */
    Excursion findById(Long id);

    /**
     * Retrieves all excursions
     *
     * @return collection of all excursion
     */
    Collection<Excursion> findAll();

    /**
     * Finds list of excursion with given destination
     *
     * @param destination of excursion
     * @return collection of excursions with given destination
     * @throws IllegalArgumentException if destination is NULL
     */
    Collection<Excursion> findByDestination(String destination);

    /**
     * Retrieves all excursions by Trip.
     *
     * @param tripId of the excursion
     * @return collection of all excursions by TripId
     * @throws IllegalArgumentException if parameter is NULL or not saved yet
     */
    Collection<Excursion> findByTripId(Long tripId);

    /**
     * Updates given excursion's data in the DB
     *
     * @param excursion to be updated
     * @throws IllegalArgumentException                                   if parameter is NULL or not saved yet
     * @throws org.springframework.transaction.TransactionSystemException if constraints are violated
     */
    void update(Excursion excursion);

    /**
     * Erases data for given excursion form the DB
     *
     * @param excursion to be erased
     * @throws IllegalArgumentException                      when NULL as parameter
     * @throws javax.validation.ConstraintViolationException when parameter is not valid
     */
    void remove(Excursion excursion) throws IllegalArgumentException;

}
