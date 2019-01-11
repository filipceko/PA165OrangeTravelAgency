package cz.muni.fi.travelAgency.controllers;

import cz.muni.fi.travelAgency.DTO.CheapTravelDTO;
import cz.muni.fi.travelAgency.DTO.TripDTO;
import cz.muni.fi.travelAgency.facade.TripFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Provides the public shopping interface.
 *
 * @author Rithy, Filip
 */
@Controller
@RequestMapping("/trips")
public class TripsController {
    /**
     * Logger for this class
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(TripsController.class);

    /**
     * Application context
     */
    @Autowired
    private ApplicationContext context;

    /**
     * Trip Facade
     */
    @Autowired
    private TripFacade tripFacade;

    /**
     * Handles trip presentation page
     *
     * @param model of the page
     * @return target page address
     */
    @RequestMapping("/show")
    public String list(Model model) {
        LOGGER.debug("show()");
        Collection<TripDTO> allTrips = tripFacade.getAllTrips();
        model.addAttribute("trips", allTrips);
        return "trips/show";
    }

    /**
     * Handles detail displaying for a trip
     *
     * @param id    of the displayed trip
     * @param model of the page
     * @return target page address
     */
    @RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model) {
        model.addAttribute("trip", tripFacade.getTripById(id));
        return "trips/detail";
    }

    /**
     * Handles displaying trips with filters
     *
     * @param filter to be used
     * @param model  for the page
     * @return target page address
     */
    @RequestMapping(value = "show/{filter}", method = RequestMethod.GET)
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
                        new Object[]{filter}, LocaleContextHolder.getLocale()));
        }
        model.addAttribute("trips", trips);
        return "trips/show";
    }

    /**
     * Handles displaying trips for given amount of money
     *
     * @param moneyString representing available amount of money
     * @param model       of the page
     * @return target page address
     */
    @RequestMapping(value = "forMoney", method = RequestMethod.POST)
    public String filter(@RequestParam("money") String moneyString, Model model) {
        Double money = Double.parseDouble(moneyString);
        CheapTravelDTO result = tripFacade.tripsForMoney(money);
        model.addAttribute("trips", result.getTrips());
        model.addAttribute("excursions", result.getExcursions());
        return "/trips/forMoney";
    }
}
