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
        for (ExcursionDTO excursion : excursions){
            initDurationString(excursion);
        }
        model.addAttribute("excursions",excursions);
        return "admin/excursion/list";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String create(@PathVariable long id, Model model){
        ExcursionDTO excursionDTO = excursionFacade.findExcursionById(id);
        initDurationString(excursionDTO);
        model.addAttribute("excursion", excursionDTO);
        return "admin/excursion/detail";
    }

    private void initDurationString(ExcursionDTO excursion){
        excursion.setDurationString(excursion.getExcursionDuration().toString()
                .substring(2)
                .replaceAll("(\\d[HMS])(?!$)", "$1 ")
                .toLowerCase());
    }

//    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
//    public String delete(@PathVariable long id, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
//        ExcursionDTO excursion = excursionFacade.findExcursionById(id);
//        logger.debug("delete({})", id);
//        try {
//            excursionFacade.deleteExcursion(excursion);
//            redirectAttributes.addFlashAttribute("alert_success", "Excursion \"" + excursion.getId() + "\" was deleted.");
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            logger.error("excursion "+id+" cannot be deleted");
//            logger.error(NestedExceptionUtils.getMostSpecificCause(ex).getMessage());
//            redirectAttributes.addFlashAttribute("alert_danger", "Excursion \"" + excursion.getId() + "\" cannot be deleted." + ex.getMessage());
//        }
//        return "redirect:" + uriBuilder.path("/admin/excursion/list").toUriString();
//    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable long id, UriComponentsBuilder uriBuilder, RedirectAttributes attributes){
        try {
            excursionFacade.deleteExcursion(excursionFacade.findExcursionById(id));
            attributes.addFlashAttribute("alert_success", "Trip number "+id+" was canceled.");
        } catch (DataAccessException ex) {
            logger.warn("cannot remove Trip {}", id);
            attributes.addFlashAttribute("alert_danger", "Trip number "+id+" was not canceled. "+ex.getMessage());
        }
        return "redirect:" + uriBuilder.path("/admin/excursion/list").build().encode().toUriString();
    }


}
