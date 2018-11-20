package cz.muni.fi.travelAgency.service;

import cz.muni.fi.travelAgency.entities.Reservation;

import java.time.LocalDate;
import java.util.Collection;

/**
 * Reservation Service Interface
 *
 * @author Filip Čekovský
 */
public interface ReservationService {
    /**
     * Creates reservation with given data
     *
     * @param reservation in the state that should be save
     */
    void create(Reservation reservation);

    /**
     * Retrieves all available reservations
     *
     * @return Collection of available reservations
     */
    Collection<Reservation> findAll();

    /**
     * Retrieves reservation with given ID
     *
     * @param id of the reservation to retrieve
     * @return retrieved reservation
     */
    Reservation findById(Long id);

    /**
     * Retrieves all reservations made by customer with given ID
     *
     * @param customerId ID of customer to retrieve for
     * @return Collection of reservations
     */
    Collection<Reservation> findByCustomer(Long customerId);

    /**
     * Retrieves all reservations for a trip with given ID
     *
     * @param tripId ID of the trip to retrieve for
     * @return Collection of reservations
     */
    Collection<Reservation> findByTrip(Long tripId);

    /**
     * Updates reservation in the DB with given data
     *
     * @param data Reservation filled with updated data
     */
    void update(Reservation data);

    /**
     * Removes given reservation
     *
     * @param reservation to delete
     */
    void remove(Reservation reservation);

    /**
     * Retrieves all reservations made between given dates.
     *
     * @param from LocalDate earliest reservations could be made. If null treated as infinity
     * @param to   LocalDate last reservations could be made. If null treated as infinity
     * @return Collection of reservations made between the given dates
     */
    Collection<Reservation> findReservationsBetween(LocalDate from, LocalDate to);
}
