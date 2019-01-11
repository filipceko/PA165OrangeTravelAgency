package cz.muni.fi.travelAgency.service;

import cz.muni.fi.travelAgency.dao.ExcursionDao;
import cz.muni.fi.travelAgency.entities.Excursion;
import cz.muni.fi.travelAgency.exceptions.DataAccessLayerException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;

/**
 * Implementation of the {@link ExcursionService}. This class is part of the
 * service module of the application that provides the implementation of the
 * business logic (main logic of the application).
 *
 * @author Rajivv
 */
@Service
public class ExcursionServiceImpl implements ExcursionService {

    /**
     * Data access object for excursions
     */
    @Inject
    private ExcursionDao excursionDao;

    @Override
    public Collection<Excursion> getAllExcursions() {
        try {
            return excursionDao.findAll();
        } catch (IllegalArgumentException exp) {
            throw new DataAccessLayerException(exp.getMessage());
        }
    }

    @Override
    public void createExcursion(Excursion excursion) {
        if (excursion == null) {
            throw new IllegalArgumentException("tried to create NULL excursion");
        }
        try {
            excursionDao.create(excursion);
        } catch (IllegalArgumentException exp) {
            throw new DataAccessLayerException(exp.getMessage());
        }
    }

    @Override
    public void updateExcursion(Excursion excursion) {
        if (excursion == null) {
            throw new IllegalArgumentException("tried to update NULL excursion");
        }
        try {
            excursionDao.update(excursion);
        } catch (IllegalArgumentException exp) {
            throw new DataAccessLayerException(exp.getMessage());
        }
    }

    @Override
    public void deleteExcursion(Excursion excursion) {
        if (excursion == null) {
            throw new IllegalArgumentException("Can not remove NULL excursion");
        }
        try {
            excursionDao.remove(excursion);
        } catch (IllegalArgumentException exp) {
            throw new DataAccessLayerException(exp.getMessage());
        }
    }

    @Override
    public Excursion findExcursionById(Long excursionId) {
        if (excursionId == null) {
            throw new IllegalArgumentException("Find Excursion by Id was called with NULL argument");
        }
        try {
            return excursionDao.findById(excursionId);
        } catch (IllegalArgumentException exp) {
            throw new DataAccessLayerException(exp.getMessage());
        }
    }

    @Override
    public Collection<Excursion> findExcursionByDestination(String destination) {
        if (destination == null) {
            throw new IllegalArgumentException("Find Excursion by Destination was called with NULL argument");
        }
        try {
            return excursionDao.findByDestination(destination);
        } catch (IllegalArgumentException exp) {
            throw new DataAccessLayerException(exp.getMessage());
        }
    }

    @Override
    public Collection<Excursion> findExcursionByTripId(Long tripId) {
        if (tripId == null) {
            throw new IllegalArgumentException("Find Excursion by tripId was called with NULL argument");
        }
        try {
            return excursionDao.findByTripId(tripId);
        } catch (IllegalArgumentException exp) {
            throw new DataAccessLayerException(exp.getMessage());
        }
    }
}
