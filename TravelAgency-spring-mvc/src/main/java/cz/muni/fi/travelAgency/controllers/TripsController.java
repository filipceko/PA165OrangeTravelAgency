package cz.muni.fi.travelAgency.controllers;

import cz.muni.fi.travelAgency.DTO.TripDTO;
import cz.muni.fi.travelAgency.facade.TripFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

/**
 * Provides the public shopping interface.
 */
@Controller
@RequestMapping("/trips")
public class TripsController {

    private final static Logger log = LoggerFactory.getLogger(TripsController.class);

    @Autowired
    private TripFacade tripFacade;

    /**
     * Shows all categories and products.
     *
     * @param model data to display
     * @return JSP page name
     */
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

}
