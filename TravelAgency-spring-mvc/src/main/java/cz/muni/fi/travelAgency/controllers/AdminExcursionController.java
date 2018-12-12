package cz.muni.fi.travelAgency.controllers;

import cz.muni.fi.travelAgency.DTO.ExcursionCreateDTO;
import cz.muni.fi.travelAgency.DTO.ExcursionDTO;
import cz.muni.fi.travelAgency.DTO.ExcursionEditDTO;
import cz.muni.fi.travelAgency.DTO.TripDTO;
import cz.muni.fi.travelAgency.facade.ExcursionFacade;
import cz.muni.fi.travelAgency.facade.TripFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("admin/excursion")
public class AdminExcursionController {

    private final static Logger logger = LoggerFactory.getLogger(AdminTripController.class);

    @Autowired
    private ExcursionFacade excursionFacade;

    @Autowired
    private TripFacade tripFacade;

    /**
     * Shows a list of excursions.
     *
     * @param model data to display
     * @return JSP page name
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        Collection<ExcursionDTO> excursions = excursionFacade.getAllExcursions();
        for (ExcursionDTO excursion : excursions){
            initDurationString(excursion);
        }
        model.addAttribute("excursions",excursions);
        return "admin/excursion/list";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String create(@PathVariable long id, Model model){
        ExcursionDTO excursionDTO = excursionFacade.findExcursionById(id);
        initDurationString(excursionDTO);
        model.addAttribute("excursion", excursionDTO);
        return "admin/excursion/detail";
    }

    private void initDurationString(ExcursionDTO excursion){
        excursion.setDurationString(excursion.getExcursionDuration().toString()
                .substring(2)
                .replaceAll("(\\d[HMS])(?!$)", "$1 ")
                .toLowerCase());
    }

    private void initDurationInteger(ExcursionDTO excursion){
        excursion.setDurationMinutes((int) excursion.getExcursionDuration().toMinutes());
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        ExcursionDTO excursion = excursionFacade.findExcursionById(id);
        logger.debug("delete({})", id);
        try {
            excursionFacade.deleteExcursion(excursion);
            redirectAttributes.addFlashAttribute("alert_success", "Excursion \"" + excursion.getId() + "\" was deleted.");
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("excursion "+id+" cannot be deleted");
            logger.error(NestedExceptionUtils.getMostSpecificCause(ex).getMessage());
            redirectAttributes.addFlashAttribute("alert_danger", "Excursion \"" + excursion.getId() + "\" cannot be deleted." + ex.getMessage());
        }
        return "redirect:" + uriBuilder.path("/admin/excursion/list").toUriString();
    }


    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String getEdit(@PathVariable long id, Model model) {
        ExcursionDTO excursionDTO = excursionFacade.findExcursionById(id);
        excursionDTO.setId(id);
        excursionDTO.setTrip(excursionDTO.getTrip());
        initDurationInteger(excursionDTO);
        model.addAttribute("excursion", excursionDTO);
        return "admin/excursion/edit";
    }

    @RequestMapping(value = "editExcursion/{excursionId}/{tripId}", method = RequestMethod.POST)
    public String submitEdit(@PathVariable long excursionId, @PathVariable long tripId, @Valid @ModelAttribute("excursion") ExcursionEditDTO excursion, BindingResult result, ModelMap model,
                             RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        if (result.hasErrors()) {
            model.addAttribute("alert_danger", result);
            model.addAttribute("excursion", excursion);
            return "/admin/excursion/edit";
        }

        excursion.setId(excursionId);
        excursion.setTripId(tripId);
        excursionFacade.updateExcursion(excursion);
        redirectAttributes.addFlashAttribute("alert_success", "Excursion #" + excursion.getId() + " updated");
        return "redirect:" + uriBuilder.path("/admin/excursion/detail/" + excursion.getId()).build().encode().toUriString();
    }

    /**
     * Prepares an empty form.
     *
     * @param model data to be displayed
     * @return JSP page
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newProduct(Model model) {
        logger.debug("new()");
        model.addAttribute("excursionCreate", new ExcursionCreateDTO());
        return "admin/excursion/create";
    }

    @ModelAttribute("trips")
    public List<TripDTO> trips() {
        logger.debug("trips()");
        return new ArrayList<>(tripFacade.getAllTrips());
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("excursionCreate") ExcursionCreateDTO formBean, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        logger.debug("create(excursionCreate={})", formBean);

        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                logger.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                logger.trace("FieldError: {}", fe);
            }
            return "admin/excursion/create";
        }
        Long id = excursionFacade.createExcursion(formBean);
        redirectAttributes.addFlashAttribute("alert_success", "Excursion " + id + " was created");
        return "redirect:" + uriBuilder.path("/admin/excursion/detail/{id}").buildAndExpand(id).encode().toUriString();
    }

}
