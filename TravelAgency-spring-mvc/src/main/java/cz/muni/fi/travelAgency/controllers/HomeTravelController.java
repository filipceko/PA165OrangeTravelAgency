package cz.muni.fi.travelAgency.controllers;

import cz.muni.fi.travelAgency.DTO.TripDTO;
import cz.muni.fi.travelAgency.facade.TripFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

/**
 * Handles homepage
 *
 * @author Filip Cekovsky
 */
@Controller
@RequestMapping("/")
public class HomeTravelController {

    /**
     * Trip facade
     */
    @Autowired
    private TripFacade tripFacade;

    /**
     * Handles main page
     *
     * @param model model of the page
     * @return target page address
     */
    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model) {
        Collection<TripDTO> trips = tripFacade.getAvailableFutureTrip();
        model.addAttribute("trips", trips);
        return "/home";
    }
}
