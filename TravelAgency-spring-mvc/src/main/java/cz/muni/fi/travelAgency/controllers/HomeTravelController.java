package cz.muni.fi.travelAgency.controllers;

import cz.muni.fi.travelAgency.DTO.ReservationDTO;
import cz.muni.fi.travelAgency.DTO.TripDTO;
import cz.muni.fi.travelAgency.facade.ReservationFacade;
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
@RequestMapping("/")
public class HomeTravelController {

    private final static Logger logger = LoggerFactory.getLogger(HomeTravelController.class);

    @Autowired
    private TripFacade tripFacade;

    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model) {
        Collection<TripDTO> trips = tripFacade.getAvailableFutureTrip();
        model.addAttribute("trips", trips);
        return "/home";
    }



}
