package cz.muni.fi.travelAgency.controllers;

import cz.muni.fi.travelAgency.DTO.ExcursionDTO;
import cz.muni.fi.travelAgency.facade.ExcursionFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;

@Controller
@RequestMapping("admin/excursion")
public class AdminExcursionController {

    private final static Logger logger = LoggerFactory.getLogger(AdminTripController.class);

    @Autowired
    private ExcursionFacade excursionFacade;

    /**
     * Shows a list of excursions.
     *
     * @param model data to display
     * @return JSP page name
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        Collection<ExcursionDTO> excursions = excursionFacade.getAllExcursions();
        model.addAttribute("excursions",excursions);
        return "admin/excursion/list";
    }

    @RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
    public String create(@PathVariable long id, Model model){
        model.addAttribute("excursion", excursionFacade.findExcursionById(id));
        return "admin/excursion/detail";
    }

}
