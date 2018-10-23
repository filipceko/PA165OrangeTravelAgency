package cz.muni.fi.travelAgency.dao;
import cz.muni.fi.travelAgency.entities.Customer;
import cz.muni.fi.travelAgency.entities.Excursion;
import cz.muni.fi.travelAgency.entities.Reservation;
import cz.muni.fi.travelAgency.entities.Trip;

import java.util.Date;
import java.util.List;

public interface ReservationDao {

    void create(Reservation reservation);

    List<Reservation> findAll();

    Reservation findById(Long id);

    List<Reservation> findByCustomer(Customer customer);

    List<Reservation> findByTrip(Trip trip);

    void update(Reservation reservation);

    void remove(Reservation reservation)  throws IllegalArgumentException;

    List<Reservation> findReservationBetween(Date startDate, Date endDate);


}


