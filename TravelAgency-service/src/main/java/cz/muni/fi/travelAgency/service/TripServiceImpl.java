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
        } catch (Exception e){
            throw new DataAccessException("Could not get Trip from the persistence layer", e);
        }
    }

    @Override
    public Collection<Trip> findAll() {
        try {
            return tripDao.findAll();
        } catch (Exception e){
            throw new DataAccessException("Could not get Trip from the persistence layer", e);
        }
    }

    @Override
    public Collection<Trip> findByDestination(String destination){
        try {
            return tripDao.findByDestination(destination);
        } catch (Exception e){
            throw new DataAccessException("Could not get Trip from the persistence layer", e);
        }
    }

    @Override
    public Collection<Trip> findByInterval(LocalDate fromDate, LocalDate toDate){
        try {
            return tripDao.findByInterval(fromDate,toDate);
        } catch (Exception e){
            throw new DataAccessException("Could not get Trip from the persistence layer", e);
        }
    }

    @Override
    public Collection<Trip> findTripBySlot(int amount){
        try {
            Collection<Trip> allTrips = tripDao.findAll();
            Set<Trip> foundTrips = new HashSet<>();
            for (Trip trip : allTrips) {
                if (trip.getCapacity() >= amount){
                    foundTrips.add(trip);
                }
            }
            return foundTrips;
        } catch (Exception e){
            throw new DataAccessException("Could not get Trip from the persistence layer", e);
        }

    }

    @Override
    public void createTrip(Trip trip) {
        try {
            tripDao.create(trip);
        } catch (Exception e){
            throw new DataAccessException("Could not create Trip in persistence layer", e);
        }
    }

    @Override
    public void removeTrip(Trip trip) {
        try {
            tripDao.remove(trip);
        } catch (Exception e) {
            throw new DataAccessException("Could not remove Trip from the persistence layer", e);
        }
    }

    @Override
    public void updateTrip(Trip trip) {
        try {
            tripDao.update(trip);
        } catch (Exception e){
            throw new DataAccessException("Could not update Trip in persistence layer", e);
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
