package cz.muni.fi.travelAgency.service;

import cz.muni.fi.travelAgency.DTO.CheapTravelDTO;
import cz.muni.fi.travelAgency.DTO.ExcursionCreateDTO;
import cz.muni.fi.travelAgency.DTO.ExcursionDTO;
import cz.muni.fi.travelAgency.DTO.TripDTO;
import cz.muni.fi.travelAgency.entities.Excursion;
import cz.muni.fi.travelAgency.entities.Trip;
import cz.muni.fi.travelAgency.facade.TripFacade;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;

/**
 * Implementation of {@link BeanMappingService}
 *
 * @author Simona Raucinova, Filip Cekovsky
 */
@Service
public class BeanMappingServiceImpl implements BeanMappingService {

    /**
     * Mapper to be used for basic mapping.
     */
    @Autowired
    private Mapper mapper;

    /**
     * Trip facade
     */
    @Autowired
    TripFacade tripFacade;

    @Override
    public Mapper getMapper() {
        return mapper;
    }

    @Override
    public <T> List<T> mapTo(List<?> objects, Class<T> mapToClass) {
        List<T> mappedCollection = new ArrayList<>();
        for (Object object : objects) {
            mappedCollection.add(mapTo(object, mapToClass));
        }
        return mappedCollection;
    }

    @Override
    public <T> Set<T> mapTo(Set<?> objects, Class<T> mapToClass) {
        Set<T> mappedCollection = new HashSet<>();
        for (Object object : objects) {
            mappedCollection.add(mapTo(object, mapToClass));
        }
        return mappedCollection;
    }

    @Override
    public <T> Collection<T> mapTo(Collection<?> objects, Class<T> mapToClass) {
        List<T> mappedCollection = new ArrayList<>();
        for (Object object : objects) {
            mappedCollection.add(mapTo(object, mapToClass));
        }
        return mappedCollection;
    }

    @Override
    public <T> T mapTo(Object u, Class<T> mapToClass) {
        return mapper.map(u, mapToClass);
    }

    @Override
    public CheapTravelDTO mapToCheapTravel(Map<Trip, Collection<Excursion>> entitiesMap) {
        CheapTravelDTO result = new CheapTravelDTO();
        Collection<Trip> trips = entitiesMap.keySet();
        result.setTrips(mapTo(trips, TripDTO.class));
        Collection<Collection<Excursion>> excursions = entitiesMap.values();
        Set<Excursion> allExcursions = new HashSet<>();
        for (Collection<Excursion> excursionsOfTrip : excursions) {
            allExcursions.addAll(excursionsOfTrip);
        }
        result.setExcursions(mapTo(allExcursions, ExcursionDTO.class));
        return result;
    }

    @Override
    public ExcursionDTO mapManipulationToEntityDTO(ExcursionCreateDTO manipulationDTO) {
        ExcursionDTO excursionDTO = mapTo(manipulationDTO, ExcursionDTO.class);
        excursionDTO.setTrip(tripFacade.getTripById(manipulationDTO.getTripId()));
        excursionDTO.setExcursionDuration(Duration.parse("PT" + manipulationDTO.getDurationMinutes() + "M"));
        return excursionDTO;
    }
}
