/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.travelAgency.controllers;

import cz.muni.fi.travelAgency.DTO.CustomerCreateDTO;
import cz.muni.fi.travelAgency.DTO.CustomerDTO;
import cz.muni.fi.travelAgency.DTO.ReservationDTO;
import cz.muni.fi.travelAgency.facade.CustomerFacade;
import cz.muni.fi.travelAgency.facade.ReservationFacade;
import java.util.ArrayList;
import java.util.Collection;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Rajivv
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final static Logger logger = LoggerFactory.getLogger(AdminCustomerController.class);

    @Autowired
    private CustomerFacade customerFacade;
    @Autowired
    private ReservationFacade reservationFacade;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Your username and password is invalid.");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }
        return "customer/login";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        logger.debug("new()");
        model.addAttribute("customerCreate", new CustomerCreateDTO());
        return "customer/registration";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String submitCreate(@Valid @ModelAttribute("customerCreate") CustomerCreateDTO createDTO,
            BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriBuilder) {
        logger.debug("create(customerCreate={})", createDTO);
        if (!resultIsValid(bindingResult, model)) {
            return "customer/registration";
        }
        CustomerDTO customerDTO = new CustomerDTO(
                createDTO.getName(),
                createDTO.getSurname(),
                createDTO.getEmail(),
                createDTO.getPassword(),
                createDTO.getPhoneNumber(),
                createDTO.getPassportNumber(),
                createDTO.getDateOfBirth());
        customerFacade.registerCustomer(customerDTO, createDTO.getPassword());
        redirectAttributes.addFlashAttribute("alert_success", "Customer " + createDTO.getId() + " was created");
        return "redirect:" + uriBuilder.path("customer/login" + createDTO.getId()).build().encode().toUriString();
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String getEdit(@PathVariable long id, Model model) {
        CustomerDTO customerDTO = customerFacade.findCustomerById(id);
        CustomerCreateDTO createDTO = new CustomerCreateDTO(
                customerDTO.getName(),
                customerDTO.getSurname(),
                customerDTO.getEmail(),
                customerDTO.getPasswordHash(),
                customerDTO.getPhoneNumber(),
                customerDTO.getPassportNumber(),
                customerDTO.getDateOfBirth());
        model.addAttribute("customerSetting", createDTO);
        return "customer/setting";
    }

    @RequestMapping(value = "/editCustomer", method = RequestMethod.POST)
    public String submitEdit(@Valid @ModelAttribute("customer") CustomerCreateDTO createDTO,
            BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriBuilder) {
        if (!resultIsValid(bindingResult, model)) {
            return "customer/setting";
        }
        CustomerDTO customerDTO = new CustomerDTO(
                createDTO.getName(),
                createDTO.getSurname(),
                createDTO.getEmail(),
                createDTO.getPassword(),
                createDTO.getPhoneNumber(),
                createDTO.getPassportNumber(),
                createDTO.getDateOfBirth());
        customerFacade.updateCustomer(customerDTO);
        redirectAttributes.addFlashAttribute("alert_success", "Customer" + createDTO.getId() + " updated");
        return "redirect:" + uriBuilder.path("customer/setting" + createDTO.getId()).build().encode().toUriString();

    }
    
    @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        CustomerDTO customerDTO  = customerFacade.findCustomerById(id);
        logger.debug("delete({})", id);
        try {
            customerFacade.deleteCustomer(customerDTO);
            redirectAttributes.addFlashAttribute("alert_success", "Customer \"" + customerDTO.getId() + "\" was deleted.");
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("customer " + id + " cannot be deleted");
            logger.error(NestedExceptionUtils.getMostSpecificCause(ex).getMessage());
            redirectAttributes.addFlashAttribute("alert_danger", "Customer \"" + customerDTO.getId() + "\" cannot be deleted." + ex.getMessage());
        }
        return "redirect:" + uriBuilder.path("customer/login").toUriString();
    }


    

//    @RequestMapping(value = "/reservation_overview/{filter}", method = RequestMethod.GET)
//    public String overview(@PathVariable String filter, Model model
//    ) {
//        Collection<ReservationDTO> reservations;
//        switch (filter) {
//            case "all":
//                reservations = reservationFacade.getAllReservations();
//                break;
//            default:
//                reservations = new ArrayList<>();
//                model.addAttribute("alert_danger", "Unknown filter " + filter);
//        }
//        model.addAttribute("reservations_overview", reservations);
//        return "customer/reservation_overview";
//    }
    private boolean resultIsValid(BindingResult result, Model model) {
        if (result.hasErrors()) {
            for (ObjectError ge : result.getGlobalErrors()) {
                logger.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : result.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                logger.trace("FieldError: {}", fe);
            }
            return false;
        }
        return true;
    }

}
