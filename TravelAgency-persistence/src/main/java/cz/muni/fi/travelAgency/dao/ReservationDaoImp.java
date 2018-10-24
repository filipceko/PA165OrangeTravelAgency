package cz.muni.fi.travelAgency.dao;

import cz.muni.fi.travelAgency.entities.Customer;
import cz.muni.fi.travelAgency.entities.Reservation;
import cz.muni.fi.travelAgency.entities.Trip;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.Date;

/**
 * Implementation of {@link ReservationDao}
 *
 * @author Rithy
 */
@Repository
@Transactional
public class ReservationDaoImp implements ReservationDao {

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
    public Collection<Reservation> findByCustomer(Customer customer) {
        TypedQuery<Reservation> query = em.createQuery("SELECT e FROM Reservation e WHERE e.customer = :customerId",
                Reservation.class);
        query.setParameter("customerId", customer);
        return query.getResultList();
    }

    @Override
    public Collection<Reservation> findByTrip(Trip trip) {
        TypedQuery<Reservation> query = em.createQuery("SELECT e FROM Reservation e WHERE e.trip = :tripId",
                Reservation.class);
        query.setParameter("tripId", trip);
        return query.getResultList();
    }

    @Override
    public Collection<Reservation> findReservationBetween(Date startDate, Date endDate) {
        TypedQuery<Reservation> query = em.createQuery("SELECT e FROM Reservation e WHERE e.reserveDate BETWEEN :startDate AND :endDate",
                Reservation.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }

    @Override
    public void update(Reservation reservation) {
        em.merge(reservation);
    }

    @Override
    public void remove(Reservation reservation) {
        em.remove(em.merge(reservation));
    }
}
