package cz.muni.fi.travelAgency.service;

import cz.muni.fi.travelAgency.dao.ExcursionDao;
import cz.muni.fi.travelAgency.entities.Excursion;
import cz.muni.fi.travelAgency.entities.Trip;
import java.util.Collection;
import javax.inject.Inject;
import org.springframework.stereotype.Service;


/**
 * Implementation of the {@link ExcursionService}. This class is part of the
 * service module of the application that provides the implementation of the
 * business logic (main logic of the application).
 *
 * @author Rajivv
 */
@Service
public class ExcursionServiceImpl implements ExcursionService{
    @Inject
    private ExcursionDao excursionDao;

    @Override
    public Collection<Excursion> getAllExcursions() {
        return excursionDao.findAll();
    }

    @Override
    public void createExcursion(Excursion excursion) {
        excursionDao.create(excursion);
    }

    @Override
    public void updateExcurison(Excursion excursion) {
         if (excursion == null) {
            throw new IllegalArgumentException("tried to update NULL excursion");
        }
        excursionDao.update(excursion);
    }

    @Override
    public void deleteExcursion(Excursion excursion) {
       if (excursion == null) {
            throw new IllegalArgumentException("Can not remove NULL excursion");
        }
        excursionDao.remove(excursion);
    }

    @Override
    public Excursion findExcursionById(Long excursionId) {
       return excursionDao.findById(excursionId);
    }

    @Override
    public Collection<Excursion> findExcursionByDestination(String destination) {
       if (destination == null) {
            throw new IllegalArgumentException("Find By Destination was called with NULL argument");
        }
        return excursionDao.findByDestination(destination);
    }

    @Override
    public Collection<Excursion> findExcursionByTrip(Trip trip) {
       return excursionDao.findByTrip(trip);
    }
    
}
