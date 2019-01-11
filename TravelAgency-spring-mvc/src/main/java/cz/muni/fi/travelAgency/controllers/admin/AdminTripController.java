package cz.muni.fi.travelAgency.controllers.admin;

import cz.muni.fi.travelAgency.DTO.TripDTO;
import cz.muni.fi.travelAgency.controllers.utils.ControllerUtils;
import cz.muni.fi.travelAgency.facade.TripFacade;
import cz.muni.fi.travelAgency.validators.TripValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Controller handling admins' trip pages.
 *
 * @author Filip Cekovsky
 */
@Controller
@RequestMapping("admin/trip")
public class AdminTripController {
    /**
     * Logger ofr this class
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminTripController.class);

    /**
     * Application context
     */
    @Autowired
    private ApplicationContext context;

    /**
     * Validator for trips
     */
    @Autowired
    private TripValidator validator;

    /**
     * Trip facade
     */
    @Autowired
    private TripFacade tripFacade;

    /**
     * Initializes validation
     *
     * @param binder data binder
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        if (binder.getTarget() instanceof TripDTO) {
            binder.addValidators(validator);
        }
    }

    /**
     * Handles page listing the trips
     *
     * @param filter Path variable deciding what filter will be used
     * @param model  of the page
     * @return target page address
     */
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
                model.addAttribute("alert_danger", context.getMessage("error.unknownFilter",
                        new Object[] {filter}, LocaleContextHolder.getLocale()));
        }
        model.addAttribute("trips", trips);
        return "admin/trip/list";
    }

    /**
     * Handles filtering based on trips destination
     *
     * @param filter text submitted to the filter
     * @param model  of the page
     * @return target page address
     */
    @RequestMapping(value = "filterTrips", method = RequestMethod.POST)
    public String filter(@RequestParam("filter") String filter, Model model) {
        Collection<TripDTO> trips = tripFacade.getAllTrips();
        Collection<TripDTO> filtered = trips.stream().filter(tripDTO -> tripDTO.getDestination().contains(filter))
                .collect(Collectors.toList());
        model.addAttribute("trips", filtered);
        return "/admin/trip/list";
    }

    /**
     * Handles trips detail page
     *
     * @param id    of the displayed trip
     * @param model of the page
     * @return target page address
     */
    @RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model) {
        model.addAttribute("trip", tripFacade.getTripById(id));
        return "admin/trip/detail";
    }

    /**
     * Handles trip deletion
     *
     * @param id         of deleted trip
     * @param uriBuilder builder for redirect
     * @param attributes attributes for redirect
     * @return redirection address
     */
    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable long id, UriComponentsBuilder uriBuilder, RedirectAttributes attributes) {
        try {
            tripFacade.removeTrip(tripFacade.getTripById(id));
            attributes.addFlashAttribute("alert_success", context.getMessage("success.tripCanceled",
                    new Object[] {id}, LocaleContextHolder.getLocale()));
        } catch (DataAccessException ex) {
            LOGGER.warn("cannot remove Trip {}", id);
            attributes.addFlashAttribute("alert_danger", context.getMessage("error.tripNotCanceled",
                    new Object[] {id}, LocaleContextHolder.getLocale()));
        }
        return "redirect:" + uriBuilder.path("/admin/trip/list/all").build().encode().toUriString();
    }

    /**
     * Handles trip editing page creation
     *
     * @param id    of edited trip
     * @param model of the page
     * @return target page address
     */
    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String getEdit(@PathVariable long id, Model model) {
        model.addAttribute("trip", tripFacade.getTripById(id));
        return "admin/trip/edit";
    }

    /**
     * Handles trip editing
     *
     * @param trip               edited trip information
     * @param result             validation result
     * @param model              of the page
     * @param redirectAttributes attributes for redirect page
     * @param uriBuilder         for redirect page
     * @return redirect address
     */
    @RequestMapping(value = "editTrip", method = RequestMethod.POST)
    public String submitEdit(@Valid @ModelAttribute("trip") TripDTO trip, BindingResult result, Model model,
                             RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        if (!ControllerUtils.isResultValid(result, model)) {
            return "/admin/trip/edit";
        }
        tripFacade.updateTrip(trip);
        redirectAttributes.addFlashAttribute("alert_success", context.getMessage("success.tripEdited",
                new Object[] {trip.getDestination()}, LocaleContextHolder.getLocale()));
        return "redirect:" + uriBuilder.path("/admin/trip/detail/" + trip.getId()).build().encode().toUriString();
    }

    /**
     * Handles create page initialization
     *
     * @param model of the page
     * @return target page address
     */
    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute("trip", new TripDTO());
        return "admin/trip/create";
    }

    /**
     * Handles trip creation
     *
     * @param trip               to be created
     * @param result             of validation
     * @param model              of the page
     * @param redirectAttributes redirection attributes
     * @param uriBuilder         for redirect address
     * @return redirection address
     */
    @RequestMapping(value = "createTrip", method = RequestMethod.POST)
    public String submitCreate(@Valid @ModelAttribute("trip") TripDTO trip, BindingResult result, Model model,
                               RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        if (!ControllerUtils.isResultValid(result, model)) {
            return "/admin/trip/create";
        }
        tripFacade.createTrip(trip);
        redirectAttributes.addFlashAttribute("alert_success", context.getMessage("success.tripCreated",
                new Object[] {trip.getDestination()}, LocaleContextHolder.getLocale()));
        return "redirect:" + uriBuilder.path("/admin/trip/detail/" + trip.getId()).build().encode().toUriString();
    }


}
