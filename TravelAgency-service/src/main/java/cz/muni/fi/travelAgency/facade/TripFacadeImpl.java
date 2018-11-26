package cz.muni.fi.travelAgency.facade;

import cz.muni.fi.travelAgency.DTO.CustomerDTO;
import cz.muni.fi.travelAgency.DTO.TripDTO;
import cz.muni.fi.travelAgency.entities.Customer;
import cz.muni.fi.travelAgency.entities.Trip;
import cz.muni.fi.travelAgency.service.BeanMappingService;
import cz.muni.fi.travelAgency.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Collection;

/**
 * Implemetation of {@link TripFacade}.
 *
 * @author Rithy Ly
 */
@Service
@Transactional
public class TripFacadeImpl implements TripFacade {

    @Autowired
    private TripService tripService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public void createTrip(TripDTO trip) {
        if (trip == null) {
            throw new IllegalArgumentException("TripDTO is null.");
        }
        Trip mappedTrip = beanMappingService.mapTo(trip, Trip.class);
        tripService.createTrip(mappedTrip);
        trip.setId(mappedTrip.getId());
    }

    @Override
    public void updateTrip(TripDTO trip) {
        if (trip == null) {
            throw new IllegalArgumentException("TripDTO is null.");
        }
        Trip mappedTrip = beanMappingService.mapTo(trip, Trip.class);
        tripService.updateTrip(mappedTrip);
    }

    @Override
    public void removeTrip(TripDTO trip) {
        if (trip == null) {
            throw new IllegalArgumentException("TripDTO is null.");
        }
        Trip mappedTrip = beanMappingService.mapTo(trip, Trip.class);
        tripService.removeTrip(mappedTrip);
    }

    @Override
    public Collection<TripDTO> getTripBySlot(int amount) {
        Collection<Trip> trips = tripService.findTripBySlot(amount);
        return beanMappingService.mapTo(trips, TripDTO.class);
    }

    @Override
    public TripDTO getTripById(long id) {
        Trip trip = tripService.findById(id);
        return (trip == null) ? null : beanMappingService.mapTo(trip, TripDTO.class);
    }

    @Override
    public Collection<TripDTO> getAllTrips() {
        return beanMappingService.mapTo(tripService.findAll(), TripDTO.class);
    }

    @Override
    public Collection<TripDTO> getTripByDestination(String destination) {
        return beanMappingService.mapTo(tripService.findByDestination(destination), TripDTO.class);
    }

    @Override
    public Collection<TripDTO> getTripByInterval(LocalDate fromDate, LocalDate toDate) {
        return beanMappingService.mapTo(tripService.findByInterval(fromDate, toDate), TripDTO.class);
    }

    @Override
    public Collection<TripDTO> getAvailableFutureTrip() {
        Collection<Trip> trips = tripService.findAvailableFutureTrip();
        return beanMappingService.mapTo(trips, TripDTO.class);
    }

    @Override
    public Collection<CustomerDTO> getAllCustomers(TripDTO trip) {
        Trip mappedTrip = beanMappingService.mapTo(trip, Trip.class);
        Collection<Customer> customers = tripService.getAllCustomers(mappedTrip);
        return beanMappingService.mapTo(customers,CustomerDTO.class);
    }

}
