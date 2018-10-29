package cz.muni.fi.travelAgency.dao;

import cz.muni.fi.travelAgency.entities.Excursion;
import cz.muni.fi.travelAgency.entities.Trip;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link ExcursionDao}
 *
 * @author Rajivv
 */
@Repository
@Transactional
public class ExcursionDaoImpl implements ExcursionDao {

    /**
     * Entity manager for this class.
     */
    @PersistenceContext
    private EntityManager eManager;

    @Override
    public void create(Excursion excursion) {
        eManager.persist(excursion);
    }

    @Override
    public Excursion findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Find By Id was called with NULL argument");
        }
        return eManager.find(Excursion.class, id);
    }

    @Override
    public Collection<Excursion> findAll() {
        return eManager.createQuery("select e from Excursion e", Excursion.class).getResultList();

    }

    @Override
    public Collection<Excursion> findByDestination(String destination) {
        if (destination == null) {
            throw new IllegalArgumentException("Find By Destination was called with NULL argument");
        }
        return eManager.createQuery("SELECT e from Excursion e WHERE e.destination LIKE :destination", Excursion.class)
                .setParameter("destination", "%" + destination + "%")
                .getResultList();
    }

    @Override
    public Collection<Excursion> findByTrip(Trip trip) {
        TypedQuery<Excursion> query = eManager.createQuery("SELECT e FROM Excursion e WHERE e.trip = :tripId", Excursion.class);
        query.setParameter("tripId", trip);
        return query.getResultList();
    }

    @Override
    public void update(Excursion excursion) {
        if (excursion == null) {
            throw new IllegalArgumentException("tried to update NULL excursion");
        }
        if (findById(excursion.getId()) == null) {
            throw new IllegalArgumentException("Excursion must be saved before editing");
        }
        eManager.merge(excursion);
    }

    @Override
    public void remove(Excursion excursion) {
        if (excursion == null) {
            throw new IllegalArgumentException("Can not delete NULL excursion");
        }
        if (findById(excursion.getId()) != null) {
            Trip trip = excursion.getTrip();
            eManager.remove(eManager.merge(excursion));
            trip.removeExcursion(excursion);
            eManager.merge(trip);
        } else {
            throw new IllegalArgumentException("Excursion must be saved before delete");
        }
    }
}
