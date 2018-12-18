package cz.muni.fi.travelAgency.controllers.admin;

import cz.muni.fi.travelAgency.DTO.TripDTO;
import cz.muni.fi.travelAgency.facade.TripFacade;
import cz.muni.fi.travelAgency.validators.TripValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Filip Cekovsky
 */
@Controller
@RequestMapping("admin/trip")
public class AdminTripController {

    private final static Logger logger = LoggerFactory.getLogger(AdminTripController.class);

    @Autowired
    private TripValidator validator;

    @Autowired
    private TripFacade tripFacade;


    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        if (binder.getTarget() instanceof TripDTO) {
            binder.addValidators(validator);
        }
    }

    @RequestMapping(value = "list/{filter}", method = RequestMethod.GET)
    public String list(@PathVariable String filter, Model model) {
        Collection<TripDTO> trips;
        switch (filter) {
            case "all":
                trips = tripFacade.getAllTrips();
                break;
            case "future":
                trips = tripFacade.getAvailableFutureTrip();
                break;
            default:
                trips = new ArrayList<>();
                model.addAttribute("alert_danger", "Unknown filter " + filter);
        }
        model.addAttribute("trips", trips);
        return "admin/trip/list";
    }

    @RequestMapping(value = "filterTrips", method = RequestMethod.POST)
    public String filter(@RequestParam("filter") String filter, Model model) {
        Collection<TripDTO> trips = tripFacade.getAllTrips();
        Collection<TripDTO> filtered = trips.stream().filter(tripDTO -> tripDTO.getDestination().contains(filter))
                .collect(Collectors.toList());
        model.addAttribute("trips", filtered);
        return "/admin/trip/list";
    }

    @RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model) {
        model.addAttribute("trip", tripFacade.getTripById(id));
        return "admin/trip/detail";
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable long id, UriComponentsBuilder uriBuilder, RedirectAttributes attributes) {
        try {
            tripFacade.removeTrip(tripFacade.getTripById(id));
            attributes.addFlashAttribute("alert_success", "Trip number " + id + " was canceled.");
        } catch (DataAccessException ex) {
            logger.warn("cannot remove Trip {}", id);
            attributes.addFlashAttribute("alert_danger", "Trip number " + id + " was not canceled. " + ex.getMessage());
        }
        return "redirect:" + uriBuilder.path("/admin/trip/list/all").build().encode().toUriString();
    }

    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String getEdit(@PathVariable long id, Model model) {
        model.addAttribute("trip", tripFacade.getTripById(id));
        return "admin/trip/edit";
    }

    @RequestMapping(value = "editTrip", method = RequestMethod.POST)
    public String submitEdit(@Valid @ModelAttribute("trip") TripDTO trip, BindingResult result, Model model,
                             RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        if (!resultIsValid(result, model)) {
            return "/admin/trip/edit";
        }
        tripFacade.updateTrip(trip);
        redirectAttributes.addFlashAttribute("alert_success", "Trip #" + trip.getId() + " updated");
        return "redirect:" + uriBuilder.path("/admin/trip/detail/" + trip.getId()).build().encode().toUriString();
    }

    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute("trip", new TripDTO());
        return "admin/trip/create";
    }

    @RequestMapping(value = "createTrip", method = RequestMethod.POST)
    public String submitCreate(@Valid @ModelAttribute("trip") TripDTO trip, BindingResult result, Model model,
                               RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        if (!resultIsValid(result, model)) {
            return "/admin/trip/create";
        }
        tripFacade.createTrip(trip);
        redirectAttributes.addFlashAttribute("alert_success", "Trip #" + trip.getId() + " created");
        return "redirect:" + uriBuilder.path("/admin/trip/detail/" + trip.getId()).build().encode().toUriString();
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
