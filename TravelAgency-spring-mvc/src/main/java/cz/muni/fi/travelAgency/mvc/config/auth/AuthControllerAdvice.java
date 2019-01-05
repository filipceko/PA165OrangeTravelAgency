package cz.muni.fi.travelAgency.mvc.config.auth;

import cz.muni.fi.travelAgency.DTO.CustomerDTO;
import cz.muni.fi.travelAgency.facade.CustomerFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class AuthControllerAdvice {

    private Logger logger = LoggerFactory.getLogger(AuthControllerAdvice.class);

    @Autowired
    CustomerFacade customerFacade;

    @ModelAttribute("authenticatedUser")
    public CustomerDTO getAuthenticatedUser(){
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.debug("Getting customer info for " + email);
        if (email != null && !email.isEmpty() && !email.equals("anonymousUser")) {
            return customerFacade.findCustomerByEmail(email);
        }
        return null;
    }
}
