package cz.muni.fi.travelAgency.dao;

import cz.muni.fi.travelAgency.entities.Excursion;
import cz.muni.fi.travelAgency.entities.Trip;
import java.util.List;

/**
 * dao class for {@link Excursion} entity
 * @author xrajivv
 */
public interface ExcursionDao {

    /**
     * Stores excursion into the DB.
     * @param excursion to be stored
     */
    void create(Excursion excursion);

    /**
     * Retrievs excursion with given ID.
     * @param id of the excursion
     * @return excursion with given ID
     */
    Excursion findById(Long id);

    /**
     * Retrieves all excursions.
     * @return list of all excursion
     */
    List<Excursion> findAll();

    /**
     * Retrieves all excursions by Trip.
     * @param trip of the excursion
     * @return list of all excursion by Trip
     */
    List<Excursion> findByTrip(Trip trip);

    /**
     * Updates given excursion's data in the DB
     * @param excursion to be updated
     */
    void update(Excursion excursion);

    /**
     * Erases data for given excursion form the DB.
     * @param excursion to be erased
     */
    void remove(Excursion excursion) throws IllegalArgumentException;

}
