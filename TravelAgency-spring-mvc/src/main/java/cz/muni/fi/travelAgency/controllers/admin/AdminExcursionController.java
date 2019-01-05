package cz.muni.fi.travelAgency.controllers.admin;

import cz.muni.fi.travelAgency.DTO.ExcursionDTO;
import cz.muni.fi.travelAgency.DTO.ExcursionManipulationDTO;
import cz.muni.fi.travelAgency.DTO.TripDTO;
import cz.muni.fi.travelAgency.facade.ExcursionFacade;
import cz.muni.fi.travelAgency.facade.TripFacade;
import cz.muni.fi.travelAgency.service.BeanMappingService;
import cz.muni.fi.travelAgency.validators.ExcursionValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Simona Raucinova
 */
@Controller
@RequestMapping("admin/excursion")
public class AdminExcursionController {

    private final static Logger logger = LoggerFactory.getLogger(AdminTripController.class);

    @Autowired
    private ExcursionValidator validator;

    @Autowired
    private ExcursionFacade excursionFacade;

    @Autowired
    private TripFacade tripFacade;

    @Autowired
    private BeanMappingService mapper;

    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String create(Model model) {
        logger.debug("new()");
        model.addAttribute("excursionCreate", new ExcursionManipulationDTO());
        return "admin/excursion/create";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String submitCreate(@Valid @ModelAttribute("excursionCreate") ExcursionManipulationDTO manipulationDTO, BindingResult bindingResult,
                               Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        logger.debug("create(excursionCreate={})", manipulationDTO);
        if (!resultIsValid(bindingResult, model)) {
            return "admin/excursion/create";
        }
        ExcursionDTO excursionDTO = mapManipulationToEntityDTO(manipulationDTO);
        excursionFacade.createExcursion(excursionDTO);
        redirectAttributes.addFlashAttribute("alert_success", "Excursion " + excursionDTO.getId() + " was created");
        return "redirect:" + uriBuilder.path("/admin/excursion/detail/" + excursionDTO.getId()).build().encode().toUriString();
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Model model) {
        Collection<ExcursionDTO> excursions = excursionFacade.getAllExcursions();
        model.addAttribute("excursions", excursions);
        return "admin/excursion/list";
    }

    @RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model) {
        ExcursionDTO excursionDTO = excursionFacade.findExcursionById(id);
        model.addAttribute("excursion", excursionDTO);
        return "admin/excursion/detail";
    }


    @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        ExcursionDTO excursion = excursionFacade.findExcursionById(id);
        logger.debug("delete({})", id);
        try {
            excursionFacade.deleteExcursion(excursion);
            redirectAttributes.addFlashAttribute("alert_success", "Excursion \"" + excursion.getId() + "\" was deleted.");
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("excursion " + id + " cannot be deleted");
            logger.error(NestedExceptionUtils.getMostSpecificCause(ex).getMessage());
            redirectAttributes.addFlashAttribute("alert_danger", "Excursion \"" + excursion.getId() + "\" cannot be deleted." + ex.getMessage());
        }
        return "redirect:" + uriBuilder.path("/admin/excursion/list").toUriString();
    }


    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String getEdit(@PathVariable long id, Model model) {
        ExcursionDTO excursionDTO = excursionFacade.findExcursionById(id);
        ExcursionManipulationDTO manipulationDTO = mapper.mapTo(excursionDTO, ExcursionManipulationDTO.class);
        manipulationDTO.setDurationMinutes(excursionDTO.getExcursionDuration().toMinutes());
        manipulationDTO.setTripId(excursionDTO.getTrip().getId());
        model.addAttribute("excursion", manipulationDTO);
        return "admin/excursion/edit";
    }

    @RequestMapping(value = "editExcursion", method = RequestMethod.POST)
    public String submitEdit(@Valid @ModelAttribute("excursion") ExcursionManipulationDTO manipulationDTO,
                             BindingResult result, Model model, RedirectAttributes redirectAttributes,
                             UriComponentsBuilder uriBuilder) {
        if (!resultIsValid(result, model)) {
            return "admin/excursion/edit";
        }
        ExcursionDTO excursionDTO = mapManipulationToEntityDTO(manipulationDTO);
        excursionFacade.updateExcursion(excursionDTO);
        redirectAttributes.addFlashAttribute("alert_success", "Excursion #" + excursionDTO.getId() + " updated");
        return "redirect:" + uriBuilder.path("/admin/excursion/detail/" + excursionDTO.getId()).build().encode().toUriString();
    }

    @ModelAttribute("trips")
    public List<TripDTO> trips() {
        logger.debug("trips()");
        return new ArrayList<>(tripFacade.getAllTrips());
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        if (binder.getTarget() instanceof ExcursionDTO) {
            binder.addValidators(validator);
        }
    }

    private ExcursionDTO mapManipulationToEntityDTO(ExcursionManipulationDTO manipulationDTO){
        ExcursionDTO excursionDTO = mapper.mapTo(manipulationDTO, ExcursionDTO.class);
        excursionDTO.setTrip(tripFacade.getTripById(manipulationDTO.getTripId()));
        excursionDTO.setExcursionDuration(Duration.parse("PT" + manipulationDTO.getDurationMinutes() + "M"));
        return excursionDTO;
    }

    private boolean resultIsValid(BindingResult result, Model model) {
        if (result.hasErrors()) {
            for (ObjectError ge : result.getGlobalErrors()) {
                logger.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : result.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                logger.trace("FieldError: {}", fe);
            }
            return false;
        }
        return true;
    }

}
