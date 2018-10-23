package cz.muni.fi.travelAgency.dao;

import cz.muni.fi.travelAgency.entities.Customer;
import cz.muni.fi.travelAgency.entities.Reservation;
import cz.muni.fi.travelAgency.entities.Trip;
import java.util.Date;
import java.util.List;

public interface ReservationDao {

    /**
     * Add new Reservation into database
     * @param reservation
     * @return Created Reservation
     */
    Reservation create(Reservation reservation);

    /**
     * Get all reservation
     * @return List of Reservation in database
     */
    List<Reservation> findAll();

    /**
     * Get Reservation by id
     * @param id
     * @return​ Reservation retrieved with the given id
     */
    Reservation findById(Long id);

    /**
     * Get Reservation by customer
     * @param customer
     * @return Reservation retrieved with the given related customer
     */
    List<Reservation> findByCustomer(Customer customer);

    /**
     * Get Reservation by trip
     * @param trip
     * @return Reservation retrieved with the given related trip
     */
    List<Reservation> findByTrip(Trip trip);

    /**
     * Update existing Reservation
     * @param reservation
     */
    void update(Reservation reservation);

    /**
     * Remove existing Reservation
     * @param reservation
     * @throws IllegalArgumentException
     */
    void remove(Reservation reservation)  throws IllegalArgumentException;

    /**
     * Get List of Reservation by providing from date and to date
     * @param startDate and enddate
     * @return Reservation retrieved with the given date
     */
    List<Reservation> findReservationBetween(Date startDate, Date endDate);
}


