package cz.muni.fi.travelAgency.controllers;


import cz.muni.fi.travelAgency.DTO.*;
import cz.muni.fi.travelAgency.controllers.admin.AdminTripController;
import cz.muni.fi.travelAgency.facade.ReservationFacade;
import cz.muni.fi.travelAgency.facade.TripFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Filip Cekovsky
 */
@Controller
@RequestMapping("/reservation")
public class ReservationController {

    private final static Logger logger = LoggerFactory.getLogger(AdminTripController.class);

    @Autowired
    TripFacade tripFacade;

    @Autowired
    ReservationFacade reservationFacade;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createReservation(Model model) {
        model.addAttribute("trips", tripFacade.getAllTrips());
        return "reservation/pickTrip";
    }

    @RequestMapping(value = "/create/{tripId}", method = RequestMethod.GET)
    public String createReservation(@PathVariable long tripId, Model model) {
        TripDTO tripDTO = tripFacade.getTripById(tripId);
        model.addAttribute("trip", tripDTO);
        ReservationCreateDTO createDTO = new ReservationCreateDTO(tripId, tripDTO.getExcursions());
        model.addAttribute("reservation", createDTO);
        return "reservation/create";
    }

    @RequestMapping(value = "/createReservation", method = RequestMethod.POST)
    public String submitReservation(@ModelAttribute("reservation") ReservationCreateDTO createDTO,
                                    @ModelAttribute("authenticatedUser") CustomerDTO customer,
                                    Model model,
                                    RedirectAttributes redirectAttributes) {
        TripDTO trip = tripFacade.getTripById(createDTO.getTripId());
        Set<ExcursionDTO> excursions = trip.getExcursions().stream().filter(excursion -> createDTO
                .getExcursions().contains(excursion.getDestination())).collect(Collectors.toSet());
        ReservationDTO reservationDTO = new ReservationDTO(customer, trip, excursions, LocalDate.now());
        reservationFacade.createReservation(reservationDTO);
        redirectAttributes.addFlashAttribute("alert_success", "Reservation created");
        model.addAttribute("trips", tripFacade.getAllTrips());
        return "/home";
    }
}
