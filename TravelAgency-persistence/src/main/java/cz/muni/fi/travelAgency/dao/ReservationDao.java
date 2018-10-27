package cz.muni.fi.travelAgency.dao;

import cz.muni.fi.travelAgency.entities.Customer;
import cz.muni.fi.travelAgency.entities.Reservation;
import cz.muni.fi.travelAgency.entities.Trip;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

/**
 * Reservation DAO interface
 *
 * @author Rithy
 */
public interface ReservationDao {

    /**
     * Add new Reservation into database
     * @param reservation to be added
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
     * @return reservation with the given id
     */
    Reservation findById(Long id);

    /**
     * Get Reservation by customer
     * @param customer to retrieve reservations for
     * @return collection of reservations by the given customer
     */
    Collection<Reservation> findByCustomer(Customer customer);

    /**
     * Get Reservation by trip
     * @param trip to retrieve reservations for
     * @return collection of reservations with the given trip
     */
    Collection<Reservation> findByTrip(Trip trip);

    /**
     * Update existing Reservation
     * @param reservation to be updated
     */
    void update(Reservation reservation);

    /**
     * Remove existing Reservation
     * @param reservation to be deleted
     */
    void remove(Reservation reservation);

    /**
     * Get List of Reservation by providing from date and to date
     * @param startDate of the interval
     * @param endDate   of the interval
     * @return collection of reservations with the given date
     */
    Collection<Reservation> findReservationBetween(LocalDate startDate, LocalDate endDate);
}


