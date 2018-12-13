package cz.muni.fi.travelAgency.validators;

import cz.muni.fi.travelAgency.DTO.ExcursionCreateDTO;
import cz.muni.fi.travelAgency.facade.TripFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.ConstraintViolation;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Set;

public class ExcursionCreateDTOValidator implements Validator {

    @Autowired
    private TripFacade tripFacade;

    @Override
    public boolean supports(Class<?> aClass) {
        return ExcursionCreateDTO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ExcursionCreateDTO excursionCreateDTO = (ExcursionCreateDTO) o;
        if (excursionCreateDTO.getPrice() <= 0){
            errors.rejectValue("price","ExcursionCreateDTOValidator.negativePrice");
        }
        if(excursionCreateDTO.getExcursionDate().isBefore(LocalDate.now().plusDays(1))) {
            errors.rejectValue("excursionDate","ExcursionCreateDTOValidator.pastDate");
        }

        if (tripFacade
                .getTripById
                        (excursionCreateDTO
                                .getTripId())
                .getFromDate()
                        .isAfter(excursionCreateDTO.getExcursionDate()) ||
            tripFacade.getTripById(excursionCreateDTO.getTripId()).getToDate()
                        .isBefore(excursionCreateDTO.getExcursionDate())){

            errors.rejectValue("excursionDate","ExcursionCreateDTOValidator.date.not.during.trip");
        }
    }
}
