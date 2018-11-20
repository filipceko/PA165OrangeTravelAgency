package cz.muni.fi.travelAgency.dao;

import cz.muni.fi.travelAgency.entities.Reservation;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
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
        TypedQuery<Reservation> query = em.createQuery("SELECT e FROM Reservation e WHERE e.customer = :customerId",
                Reservation.class);
        query.setParameter("customerId", customerId);
        return query.getResultList();
    }

    @Override
    public Collection<Reservation> findByTripId(Long tripId) {
        TypedQuery<Reservation> query = em.createQuery("SELECT e FROM Reservation e WHERE e.trip = :tripId",
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
        if (em.find(Reservation.class, reservation.getId()) != null) {
            em.merge(reservation);
        } else {
            throw new IllegalArgumentException("Tried to update Reservation that was not saved before.");
        }
    }

    @Override
    public void remove(Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("Tried to remove NULL!");
        }
        if (em.find(Reservation.class, reservation.getId()) != null) {
            em.createQuery("delete Reservation r where r.id = :id").setParameter("id", reservation.getId()).executeUpdate();
        } else {
            throw new IllegalArgumentException("Tried to Remove Reservation that was not saved before.");
        }

    }
}
