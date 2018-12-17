package cz.muni.fi.travelAgency.rest.controllers;

import cz.muni.fi.travelAgency.DTO.ExcursionDTO;
import cz.muni.fi.travelAgency.DTO.ExcursionManipulationDTO;
import cz.muni.fi.travelAgency.facade.ExcursionFacade;
import cz.muni.fi.travelAgency.rest.ApiUris;
import cz.muni.fi.travelAgency.rest.exceptions.ResourceAlreadyExistingException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@RestController
@RequestMapping(ApiUris.EXCURSIONS_URI)
public class ExcursionsController {

    final static Logger logger = LoggerFactory.getLogger(ExcursionsController.class);

    @Inject
    private ExcursionFacade excursionFacade;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<ExcursionDTO> getAllExcursions() {
        logger.debug("rest getAllExcursions()");
        try {
            return new ArrayList<>(excursionFacade.getAllExcursions());
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex.getMessage(), ex);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ExcursionDTO detail(@PathVariable long id){
        logger.debug("rest getExcursion({})", id);
        ExcursionDTO excursionDTO = excursionFacade.findExcursionById(id);
        if (excursionDTO != null) {
            return excursionDTO;
        } else {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteExcursion(@PathVariable("id") long id) throws Exception {
        logger.debug("rest deleteExcursion({})", id);
        try {
            excursionFacade.deleteExcursion(excursionFacade.findExcursionById(id));
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final ExcursionDTO createExcursion(@RequestBody ExcursionManipulationDTO excursion) throws Exception {

        logger.debug("rest createExcursion()");

        try {
            excursionFacade.createExcursion(excursion);
            return excursionFacade.findExcursionById(excursion.getId());
        } catch (Exception ex) {
            throw new ResourceAlreadyExistingException();
        }
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final ExcursionDTO editExcursion(@RequestBody ExcursionManipulationDTO excursion) throws Exception{

        logger.debug("rest editExcursion()");

        try {
            excursionFacade.updateExcursion(excursion);
            return excursionFacade.findExcursionById(excursion.getId());
        } catch (Exception ex) {
            throw new InvalidParameterException();
        }

    }
}
