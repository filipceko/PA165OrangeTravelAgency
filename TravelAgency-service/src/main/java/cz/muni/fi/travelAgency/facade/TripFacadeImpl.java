package cz.muni.fi.travelAgency.facade;

import cz.muni.fi.travelAgency.DTO.TripDTO;
import cz.muni.fi.travelAgency.entities.Trip;
import cz.muni.fi.travelAgency.service.BeanMappingService;
import cz.muni.fi.travelAgency.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * Implemetation of {@link TripFacade}.
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
    public void createTrip(TripDTO trip)
    {
        if (trip == null) {
            throw new IllegalArgumentException("TripDTO is null.");
        }
        Trip mappedTrip = beanMappingService.mapTo(trip, Trip.class);
        tripService.createTrip(mappedTrip);
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
    public Collection<TripDTO> getTripBySlot(int amount){
        Collection<Trip> trips = tripService.findTripBySlot(amount);
        return beanMappingService.mapTo(trips, TripDTO.class);
    }

    @Override
    public TripDTO getTripById(long id){
        Trip trip = tripService.findById(id);
        return (trip == null) ? null : beanMappingService.mapTo(trip, TripDTO.class);
    }

    @Override
    public List<TripDTO> getAllTrips(){
        return beanMappingService.mapTo(tripService.findAll(), TripDTO.class);
    }

    @Override
    public List<TripDTO> getTripByDestination(String destination){
        return beanMappingService.mapTo(tripService.findByDestination(destination), TripDTO.class);
    }

    @Override
    public List<TripDTO> getTripByInterval(LocalDate fromDate, LocalDate toDate) {
        return beanMappingService.mapTo(tripService.findByInterval(fromDate,toDate), TripDTO.class);
    }

}
