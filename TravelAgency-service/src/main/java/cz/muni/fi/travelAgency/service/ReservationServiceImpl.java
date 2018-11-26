package cz.muni.fi.travelAgency.service;

import cz.muni.fi.travelAgency.dao.ReservationDao;
import cz.muni.fi.travelAgency.entities.Reservation;
import cz.muni.fi.travelAgency.exceptions.DataAccessException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Collection;

/**
 * Reservation Service implementation
 *
 * @author Filip Cekovsky
 */
@Service
public class ReservationServiceImpl implements ReservationService {
    @Inject
    private ReservationDao reservationDao;

    @Override
    public void create(Reservation reservation) {
        if(reservation == null){
            throw new IllegalArgumentException("tried to create NULL reservation");
        }
        try {
            reservationDao.create(reservation);
        } catch (Exception e){
            throw new DataAccessException("Could not create Reservation in persistence layer", e);
        }

    }

    @Override
    public Collection<Reservation> findAll() {
        try {
            return reservationDao.findAll();
        } catch (Exception e){
            throw new DataAccessException("Could not get Reservations from the persistence layer", e);
        }
    }

    @Override
    public Reservation findById(Long id) {
        if (id == null){
            throw new IllegalArgumentException("Tried to find Reservation by NULL");
        }
        try {
            return reservationDao.findById(id);
        } catch (Exception e){
            throw new DataAccessException("Could not get Reservations from the persistence layer", e);
        }
    }

    @Override
    public Collection<Reservation> findByCustomer(Long customerId) {
        if (customerId == null){
            throw new IllegalArgumentException("Tried to find Reservation by NULL customer ID");
        }
        try {
            return reservationDao.findByCustomerId(customerId);
        } catch (Exception e){
            throw new DataAccessException("Could not get Reservations from the persistence layer", e);
        }
    }

    @Override
    public Collection<Reservation> findByTrip(Long tripId) {
        if (tripId == null){
            throw new IllegalArgumentException("Tried to find Reservation by NULL trip ID");
        }
        try {
            return reservationDao.findByTripId(tripId);
        } catch (Exception e){
            throw new DataAccessException("Could not get Reservations from the persistence layer", e);
        }
    }

    @Override
    public void update(Reservation data) {
        if (data == null){
            throw new IllegalArgumentException("Tried to update NULL Reservation");
        }
        if (data.getId() == null){
            throw new IllegalArgumentException("Tried to update reservation without ID");
        }
        try {
            reservationDao.update(data);
        } catch (Exception e){
            throw new DataAccessException("Could not update Reservation in persistence layer", e);
        }
    }

    @Override
    public void remove(Reservation reservation) {
        if (reservation == null){
            throw new IllegalArgumentException("Tried to delete NULL Reservation");
        }
        if (reservation.getId() == null){
            throw new IllegalArgumentException("Tried to delete reservation without ID");
        }
        try {
            reservationDao.remove(reservation);
        } catch (Exception e) {
            throw new DataAccessException("Could not remove Reservation from the persistence layer", e);
        }
    }

    @Override
    public Collection<Reservation> findReservationsBetween(LocalDate from, LocalDate to) {
        try {
            return reservationDao.findReservationBetween(from, to);
        } catch (Exception e){
            throw new DataAccessException("Could not get Reservations from the persistence layer", e);
        }
    }
}
