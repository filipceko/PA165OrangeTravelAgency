package cz.muni.fi.travelAgency.validators;

import cz.muni.fi.travelAgency.DTO.TripDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class TripValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return TripDTO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if (o instanceof TripDTO) {
            TripDTO tripDTO = (TripDTO) o;
            LocalDate from = tripDTO.getFromDate();
            LocalDate to = tripDTO.getToDate();
            if ( from != null && to != null && from.isAfter(to)) {
                errors.rejectValue("toDate", "tripValidator.fromAfterTo");
            }
        }
    }
}
