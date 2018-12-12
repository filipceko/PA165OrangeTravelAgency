package cz.muni.fi.travelAgency.facade;

import cz.muni.fi.travelAgency.DTO.ExcursionCreateDTO;
import cz.muni.fi.travelAgency.DTO.ExcursionDTO;
import cz.muni.fi.travelAgency.DTO.ExcursionEditDTO;
import cz.muni.fi.travelAgency.entities.Excursion;
import cz.muni.fi.travelAgency.service.BeanMappingService;
import cz.muni.fi.travelAgency.service.ExcursionService;
import cz.muni.fi.travelAgency.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Implemetation of {@link ExcursionFacade}.
 *
 * @author Rajivv
 */
@Service
@Transactional
public class ExcursionFacadeImpl implements ExcursionFacade {

    /**
     * Excursion service.
     */
    @Autowired
    private ExcursionService excursionService;

    @Autowired
    private TripService tripService;

    /**
     * Mapper responsible for mapping DTOs to Entities.
     */
    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public Long createExcursion(ExcursionCreateDTO excursionDTO) {
        Excursion mappedExcursion = beanMappingService.mapTo(excursionDTO, Excursion.class);
        mappedExcursion.setTrip(tripService.findById(excursionDTO.getTripId()));
        Excursion excursion = excursionService.createExcursion(mappedExcursion);
        return excursion.getId();
    }

    @Override
    public ExcursionDTO findExcursionById(Long excursionId) {
        Excursion excursion = excursionService.findExcursionById(excursionId);
        return (excursion == null) ? null : beanMappingService.mapTo(excursion, ExcursionDTO.class);
    }

    @Override
    public Collection<ExcursionDTO> getAllExcursions() {
        return beanMappingService.mapTo(excursionService.getAllExcursions(), ExcursionDTO.class);
    }

    @Override
    public Collection<ExcursionDTO> findExcursionByDestination(String destination) {
        Collection<Excursion> excursions = excursionService.findExcursionByDestination(destination);
        return beanMappingService.mapTo(excursions, ExcursionDTO.class);
    }

    @Override
    public Collection<ExcursionDTO> findExcursionByTripId(Long tripId) {
        Collection<Excursion> excursions = excursionService.findExcursionByTripId(tripId);
        return beanMappingService.mapTo(excursions, ExcursionDTO.class);
    }

    @Override
    public void updateExcursion(ExcursionEditDTO excursionDTO) {
        if (excursionDTO == null) {
            throw new IllegalArgumentException("tried to update NULL excursion");
        }
        if (excursionDTO.getId() == null) {
            throw new IllegalArgumentException("tried to update excursion without ID");
        }
        Excursion mappedExcursion = beanMappingService.mapTo(excursionDTO, Excursion.class);
        mappedExcursion.setTrip(tripService.findById(excursionDTO.getTripId()));
        mappedExcursion.setId(excursionDTO.getId());
        excursionService.updateExcursion(mappedExcursion);
    }

    @Override
    public void deleteExcursion(ExcursionDTO excursionDTO) {
        if (excursionDTO == null) {
            throw new IllegalArgumentException("Tried to delete null excursion");
        }
        if (excursionDTO.getId() == null) {
            throw new IllegalArgumentException("tried to delete excursion without ID");
        }
        Excursion mappedExcursion = beanMappingService.mapTo(excursionDTO, Excursion.class);
        excursionService.deleteExcursion(mappedExcursion);
    }

}
