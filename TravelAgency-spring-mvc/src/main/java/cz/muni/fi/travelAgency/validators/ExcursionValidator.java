package cz.muni.fi.travelAgency.validators;

import cz.muni.fi.travelAgency.DTO.ExcursionCreateDTO;
import cz.muni.fi.travelAgency.DTO.ExcursionDTO;
import cz.muni.fi.travelAgency.facade.TripFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

/**
 * Class securing excursion validation
 *
 * @author Simona Raucinova
 */
@Component
public class ExcursionValidator implements Validator {

    /**
     * Trip facade
     */
    @Autowired
    private TripFacade tripFacade;

    @Override
    public boolean supports(Class<?> aClass) {
        return ExcursionCreateDTO.class.isAssignableFrom(aClass) ||
                ExcursionDTO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if (o instanceof ExcursionCreateDTO) {
            ExcursionCreateDTO excursionDTO = (ExcursionCreateDTO) o;
            if (excursionDTO.getPrice() <= 0) {
                errors.rejectValue("price", "excursionValidator.negativePrice");
            }
            if (excursionDTO.getExcursionDate().isBefore(LocalDate.now().plusDays(1))) {
                errors.rejectValue("excursionDate", "excursionValidator.pastDate");
            }
            if (excursionDTO.getDurationMinutes() <= 0) {
                errors.rejectValue("durationMinutes", "excursionValidator.negativeMinutes");
            }
            if (excursionDTO.getTripId() == null) {
                errors.rejectValue("excursionDate", "excursionValidator.tripNull");
            }
            if (tripFacade.getTripById
                    (excursionDTO.getTripId())
                    .getFromDate()
                    .isAfter(excursionDTO.getExcursionDate()) ||
                    tripFacade.getTripById(excursionDTO.getTripId()).getToDate()
                            .isBefore(excursionDTO.getExcursionDate())) {

                errors.rejectValue("excursionDate", "ExcursionCreateDTOValidator.date.not.during.trip");
            }
        }
    }
}
