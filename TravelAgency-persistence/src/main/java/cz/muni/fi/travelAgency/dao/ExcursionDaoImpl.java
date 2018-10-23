package cz.muni.fi.travelAgency.dao;

import cz.muni.fi.travelAgency.entities.Excursion;
import cz.muni.fi.travelAgency.entities.Trip;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

/**
 * Implementation of {@link ExcursionDao}
 * @author xrajivv
 */
@Repository
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
        return eManager.find(Excursion.class, id);
    }

    @Override
    public List<Excursion> findAll() {
        return eManager.createQuery("select e from Excursion e", Excursion.class).getResultList();

    }
    @Override
    public List<Excursion> findByTrip(Trip trip) {
        TypedQuery<Excursion> query = eManager.createQuery("SELECT e FROM Excursion e WHERE e.trip = :tripId", Excursion.class);
        query.setParameter("tripId", trip);
        return query.getResultList();
    }

    @Override
    public void update(Excursion excursion) {
        eManager.merge(excursion);
    }

    @Override
    public void remove(Excursion excursion) throws IllegalArgumentException {
        eManager.remove(excursion);
    }

}
