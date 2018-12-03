package cz.muni.fi.travelAgency.dao;

import cz.muni.fi.travelAgency.entities.Customer;
import cz.muni.fi.travelAgency.entities.Reservation;
import cz.muni.fi.travelAgency.entities.Trip;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.Collection;

/**
 * Implementation of {@link ReservationDao}
 *
 * @author Rithy
 */
@Repository
public class ReservationDaoImpl implements ReservationDao {

    /**
     * Entity manager for this class.
     */
    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Reservation reservation) {
        em.persist(reservation);
    }

    @Override
    public Collection<Reservation> findAll() {
        return em.createQuery("SELECT e FROM Reservation e ORDER BY e.reserveDate DESC", Reservation.class).getResultList();
    }

    @Override
    public Reservation findById(Long id) {
        return em.find(Reservation.class, id);
    }

    @Override
    public Collection<Reservation> findByCustomerId(Long customerId) {
        TypedQuery<Reservation> query = em.createQuery("SELECT e FROM Reservation e WHERE e.customer.id = :customerId",
                Reservation.class);
        query.setParameter("customerId", customerId);
        return query.getResultList();
    }

    @Override
    public Collection<Reservation> findByTripId(Long tripId) {
        TypedQuery<Reservation> query = em.createQuery("SELECT e FROM Reservation e WHERE e.trip.id = :tripId",
                Reservation.class);
        query.setParameter("tripId", tripId);
        return query.getResultList();
    }

    @Override
    public Collection<Reservation> findReservationBetween(LocalDate startDate, LocalDate endDate) {
        if (startDate == null && endDate != null) {
            TypedQuery<Reservation> query = em.createQuery("SELECT e FROM Reservation e WHERE e.reserveDate <= :endDate", Reservation.class);
            query.setParameter("endDate", endDate);
            return query.getResultList();
        }
        if (startDate != null && endDate == null) {
            TypedQuery<Reservation> query = em.createQuery("SELECT e FROM Reservation e WHERE e.reserveDate >= :startDate", Reservation.class);
            query.setParameter("startDate", startDate);
            return query.getResultList();
        }
        if (startDate == null) {
            //both are null
            return findAll();
        }
        //none is null
        TypedQuery<Reservation> query = em.createQuery("SELECT e FROM Reservation e WHERE e.reserveDate BETWEEN :startDate AND :endDate",
                Reservation.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }

    @Override
    public void update(Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("Tried to update NULL!");
        }
        if (findById(reservation.getId()) == null) {
            throw new IllegalArgumentException("Tried to update Reservation that was not saved before.");
        }
        em.merge(reservation);
    }

    @Override
    public void remove(Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("Tried to remove NULL!");
        }
        if (findById(reservation.getId()) != null) {
            Trip trip = reservation.getTrip();
            trip.removeReservation(reservation);
            Customer customer = reservation.getCustomer();
            customer.removeReservation(reservation);
            em.remove(em.merge(reservation));
            em.merge(trip);
            em.merge(customer);
        } else {
            throw new IllegalArgumentException("Tried to Remove Reservation that was not saved before.");
        }

    }
}
