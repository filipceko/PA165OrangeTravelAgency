package cz.muni.fi.travelAgency.controllers;

import cz.muni.fi.travelAgency.DTO.CustomerCreateDTO;
import cz.muni.fi.travelAgency.DTO.CustomerDTO;
import cz.muni.fi.travelAgency.DTO.ReservationDTO;
import cz.muni.fi.travelAgency.controllers.utils.ControllerUtils;
import cz.muni.fi.travelAgency.facade.CustomerFacade;
import cz.muni.fi.travelAgency.facade.ReservationFacade;
import cz.muni.fi.travelAgency.service.BeanMappingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collection;

/**
 * Handles customer related pages
 *
 * @author Rajivv/Rithy
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {
    /**
     * Logger for this class
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
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
     * customer facade
     */
    @Autowired
    private CustomerFacade customerFacade;
    /**
     * Bean mapper
     */
    @Autowired
    private BeanMappingService mapper;

    /**
     * Handles registration page
     *
     * @param model of the page
     * @return target page address
     */
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("customerCreate", new CustomerCreateDTO());
        return "customer/registration";
    }

    /**
     * Handles new customer creation
     *
     * @param createDTO          customer to be created
     * @param bindingResult      result of validation
     * @param model              of the page
     * @param redirectAttributes redirection attributes
     * @param uriBuilder         for redirection
     * @return redirection address
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String submitCreate(@Valid @ModelAttribute("customerCreate") CustomerCreateDTO createDTO,
                               BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes,
                               UriComponentsBuilder uriBuilder) {
        if (!ControllerUtils.isResultValid(bindingResult, model)) {
            return "customer/registration";
        }
        CustomerDTO customerDTO = mapper.mapTo(createDTO, CustomerDTO.class);
        customerFacade.registerCustomer(customerDTO, createDTO.getPassword());
        redirectAttributes.addFlashAttribute("alert_success", context.getMessage("success.accountCreated",
                new Object[]{customerDTO.getName(), customerDTO.getSurname()}, LocaleContextHolder.getLocale()));
        return "redirect:" + uriBuilder.path("/").build().encode().toUriString();
    }

    /**
     * Handles displaying customers information
     *
     * @param customer currently logged in customer
     * @param model    of the page
     * @return target page address
     */
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public String detail(@ModelAttribute("authenticatedUser") CustomerDTO customer, Model model) {
        model.addAttribute("customer", customer);
        return "customer/detail";
    }

    /**
     * Handles customer information editing page
     *
     * @param customer to be edited
     * @param model    of the page
     * @return target page address
     */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String getEdit(@ModelAttribute("authenticatedUser") CustomerDTO customer,
                          Model model) {
        CustomerDTO customerDTO = customerFacade.findCustomerById(customer.getId());
        CustomerCreateDTO createDTO = mapper.mapTo(customerDTO, CustomerCreateDTO.class);
        createDTO.setPassword(customerDTO.getPasswordHash());
        model.addAttribute("customer", createDTO);
        return "customer/edit";
    }

    /**
     * Handles customer updating.
     *
     * @param createDTO          customer to be updated
     * @param bindingResult      result of validation
     * @param model              of the page
     * @param redirectAttributes attributes for the redirection
     * @param uriBuilder         for the redirection
     * @return redirection address
     */
    @RequestMapping(value = "editCustomer", method = RequestMethod.POST)
    public String submitEdit(@Valid @ModelAttribute("customer") CustomerCreateDTO createDTO, BindingResult bindingResult,
                             Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        if (ControllerUtils.isResultValid(bindingResult, model)) {
            return "customer/edit";
        }
        CustomerDTO customerDTO = mapper.mapTo(createDTO, CustomerDTO.class);
        customerDTO.setPasswordHash(createDTO.getPassword());
        customerFacade.updateCustomer(customerDTO);
        redirectAttributes.addFlashAttribute("alert_success", context.getMessage("success.accountUpdated",
                new Object[]{}, LocaleContextHolder.getLocale()));
        return "redirect:" + uriBuilder.path("/customer/detail").build().encode().toUriString();
    }

    /**
     * Handles account deletion page
     *
     * @param customer           to be deleted
     * @param uriBuilder         redirection builder
     * @param redirectAttributes redirection attributes
     * @param request            http servlet request
     * @return redirection address
     */
    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public String delete(@ModelAttribute("authenticatedUser") CustomerDTO customer, UriComponentsBuilder uriBuilder,
                         RedirectAttributes redirectAttributes, HttpServletRequest request) {
        CustomerDTO customerDTO = customerFacade.findCustomerById(customer.getId());
        try {
            customerFacade.deleteCustomer(customerDTO);
            redirectAttributes.addFlashAttribute("alert_success", context.getMessage("success.accountDeleted",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        } catch (Exception ex) {
            LOGGER.error("Customer #" + customer.getId() + " could not be deleted", ex);
            redirectAttributes.addFlashAttribute("alert_danger", context.getMessage("error.customerNotDeleted",
                    new Object[]{customerDTO.getName(), customerDTO.getSurname()}, LocaleContextHolder.getLocale()));
        }
        try {
            request.logout();
        } catch (ServletException ex) {
            LOGGER.error("Could not logout user after account deletion", ex);
            redirectAttributes.addFlashAttribute("alert_danger", context.getMessage("error.notLoggedOut",
                    new Object[]{}, LocaleContextHolder.getLocale()));
        }
        return "redirect:" + uriBuilder.path("/auth/login").toUriString();
    }

    /**
     * Handles reservation overview page.
     *
     * @param customer to display reservations for
     * @param model    of the page
     * @return target page address
     */
    @RequestMapping(value = "reservationView", method = RequestMethod.GET)
    public String detailReservation(@ModelAttribute("authenticatedUser") CustomerDTO customer, Model model) {
        Collection<ReservationDTO> reservationDTO = reservationFacade.getByCustomer(customer.getId());
        model.addAttribute("reservations", reservationDTO);
        model.addAttribute("customer", customer);
        return "customer/reservationView";
    }
}
