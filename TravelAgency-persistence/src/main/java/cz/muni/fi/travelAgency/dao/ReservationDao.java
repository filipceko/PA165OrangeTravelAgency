package cz.muni.fi.travelAgency.dao;

import cz.muni.fi.travelAgency.entities.Customer;
import cz.muni.fi.travelAgency.entities.Reservation;
import cz.muni.fi.travelAgency.entities.Trip;

import java.time.LocalDate;
import java.util.Collection;
import java.time.LocalDate;

/**
 * Reservation DAO interface
 *
 * @author Rithy
 */
public interface ReservationDao {

    /**
     * Add new Reservation into database
     * @param reservation to be added
     * @throws IllegalArgumentException if input is NULL
     * @throws javax.validation.ConstraintViolationException if parameter violates constraints
     */
    void create(Reservation reservation);

    /**
     * Get all reservations
     * @return collection of Reservation in database
     */
    Collection<Reservation> findAll();

    /**
     * Get Reservation by id
     * @param id of the reservation
     * @return reservation with given ID or NULL if none found
     * @throws IllegalArgumentException if id is NULL
     */
    Reservation findById(Long id);

    /**
     * Get Reservation by customer
     * @param customer to retrieve reservations for
     * @return collection of reservations by the given customer or null if none found
     * @throws IllegalArgumentException if one of name parts is NULL
     */
    Collection<Reservation> findByCustomer(Customer customer);

    /**
     * Get Reservation by trip
     * @param trip to retrieve reservations for
     * @return collection of reservations with the given trip or null if none found
     * @throws IllegalArgumentException if one of name parts is NULL
     */
    Collection<Reservation> findByTrip(Trip trip);

    /**
     * Updates given Reservation's data in the DB
     * @param reservation to be updated
     * @throws IllegalArgumentException if parameter is NULL or not saved yet.
     * @throws org.springframework.transaction.TransactionSystemException if constraints are violated
     */
    void update(Reservation reservation);

    /**
     * Erases data for given Reservation form the DB.
     * @param reservation to be erased
     * @throws IllegalArgumentException when NULL as parameter
     * @throws javax.validation.ConstraintViolationException when parameter is not valid
     */
    void remove(Reservation reservation);

    /**
     * Get List of Reservation by providing from date and to date
     * @param startDate of the interval
     * @param endDate   of the interval
     * @return collection of reservations with the given date
     * @throws IllegalArgumentException if startDate&endDate is NULL
     */
    Collection<Reservation> findReservationBetween(LocalDate startDate, LocalDate endDate);
}


