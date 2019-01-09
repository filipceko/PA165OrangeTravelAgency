package cz.muni.fi.travelAgency.controllers;

import cz.muni.fi.travelAgency.DTO.CheapTravelDTO;
import cz.muni.fi.travelAgency.DTO.TripDTO;
import cz.muni.fi.travelAgency.facade.TripFacade;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Provides the public shopping interface.
 */
@Controller
@RequestMapping("/trips")
public class TripsController {

    private final static Logger log = LoggerFactory.getLogger(TripsController.class);

    @Autowired
    private TripFacade tripFacade;

    @RequestMapping("/show")
    public String list(Model model) {
        log.debug("show()");
        Collection<TripDTO> allTrips = tripFacade.getAllTrips();
        model.addAttribute("trips", allTrips);
        return "trips/show";
    }

    @RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model) {
        model.addAttribute("trip", tripFacade.getTripById(id));
        return "trips/detail";
    }
    
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
                model.addAttribute("alert_danger", "Unknown filter " + filter);
        }
        model.addAttribute("trips", trips);
        return "trips/show";
    }

    @RequestMapping(value = "forMoney", method = RequestMethod.POST)
    public String filter(@RequestParam("money") String moneyString, Model model) {
        Double money = Double.parseDouble(moneyString);
        CheapTravelDTO result = tripFacade.tripsForMoney(money);
        model.addAttribute("trips", result.getTrips());
        model.addAttribute("excursions", result.getExcursions());
        return "/trips/forMoney";
    }
}
