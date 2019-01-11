package cz.muni.fi.travelAgency.dao;

import cz.muni.fi.travelAgency.entities.Excursion;
import cz.muni.fi.travelAgency.entities.Reservation;
import cz.muni.fi.travelAgency.entities.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;

/**
 * Implementation of {@link ExcursionDao}
 *
 * @author Rajivv
 */
@Repository
public class ExcursionDaoImpl implements ExcursionDao {

    /**
     * Entity manager for this class.
     */
    @PersistenceContext
    private EntityManager eManager;

    /**
     * Data access object for reservations
     */
    @Autowired
    private ReservationDao reservationDao;

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
    public Collection<Excursion> findByTripId(Long tripId) {
        TypedQuery<Excursion> query = eManager.createQuery("SELECT e FROM Excursion e WHERE e.trip.id = :tripId", Excursion.class);
        query.setParameter("tripId", tripId);
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
            throw new IllegalArgumentException("Can not remove NULL excursion");
        }
        if (findById(excursion.getId()) != null) {
            Trip trip = excursion.getTrip();
            trip.removeExcursion(excursion);
            for (Reservation reservation : reservationDao.findByTripId(trip.getId())) {
                if (reservation.getExcursions().contains(excursion)) {
                    reservation.removeExcursion(excursion);
                    reservationDao.update(reservation);
                }
            }
            eManager.remove(eManager.merge(excursion));
        } else {
            throw new IllegalArgumentException("Excursion must be saved before remove");
        }


    }
}
