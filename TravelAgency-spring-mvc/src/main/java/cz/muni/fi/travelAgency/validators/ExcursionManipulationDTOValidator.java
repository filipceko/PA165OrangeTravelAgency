package cz.muni.fi.travelAgency.validators;

import cz.muni.fi.travelAgency.DTO.ExcursionManipulationDTO;
import cz.muni.fi.travelAgency.facade.TripFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class ExcursionManipulationDTOValidator implements Validator {

    @Autowired
    private TripFacade tripFacade;

    @Override
    public boolean supports(Class<?> aClass) {
        return ExcursionManipulationDTO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ExcursionManipulationDTO excursionManipulationDTO = (ExcursionManipulationDTO) o;
        if (excursionManipulationDTO.getPrice() <= 0){
            errors.rejectValue("price","ExcursionCreateDTOValidator.negativePrice");
        }
        if(excursionManipulationDTO.getExcursionDate().isBefore(LocalDate.now().plusDays(1))) {
            errors.rejectValue("excursionDate","ExcursionCreateDTOValidator.pastDate");
        }
        if(excursionManipulationDTO.getDurationMinutes() <=0 ){
            errors.rejectValue("durationMinutes","ExcursionCreateDTOValidator.negativeMinutes");
        }


        if (tripFacade.getTripById
                        (excursionManipulationDTO.getTripId())
                .getFromDate()
                        .isAfter(excursionManipulationDTO.getExcursionDate()) ||
            tripFacade.getTripById(excursionManipulationDTO.getTripId()).getToDate()
                        .isBefore(excursionManipulationDTO.getExcursionDate())){

            errors.rejectValue("excursionDate","ExcursionCreateDTOValidator.date.not.during.trip");
        }
    }
}
