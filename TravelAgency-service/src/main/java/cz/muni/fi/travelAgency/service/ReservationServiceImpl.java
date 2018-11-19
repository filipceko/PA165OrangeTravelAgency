package cz.muni.fi.travelAgency.service;

import cz.muni.fi.travelAgency.dao.ReservationDao;
import cz.muni.fi.travelAgency.entities.Reservation;
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
        reservationDao.create(reservation);
    }

    @Override
    public Collection<Reservation> findAll() {
        return reservationDao.findAll();
    }

    @Override
    public Reservation findById(Long id) {
        return reservationDao.findById(id);
    }

    @Override
    public Collection<Reservation> findByCustomer(Long customerId) {
        return reservationDao.findByCustomer(customerId);
    }

    @Override
    public Collection<Reservation> findByTrip(Long tripId) {
        return reservationDao.findByTrip(tripId);
    }

    @Override
    public void update(Reservation data) {
        reservationDao.update(data);
    }

    @Override
    public void remove(Reservation reservation) {
        reservationDao.remove(reservation);
    }

    @Override
    public Collection<Reservation> findReservationsBetween(LocalDate from, LocalDate to) {
        return reservationDao.findReservationBetween(from, to);
    }
}
