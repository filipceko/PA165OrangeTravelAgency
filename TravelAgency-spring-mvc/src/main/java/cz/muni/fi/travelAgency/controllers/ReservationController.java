package cz.muni.fi.travelAgency.controllers;

import cz.muni.fi.travelAgency.DTO.*;
import cz.muni.fi.travelAgency.facade.ReservationFacade;
import cz.muni.fi.travelAgency.facade.TripFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles customer reservation pages and operations
 *
 * @author Filip Cekovsky
 */
@Controller
@RequestMapping("/reservation")
public class ReservationController {

    /**
     * trip facade
     */
    @Autowired
    TripFacade tripFacade;
    /**
     * reservation facade
     */
    @Autowired
    ReservationFacade reservationFacade;
    /**
     * Application context
     */
    @Autowired
    private ApplicationContext context;

    /**
     * Handles reservation creation page
     *
     * @param tripId   of the trip to be reservation
     * @param model    of the page
     * @param customer making the reservation
     * @return target page address
     */
    @RequestMapping(value = "/create/{tripId}", method = RequestMethod.GET)
    public String createReservation(@PathVariable long tripId, Model model,
                                    @ModelAttribute("authenticatedUser") CustomerDTO customer) {
        if (alreadyBooked(customer.getId(), tripId)) {
            model.addAttribute("alert_info", context.getMessage("info.alreadyBooked",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }
        TripDTO tripDTO = tripFacade.getTripById(tripId);
        model.addAttribute("trip", tripDTO);
        ReservationCreateDTO createDTO = new ReservationCreateDTO(tripId, tripDTO.getExcursions());
        model.addAttribute("reservation", createDTO);
        return "reservation/create";
    }

    /**
     * Handles reservation creation
     *
     * @param createDTO          reservation to create
     * @param customer           making the reservation
     * @param model              for the page
     * @param redirectAttributes attributes for redirection page
     * @return target page address
     */
    @RequestMapping(value = "/createReservation", method = RequestMethod.POST)
    public String submitReservation(@ModelAttribute("reservation") ReservationCreateDTO createDTO,
                                    @ModelAttribute("authenticatedUser") CustomerDTO customer,
                                    Model model, RedirectAttributes redirectAttributes) {
        TripDTO trip = tripFacade.getTripById(createDTO.getTripId());
        if (alreadyBooked(customer.getId(), trip.getId())) {
            model.addAttribute("alert_danger", context.getMessage("error.alreadyBooked",
                    new Object[]{}, LocaleContextHolder.getLocale()));
            model.addAttribute("trip", trip);
            model.addAttribute("reservation", createDTO);
            return "/reservation/create";
        }
        Set<ExcursionDTO> excursions = trip.getExcursions().stream().filter(excursion -> createDTO
                .getExcursions().contains(excursion.getDestination())).collect(Collectors.toSet());
        ReservationDTO reservationDTO = new ReservationDTO(customer, trip, excursions, LocalDate.now());
        reservationFacade.createReservation(reservationDTO);
        redirectAttributes.addFlashAttribute("alert_success", context.getMessage("success.reservationCreated",
                new Object[]{}, LocaleContextHolder.getLocale()));
        model.addAttribute("trips", tripFacade.getAllTrips());
        return "/home";
    }

    /**
     * Decides if given customer already booked given trip
     *
     * @param customerId customer
     * @param tripId     trip
     * @return true if already booked, false otherwise
     */
    private boolean alreadyBooked(Long customerId, Long tripId) {
        Collection<ReservationDTO> oldReservations = reservationFacade.getByCustomer(customerId);
        Set<ReservationDTO> bookedSet = oldReservations.stream()
                .filter(reservation -> reservation.getTrip().getId().equals(tripId))
                .collect(Collectors.toSet());
        return !bookedSet.isEmpty();
    }
}
