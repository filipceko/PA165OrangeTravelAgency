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
        try {
            return reservationDao.findById(id);
        } catch (Exception e){
            throw new DataAccessException("Could not get Reservations from the persistence layer", e);
        }
    }

    @Override
    public Collection<Reservation> findByCustomer(Long customerId) {
        try {
            return reservationDao.findByCustomerId(customerId);
        } catch (Exception e){
            throw new DataAccessException("Could not get Reservations from the persistence layer", e);
        }
    }

    @Override
    public Collection<Reservation> findByTrip(Long tripId) {
        try {
            return reservationDao.findByTripId(tripId);
        } catch (Exception e){
            throw new DataAccessException("Could not get Reservations from the persistence layer", e);
        }
    }

    @Override
    public void update(Reservation data) {
        try {
            reservationDao.update(data);
        } catch (Exception e){
            throw new DataAccessException("Could not update Reservation in persistence layer", e);
        }
    }

    @Override
    public void remove(Reservation reservation) {
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
