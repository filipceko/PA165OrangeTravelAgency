package cz.muni.fi.travelAgency.service;

import cz.muni.fi.travelAgency.dao.TripDao;
import cz.muni.fi.travelAgency.entities.Customer;
import cz.muni.fi.travelAgency.entities.Reservation;
import cz.muni.fi.travelAgency.entities.Trip;
import cz.muni.fi.travelAgency.exceptions.DataAccessException;
import org.springframework.stereotype.Service;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
        try {
            return tripDao.findById(id);
        } catch (IllegalArgumentException exp){
            throw new DataAccessException(exp.getMessage());
        }
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
        try {
            return tripDao.findByDestination(destination);
        } catch (IllegalArgumentException exp){
            throw new DataAccessException(exp.getMessage());
        }

    }

    public Collection<Trip> findByInterval(LocalDate fromDate, LocalDate toDate){
        if (fromDate == null || toDate == null)
        {
            throw new IllegalArgumentException("Date cannot be null.");
        }
        else if (fromDate.isAfter(toDate)){
            throw new IllegalArgumentException("From Date cannot after To Date.");
        }
        try {
            return tripDao.findByInterval(fromDate,toDate);
        } catch (IllegalArgumentException exp){
            throw new DataAccessException(exp.getMessage());
        }
    }

    public Collection<Trip> findTripBySlot(int amount){
        if (amount < 0){
            throw new IllegalArgumentException("Invalid amount of slots.");
        }
        Collection<Trip> allTrip = tripDao.findAll();
        Set<Trip> foundTrip = new HashSet<>();
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
        else if(trip.getFromDate().isAfter(trip.getToDate()))
        {
            throw new IllegalArgumentException("Cannot update trip starting date after end date.");
        }
        try {
            tripDao.create(trip);
        } catch (IllegalArgumentException exp){
            throw new DataAccessException(exp.getMessage());
        }
    }

    @Override
    public void removeTrip(Trip trip) {
        if(trip == null){
            throw new IllegalArgumentException("Cannot delete null Trip.");
        }
        else if (trip.getId() == null) {
            throw new IllegalArgumentException("Trip ID is null");
        }
        try {
            tripDao.remove(trip);
        } catch (IllegalArgumentException exp){
            throw new DataAccessException(exp.getMessage());
        }
    }

    @Override
    public void updateTrip(Trip trip) {
        if (trip == null)
        {
            throw new IllegalArgumentException("Cannot update trip with destination not set or null.");
        }
        else if(trip.getDestination().isEmpty() || trip.getDestination() == null)
        {
            throw new IllegalArgumentException("Cannot update trip with destination not set or null.");
        }
        else if(trip.getFromDate().isAfter(trip.getToDate()))
        {
            throw new IllegalArgumentException("Cannot update trip starting date after end date.");
        }
        try {
            tripDao.update(trip);
        } catch (IllegalArgumentException exp){
            throw new DataAccessException(exp.getMessage());
        }
    }

     @Override
     public Collection<Customer> getAllCustomers(Trip trip) {
         Collection<Reservation> reservations = tripDao.findById(trip.getId()).getReservations();
         Set<Customer> customers = new HashSet<>();
         for (Reservation reservation : reservations){
             customers.add(reservation.getCustomer());
         }

         return customers;
     }


 }
