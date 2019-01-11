package cz.muni.fi.travelAgency.controllers;

import cz.muni.fi.travelAgency.DTO.CustomerDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller dedicated to login/out operations
 *
 * @author Filip Cekovsky
 */
@Controller
@RequestMapping(value = {"/auth"})
public class AuthenticationController {

    /**
     * Handles redirecting to login page
     *
     * @param customer logged in persons information or null
     * @return target page address
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String authForm(@ModelAttribute("authenticatedUser") CustomerDTO customer) {
        if (customer != null) {
            return "redirect:/";
        }
        return "/auth/login";
    }
}
