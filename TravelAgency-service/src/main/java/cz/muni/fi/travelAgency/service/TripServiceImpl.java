package cz.muni.fi.travelAgency.service;

import cz.muni.fi.travelAgency.dao.TripDao;
import cz.muni.fi.travelAgency.entities.Trip;
import org.springframework.stereotype.Service;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

 /**
 * Implementation of the {@link TripService}. This class is part of the
 * service module of the application that provides the implementation of the
 * business logic.
 * @author Rithy Ly
 */

@Service
public class TripServiceImpl implements TripService {

    @Inject
    private TripDao tripDao;

    @Override
    public Trip findById(Long id) {
        return tripDao.findById(id);
    }

    @Override
    public Collection<Trip> findAll() {
        return tripDao.findAll();
    }

    @Override
    public Collection<Trip> findByDestination(String destination){
        if (destination == null || destination.isEmpty()){
            throw new IllegalArgumentException("Cannot find Trip with null destination or empty string.");
        }
        return tripDao.findByDestination(destination);
    }

    public Collection<Trip> findByInterval(LocalDate fromDate, LocalDate toDate){
        if (fromDate == null || toDate == null)
        {
            throw new IllegalArgumentException("Date cannot be null.");
        }
        else if (fromDate.isAfter(toDate)){
            throw new IllegalArgumentException("From Date cannot after To Date.");
        }
        return tripDao.findByInterval(fromDate,toDate);
    }

    public Collection<Trip> findByAvailableSlots(int amount){
        if (amount < 0){
            throw new IllegalArgumentException("Invalid amount of slots.");
        }
        Collection<Trip> allTrip = tripDao.findAll();
        Collection<Trip> foundTrip = Collections.EMPTY_LIST;
        for (Trip t : allTrip) {
            if (t.getCapacity() >= amount){
               foundTrip.add(t);
            }
        }
        return foundTrip;
    }

    @Override
    public void createTrip(Trip trip) {
        if(trip == null){
            throw new IllegalArgumentException("Cannot create null Trip.");
        }
        tripDao.create(trip);
    }

    @Override
    public void removeTrip(Trip trip) {
        if(trip == null){
            throw new IllegalArgumentException("Cannot delete null Trip.");
        }
        tripDao.remove(trip);
    }

    @Override
    public void updateTrip(Trip trip) {
        if(trip.getDestination().isEmpty() || trip.getDestination() == null)
        {
            throw new IllegalArgumentException("Cannot update trip with destination not set or null.");
        }
        else if(trip.getFromDate().isAfter(trip.getToDate()))
        {
            throw new IllegalArgumentException("Cannot update trip starting date after end date.");
        }
        tripDao.update(trip);
    }

}
