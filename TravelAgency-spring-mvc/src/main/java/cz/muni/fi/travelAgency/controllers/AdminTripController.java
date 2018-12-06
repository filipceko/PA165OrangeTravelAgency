package cz.muni.fi.travelAgency.controllers;

import cz.muni.fi.travelAgency.DTO.TripDTO;
import cz.muni.fi.travelAgency.facade.TripFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Collection;

@Controller
@RequestMapping("admin/trip")
public class AdminTripController {

    private final static Logger logger = LoggerFactory.getLogger(AdminTripController.class);

    @Autowired
    private TripFacade tripFacade;

    @RequestMapping(value = "/list/{filter}", method = RequestMethod.GET)
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

    @RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
    public String create(@PathVariable long id, Model model){
        model.addAttribute("trip", tripFacade.getTripById(id));
        return "admin/trip/detail";
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes attributes){
        try {
            tripFacade.removeTrip(tripFacade.getTripById(id));
            attributes.addFlashAttribute("alert_success", "Trip number "+id+" was canceled.");
        } catch (DataAccessException ex) {
            logger.warn("cannot remove Trip {}", id);
            attributes.addFlashAttribute("alert_danger", "Trip number "+id+" was not canceled. "+ex.getMessage());
        }
        return "redirect:" + uriBuilder.path("/admin/trip/list/all").build().encode().toUriString();
    }
}
