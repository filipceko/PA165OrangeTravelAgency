package cz.muni.fi.travelAgency.service;

import cz.muni.fi.travelAgency.dao.TripDao;
import cz.muni.fi.travelAgency.entities.Customer;
import cz.muni.fi.travelAgency.entities.Excursion;
import cz.muni.fi.travelAgency.entities.Reservation;
import cz.muni.fi.travelAgency.entities.Trip;
import cz.muni.fi.travelAgency.exceptions.DataAccessLayerException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.*;

/**
 * Implementation of the {@link TripService}. This class is part of the service
 * module of the application that provides the implementation of the business
 * logic.
 *
 * @author Rithy Ly
 */
@Service
public class TripServiceImpl implements TripService {

    @Inject
    private TripDao tripDao;

    @Override
    public Trip findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Tried to find Trip by NULL");
        }
        try {
            return tripDao.findById(id);

        } catch (Exception e) {
            throw new DataAccessLayerException("Could not get Trip from the persistence layer", e);
        }
    }

    @Override
    public Collection<Trip> findAll() {
        try {
            return tripDao.findAll();
        } catch (Exception e) {
            throw new DataAccessLayerException("Could not get Trip from the persistence layer", e);
        }
    }

    @Override
    public Collection<Trip> findByDestination(String destination) {
        if (destination == null || destination.isEmpty()) {
            throw new IllegalArgumentException("Cannot find Trip with null destination or empty string.");
        }
        try {
            return tripDao.findByDestination(destination);
        } catch (Exception e) {
            throw new DataAccessLayerException("Could not get Trip from the persistence layer", e);
        }
    }

    @Override
    public Collection<Trip> findByInterval(LocalDate fromDate, LocalDate toDate) {
        if (fromDate != null && toDate != null && (fromDate.isAfter(toDate))) {
            throw new IllegalArgumentException("From Date cannot after To Date.");
        }
        try {
            return tripDao.findByInterval(fromDate, toDate);
        } catch (Exception e) {
            throw new DataAccessLayerException("Could not get Trip from the persistence layer", e);
        }
    }

    @Override
    public Collection<Trip> findTripBySlot(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Invalid amount of slots.");
        }
        Collection<Trip> allTrips;
        try {
            allTrips = tripDao.findAll();
        } catch (Exception e) {
            throw new DataAccessLayerException("Could not get Trip from the persistence layer", e);
        }
        Set<Trip> foundTrips = new HashSet<>();
        for (Trip trip : allTrips) {
            if (trip.getCapacity() >= amount) {
                foundTrips.add(trip);
            }
        }
        return foundTrips;
    }

    @Override
    public void createTrip(Trip trip) {
        if (trip == null) {
            throw new IllegalArgumentException("Cannot create null Trip.");
        }
        if (trip.getFromDate().isAfter(trip.getToDate())) {
            throw new IllegalArgumentException("Cannot update trip starting date after end date.");
        }
        try {
            tripDao.create(trip);
        } catch (Exception e) {
            throw new DataAccessLayerException("Could not create Trip in persistence layer", e);
        }
    }

    @Override
    public void removeTrip(Trip trip) {
        if (trip == null) {
            throw new IllegalArgumentException("Tried to delete NULL trip");
        }
        if (trip.getId() == null) {
            throw new IllegalArgumentException("Tried to delete trip without ID");
        }
        tripDao.remove(trip);
    }

    @Override
    public void updateTrip(Trip trip) {
        if (trip == null) {
            throw new IllegalArgumentException("Cannot update trip with destination not set or null.");
        }
        if (trip.getDestination() == null || trip.getDestination().isEmpty()) {
            throw new IllegalArgumentException("Cannot update trip with destination not set or null.");
        } else if (trip.getFromDate().isAfter(trip.getToDate())) {
            throw new IllegalArgumentException("Cannot update trip starting date after end date.");
        } else if (trip.getId() == null) {
            throw new IllegalArgumentException("Tried to update trip without ID");
        }
        try {
            tripDao.update(trip);
        } catch (Exception e) {
            throw new DataAccessLayerException("Could not update Trip in persistence layer", e);
        }

    }

    @Override
    public Collection<Customer> getAllCustomers(Trip trip) {
        Collection<Reservation> reservations = tripDao.findById(trip.getId()).getReservations();
        Set<Customer> customers = new HashSet<>();
        for (Reservation reservation : reservations) {
            customers.add(reservation.getCustomer());
        }
        return customers;
    }

    @Override
    public Map<Trip, Collection<Excursion>> tripsForMoney(Double money) {
        if (money <= 0) {
            throw new IllegalArgumentException("Money must be more than zero");
        }
        TreeSet<Excursion> availableExcursions = new TreeSet<>(Comparator.comparing(Excursion::getPrice));
        TreeSet<Trip> allTrips = new TreeSet<>(Comparator.comparing(Trip::getPrice));
        allTrips.addAll(findTripBySlot(1));
        Double moneyLeft = money;
        Map<Trip, Collection<Excursion>> result = new HashMap<>();
        for (Trip trip : allTrips) {
            if ((moneyLeft - trip.getPrice()) < 0) {
                break;
            }
            moneyLeft = moneyLeft - trip.getPrice();
            availableExcursions.addAll(trip.getExcursions());
            result.put(trip, new HashSet<>());
        }
        for (Excursion excursion : availableExcursions) {
            if (moneyLeft - excursion.getPrice() < 0) {
                break;
            }
            moneyLeft = moneyLeft - excursion.getPrice();
            result.get(excursion.getTrip()).add(excursion);
        }
        return result;
    }

    @Override
    public Collection<Trip> findAvailableFutureTrip() {
        Collection<Trip> allTrips;
        try {
            allTrips = tripDao.findAll();
        } catch (Exception e) {
            throw new DataAccessLayerException("Could not get Trip from the persistence layer", e);
        }
        Set<Trip> foundTrips = new HashSet<>();
        allTrips.stream().filter((trip) -> (trip.getCapacity() > 0 && trip.getFromDate()
                .isAfter(LocalDate.now()))).forEach((trip) -> {
            foundTrips.add(trip);
        });
        return foundTrips;

    }
}
