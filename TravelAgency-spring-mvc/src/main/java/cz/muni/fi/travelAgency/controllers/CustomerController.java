/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.travelAgency.controllers;

import cz.muni.fi.travelAgency.DTO.CustomerCreateDTO;
import cz.muni.fi.travelAgency.DTO.CustomerDTO;
import cz.muni.fi.travelAgency.facade.CustomerFacade;
import cz.muni.fi.travelAgency.service.BeanMappingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedExceptionUtils;
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

import javax.validation.Valid;

/**
 * @author Rajivv/Rithy
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final static Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerFacade customerFacade;

    @Autowired
    private BeanMappingService mapper;

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
        CustomerDTO customerDTO = mapper.mapTo(createDTO, CustomerDTO.class);
        customerFacade.registerCustomer(customerDTO, createDTO.getPassword());
        redirectAttributes.addFlashAttribute("alert_success", "Customer " + createDTO.getSurname() + " was created");
        return "redirect:" + uriBuilder.path("/").build().encode().toUriString();
    }


    @RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model) {
        model.addAttribute("customer", customerFacade.findCustomerById(id));
        return "admin/customer/detail";
    }

    @RequestMapping(value = "edit/{id}" , method = RequestMethod.GET)
    public String getEdit(@PathVariable long id, Model model) {
        CustomerDTO customerDTO = customerFacade.findCustomerById(id);
        CustomerCreateDTO createDTO = mapper.mapTo(customerDTO, CustomerCreateDTO.class);
        createDTO.setPassword(customerDTO.getPasswordHash());
        model.addAttribute("customer", createDTO);
        return "customer/edit";
    }

    @RequestMapping(value = "editCustomer", method = RequestMethod.POST)
    public String submitEdit(@Valid @ModelAttribute("customer") CustomerCreateDTO createDTO,
                             BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes,
                             UriComponentsBuilder uriBuilder) {
//        if (!resultIsValid(bindingResult, model)) {
//            return "customer/edit";
//        }
        if (createDTO == null)
        {
            return "customer/edit";
        }
        CustomerDTO customerDTO = mapper.mapTo(createDTO, CustomerDTO.class);
        customerDTO.setPasswordHash(createDTO.getPassword());
        customerFacade.updateCustomer(customerDTO);
        redirectAttributes.addFlashAttribute("alert_success", "Customer" + createDTO.getSurname() + " updated");
        return "redirect:" + uriBuilder.path("/admin/customer/detail/" + createDTO.getId()).build().encode().toUriString();
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable long id, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        CustomerDTO customerDTO = customerFacade.findCustomerById(id);
        logger.debug("delete({})", id);
        try {
            customerFacade.deleteCustomer(customerDTO);
            redirectAttributes.addFlashAttribute("alert_success", "Customer \"" + customerDTO.getSurname() + "\" was deleted.");
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("customer " + id + " cannot be deleted");
            logger.error(NestedExceptionUtils.getMostSpecificCause(ex).getMessage());
            redirectAttributes.addFlashAttribute("alert_danger", "Customer \"" + customerDTO.getId() + "\" cannot be deleted." + ex.getMessage());
        }
        return "redirect:" + uriBuilder.path("/auth/login").toUriString();
    }

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
