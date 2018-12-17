package cz.muni.fi.travelAgency.controllers;

import cz.muni.fi.travelAgency.DTO.ReservationDTO;
import cz.muni.fi.travelAgency.facade.ReservationFacade;
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
@RequestMapping("admin/reservation")
public class AdminReservationController {

    private final static Logger logger = LoggerFactory.getLogger(AdminReservationController.class);

    @Autowired
    private ReservationFacade reservationFacade;

    @RequestMapping(value = "list/{filter}", method = RequestMethod.GET)
    public String list(@PathVariable String filter, Model model) {
        Collection<ReservationDTO> reservations;
        switch (filter) {
            case "all":
                reservations = reservationFacade.getAllReservations();
                break;
            default:
                reservations = new ArrayList<>();
                model.addAttribute("alert_danger", "Unknown filter " + filter);
        }
        model.addAttribute("reservations", reservations);
        return "admin/reservation/list";
    }

    @RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model) {
        model.addAttribute("reservation", reservationFacade.getById(id));
        return "admin/reservation/detail";
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes attributes) {
        try {
            reservationFacade.delete(reservationFacade.getById(id));
            attributes.addFlashAttribute("alert_success", "Reservation number " + id + " was canceled.");
        } catch (DataAccessException ex) {
            logger.warn("cannot remove Reservation {}", id);
            attributes.addFlashAttribute("alert_danger", "Reservation number " + id + " was not canceled. " + ex.getMessage());
        }
        return "redirect:" + uriBuilder.path("/admin/reservation/list/all").build().encode().toUriString();
    }
}
