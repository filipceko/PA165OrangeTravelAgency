package cz.muni.fi.travelAgency.facade;

import cz.muni.fi.travelAgency.DTO.ExcursionCreateDTO;
import cz.muni.fi.travelAgency.DTO.ExcursionDTO;
import cz.muni.fi.travelAgency.entities.Excursion;
import cz.muni.fi.travelAgency.service.BeanMappingService;
import cz.muni.fi.travelAgency.service.ExcursionService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implemetation of {@link ExcursionFacade}.
 * @author Rajivv
 */
@Service
@Transactional
public class ExcursionFacadeImpl implements ExcursionFacade {

    @Autowired
    private ExcursionService excursionService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public Long createExcursion(ExcursionCreateDTO excursionCreateDTO) {
        Excursion mappedExcursion = beanMappingService.mapTo(excursionCreateDTO, Excursion.class);
        excursionService.createExcursion(mappedExcursion);
        return mappedExcursion.getId();
    }

    @Override
    public ExcursionDTO getExcursionWithId(Long excursionId) {
        Excursion excursion = excursionService.findExcursionById(excursionId);
        return (excursion == null) ? null : beanMappingService.mapTo(excursion, ExcursionDTO.class);
    }

    @Override
    public Collection<ExcursionDTO> getAllExcurisons() {
        return beanMappingService.mapTo(excursionService.getAllExcursions(), ExcursionDTO.class);
    }

    @Override
    public Collection<ExcursionDTO> getExcursionByDestination(String destination) {
        Collection<Excursion> excursions = excursionService.findExcursionByDestination(destination);
        return beanMappingService.mapTo(excursions, ExcursionDTO.class);
    }

    @Override
    public Collection<ExcursionDTO> getExcursionByTripId(Long tripId) {
        Collection<Excursion> excursions = excursionService.findExcursionByTripId(tripId);
        return beanMappingService.mapTo(excursions, ExcursionDTO.class);
    }

    @Override
    public void removeExcursion(Long excursionId) {
        excursionService.deleteExcursion(excursionService.findExcursionById(excursionId));
    }

}
