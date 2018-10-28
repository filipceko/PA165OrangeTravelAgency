package cz.muni.fi.travelAgency.dao;

import cz.muni.fi.travelAgency.entities.Trip;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.Collection;

/**
 * Class to implement @link{TripDao} interface.
 *
 * @author Simona Raucinova
 */
@Repository
@Transactional
public class TripDaoImpl implements TripDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Trip trip) {
        em.persist(trip);
    }

    @Override
    public Trip findById(Long id) {
        return em.find(Trip.class, id);
    }

    @Override
    public Collection<Trip> findAll() {
        return em.createQuery("select t from Trip t", Trip.class).getResultList();
    }

    @Override
    public Collection<Trip> findByDestination(String destination) {
        return em.createQuery("select t from Trip t where t.destination = :destination", Trip.class)
                .setParameter("destination", destination)
                .getResultList();
    }

    @Override
    public Collection<Trip> findByInterval(LocalDate fromDate, LocalDate toDate) {
        return em.createQuery("select t from Trip t where (t.fromDate between :fromDate and :toDate) " +
                                                    "and (t.toDate between :fromDate and :toDate) order by t.fromDate",
                Trip.class)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .getResultList();
    }

    @Override
    public void update(Trip trip) {
        em.merge(trip);
    }

    @Override
    public void delete(Trip trip) {
        em.remove(em.merge(trip));
    }
}
