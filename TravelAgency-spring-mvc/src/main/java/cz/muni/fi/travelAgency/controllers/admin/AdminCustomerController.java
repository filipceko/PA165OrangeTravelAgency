package cz.muni.fi.travelAgency.controllers.admin;

import cz.muni.fi.travelAgency.DTO.CustomerDTO;
import cz.muni.fi.travelAgency.facade.CustomerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Handles admin customer pages.
 *
 * @author Rajivv
 */
@Controller
@RequestMapping("admin/customer")
public class AdminCustomerController {
    /**
     * Customer facade
     */
    @Autowired
    private CustomerFacade customerFacade;

    /**
     * Handles displaying all customers.
     *
     * @param model of the page
     * @return target page address
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Model model) {
        List<CustomerDTO> customers = customerFacade.getAllCustomers();
        model.addAttribute("customers", customers);
        return "admin/customer/list";
    }

    /**
     * Handles detail displaying
     *
     * @param id    of displayed customer
     * @param model of the page
     * @return target page address
     */
    @RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model) {
        model.addAttribute("customer", customerFacade.findCustomerById(id));
        return "admin/customer/detail";
    }

}
