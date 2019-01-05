package cz.muni.fi.travelAgency.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = {"/auth"})
public class AuthenticationController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String authForm(HttpServletRequest req) {
        if (req.getSession(true).getAttribute("authUser") != null) {
            return "redirect:/";
        }
        return "/auth/login";
    }
}
