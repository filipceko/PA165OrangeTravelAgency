package cz.muni.fi.travelAgency.validators;

import cz.muni.fi.travelAgency.DTO.ExcursionDTO;
import cz.muni.fi.travelAgency.DTO.ExcursionManipulationDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

/**
 * @author Simona Raucinova
 */
@Component
public class ExcursionValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return ExcursionManipulationDTO.class.isAssignableFrom(aClass) ||
                ExcursionDTO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if (o instanceof ExcursionDTO){
            //TODO
        } else if (o instanceof ExcursionManipulationDTO) {
            ExcursionManipulationDTO excursionDTO = (ExcursionManipulationDTO) o;
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
                //TODO
                errors.rejectValue("excursionDate", "excursionValidator.tripNull");
            }
        }
    }
}
