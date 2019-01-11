package cz.muni.fi.travelAgency.controllers.admin;

import cz.muni.fi.travelAgency.DTO.ReservationDTO;
import cz.muni.fi.travelAgency.facade.ReservationFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

/**
 * Controller handling admin pages for reservations
 *
 * @author Rithy
 */
@Controller
@RequestMapping("admin/reservation")
public class AdminReservationController {

    /**
     * Class logger
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminReservationController.class);

    /**
     * Application context
     */
    @Autowired
    private ApplicationContext context;

    /**
     * Reservation facade
     */
    @Autowired
    private ReservationFacade reservationFacade;

    /**
     * Handles reservation displaying
     *
     * @param model of the page
     * @return target page address
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Model model) {
        Collection<ReservationDTO> reservations;
        reservations = reservationFacade.getAllReservations();
        model.addAttribute("reservations", reservations);
        return "admin/reservation/list";
    }

    /**
     * Handles reservation displaying for give dates
     *
     * @param fromString earliest starting date
     * @param toString   latest ending date
     * @param model      of the page
     * @return target page address
     */
    @RequestMapping(value = "dateList", method = RequestMethod.POST)
    public String filter(@RequestParam("fromDate") String fromString,
                         @RequestParam("toDate") String toString,
                         Model model) {
        LocalDate fromDate = (fromString == null || fromString.isEmpty()) ? null :
                LocalDate.parse(fromString, DateTimeFormatter.ofPattern("MM/dd/yy"));
        LocalDate toDate = (toString == null || toString.isEmpty()) ? null :
                LocalDate.parse(toString, DateTimeFormatter.ofPattern("MM/dd/yy"));
        Collection<ReservationDTO> reservations = reservationFacade.getReservationByInterval(fromDate, toDate);
        model.addAttribute("reservations", reservations);
        model.addAttribute("alert_info", context.getMessage("info.tripsFromTo",
                new Object[]{fromString, toString}, LocaleContextHolder.getLocale()));
        return "/admin/reservation/list";
    }

    /**
     * Handles reservation detail page
     *
     * @param id    of the reservation
     * @param model of the page
     * @return target page address
     */
    @RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model) {
        model.addAttribute("reservation", reservationFacade.getById(id));
        return "admin/reservation/detail";
    }

    /**
     * Handles reservation deletion
     *
     * @param id         of the reservation to be deleted
     * @param uriBuilder for the redirection page
     * @param attributes for redirection page
     * @return redirection address
     */
    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable long id, UriComponentsBuilder uriBuilder, RedirectAttributes attributes) {
        try {
            reservationFacade.delete(reservationFacade.getById(id));
            attributes.addFlashAttribute("alert_success", context.getMessage("success.reservationCanceled",
                    new Object[]{id}, LocaleContextHolder.getLocale()));
        } catch (DataAccessException ex) {
            LOGGER.warn("cannot remove Reservation #" + id, ex);
            attributes.addFlashAttribute("alert_danger", context.getMessage("error.reservationNotCanceled",
                    new Object[]{id}, LocaleContextHolder.getLocale()));
        }
        return "redirect:" + uriBuilder.path("/admin/reservation/list").build().encode().toUriString();
    }
}
