package cz.muni.fi.travelAgency.facade;

import cz.muni.fi.travelAgency.DTO.ReservationDTO;

import java.time.LocalDate;
import java.util.Collection;

/**
 * Reservation facade interface
 *
 * @author Filip Cekovsky
 */
public interface ReservationFacade {
    /**
     * Creates reservation handed over and update's it's ID.
     *
     * @param reservationDTO to be created
     */
    void createReservation(ReservationDTO reservationDTO);

    /**
     * Retrieves all reservations.
     *
     * @return Collection of reservations
     */
    Collection<ReservationDTO> getAllReservations();

    /**
     * Retrieves reservation with given ID.
     *
     * @param id of the reservation
     * @return the reservation
     */
    ReservationDTO getById(Long id);

    /**
     * Retrieves reservation made by given customer.
     *
     * @param customerId ID of the customer
     * @return collection of reservations
     */
    Collection<ReservationDTO> getByCustomer(Long customerId);

    /**
     * Retrieves reservations for given trip.
     *
     * @param tripId ID of the trip
     * @return Collection of reservations
     */
    Collection<ReservationDTO> getByTrip(Long tripId);

    /**
     * Updates given reservation's data.
     *
     * @param data of the reservation to be updated
     */
    void update(ReservationDTO data);

    /**
     * Removes given reservation.
     *
     * @param reservationDTO to be removed
     */
    void delete(ReservationDTO reservationDTO);

    /**
     * Retrieves all reservation made in an interval of time.
     *
     * @param from - starting date, if null treated as infinity
     * @param to   - until date, if null treated as infinity
     * @return Collection of reservations
     */
    Collection<ReservationDTO> getReservationByInterval(LocalDate from, LocalDate to);
}
