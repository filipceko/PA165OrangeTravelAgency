package cz.muni.fi.travelAgency.dao;

import cz.muni.fi.travelAgency.entities.Trip;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

/**
 * Class to implement @link{TripDao} interface.
 * @author Simona Raucinova
 */
public class TripDaoImpl implements TripDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Trip trip) {
        em.persist(trip);
    }

    @Override
    public Trip findById(Long id) {
        return em.find(Trip.class,id);
    }

    @Override
    public List<Trip> findAll() {
        return em.createQuery("select t from Trips t",Trip.class).getResultList();
    }

    @Override
    public List<Trip> findByDestination(String destination) {
        return em.createQuery("select t from Trips t where destination = :destination",Trip.class)
                .setParameter("destination",destination)
                .getResultList();
    }

    @Override
    public List<Trip> findByToDateAndFromDate(LocalDate fromDate, LocalDate toDate) {
        return em.createQuery("select t from Trips t where fromDate = :fromDate and toDate = :toDate",Trip.class)
                .setParameter("fromDate",fromDate)
                .setParameter("toDate",toDate)
                .getResultList();
    }

    @Override
    public void update(Trip trip) {
        em.merge(trip);
    }

    @Override
    public void delete(Trip trip) {
        em.remove(trip);
    }
}
