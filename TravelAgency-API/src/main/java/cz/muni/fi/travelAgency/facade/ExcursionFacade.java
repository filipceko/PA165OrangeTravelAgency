package cz.muni.fi.travelAgency.facade;

import cz.muni.fi.travelAgency.DTO.ExcursionCreateDTO;
import cz.muni.fi.travelAgency.DTO.ExcursionDTO;
import cz.muni.fi.travelAgency.DTO.TripDTO;
import cz.muni.fi.travelAgency.enums.Currency;
import java.util.List;

/**
 *
 * @author Rajivv
 */
public interface ExcursionFacade {

    public Long createExcursion(ExcursionCreateDTO excursion);
    public void getTotalPrice(Long id, Currency currency);
    public void deleteExcursion(Long excursionId);
    public ExcursionDTO getExcursionWithId(Long id);
    public List<ExcursionDTO> getAllExcurisons();
    public List<ExcursionDTO> getExcursionByDestination(String destination);
    public List<ExcursionDTO> getExcursionByTrip(TripDTO trip);
    public void removeExcursion(Long excursionId);

}
