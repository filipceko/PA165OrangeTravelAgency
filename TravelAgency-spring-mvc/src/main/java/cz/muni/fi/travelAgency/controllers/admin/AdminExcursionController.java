package cz.muni.fi.travelAgency.controllers.admin;

import cz.muni.fi.travelAgency.DTO.ExcursionCreateDTO;
import cz.muni.fi.travelAgency.DTO.ExcursionDTO;
import cz.muni.fi.travelAgency.controllers.utils.ControllerUtils;
import cz.muni.fi.travelAgency.facade.ExcursionFacade;
import cz.muni.fi.travelAgency.service.BeanMappingService;
import cz.muni.fi.travelAgency.validators.ExcursionValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Collection;

/**
 * Controller handling admin excursion pages
 *
 * @author Simona Raucinova
 */
@Controller
@RequestMapping("admin/excursion")
public class AdminExcursionController {

    /**
     * Logger for this class.
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminTripController.class);

    /**
     * Application context
     */
    @Autowired
    private ApplicationContext context;

    /**
     * Excursion validation handling class
     */
    @Autowired
    private ExcursionValidator validator;

    /**
     * Excursion facade
     */
    @Autowired
    private ExcursionFacade excursionFacade;

    /**
     * Bean mapper
     */
    @Autowired
    private BeanMappingService mapper;

    /**
     * Initializes validator
     *
     * @param binder data binder
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        if (binder.getTarget() instanceof ExcursionDTO) {
            binder.addValidators(validator);
        }
    }

    /**
     * Handles excursion creation page
     *
     * @param model of the page
     * @return target page address
     */
    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String create(Model model) {
        LOGGER.debug("new()");
        model.addAttribute("excursionCreate", new ExcursionCreateDTO());
        return "admin/excursion/create";
    }

    /**
     * Handles excursion creation
     *
     * @param creationDTO        excursion to create
     * @param bindingResult      validation result
     * @param model              of the page
     * @param redirectAttributes redirection attributes
     * @param uriBuilder         redirection address builder
     * @return redirection address
     */
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String submitCreate(@Valid @ModelAttribute("excursionCreate") ExcursionCreateDTO creationDTO, BindingResult bindingResult,
                               Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        if (!ControllerUtils.isResultValid(bindingResult, model)) {
            return "admin/excursion/create";
        }
        ExcursionDTO excursionDTO = mapper.mapManipulationToEntityDTO(creationDTO);
        excursionFacade.createExcursion(excursionDTO);
        redirectAttributes.addFlashAttribute("alert_success", context.getMessage("success.excursionCreated",
                new Object[]{excursionDTO.getId()}, LocaleContextHolder.getLocale()));
        return "redirect:" + uriBuilder.path("/admin/excursion/detail/" + excursionDTO.getId()).build().encode().toUriString();
    }

    /**
     * Handles displaying all excursions
     *
     * @param model of the page
     * @return target page address
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Model model) {
        Collection<ExcursionDTO> excursions = excursionFacade.getAllExcursions();
        model.addAttribute("excursions", excursions);
        return "admin/excursion/list";
    }

    /**
     * Handles displaying detail for an excursion
     *
     * @param id    of the excursion to be displayed
     * @param model of the page
     * @return target page address
     */
    @RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model) {
        ExcursionDTO excursionDTO = excursionFacade.findExcursionById(id);
        model.addAttribute("excursion", excursionDTO);
        return "admin/excursion/detail";
    }

    /**
     * Handles excursion deletion
     *
     * @param id                 of the excursion to be deleted
     * @param uriBuilder         for the redirection
     * @param redirectAttributes redirection attributes
     * @return redirection address
     */
    @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        ExcursionDTO excursion = excursionFacade.findExcursionById(id);
        LOGGER.debug("delete({})", id);
        try {
            excursionFacade.deleteExcursion(excursion);
            redirectAttributes.addFlashAttribute("alert_success", context.getMessage("success.excursionCanceled",
                    new Object[]{id}, LocaleContextHolder.getLocale()));
        } catch (Exception ex) {
            LOGGER.error("Could not delete excursion #" + id, ex);
            redirectAttributes.addFlashAttribute("alert_danger", context.getMessage("error.excursionNotCanceled",
                    new Object[]{id}, LocaleContextHolder.getLocale()));
        }
        return "redirect:" + uriBuilder.path("/admin/excursion/list").toUriString();
    }

    /**
     * Handles excursion editing page
     *
     * @param id    of the excursion to be edited
     * @param model of the page
     * @return target page address
     */
    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String getEdit(@PathVariable long id, Model model) {
        ExcursionDTO excursionDTO = excursionFacade.findExcursionById(id);
        ExcursionCreateDTO manipulationDTO = mapper.mapTo(excursionDTO, ExcursionCreateDTO.class);
        manipulationDTO.setDurationMinutes(excursionDTO.getExcursionDuration().toMinutes());
        manipulationDTO.setTripId(excursionDTO.getTrip().getId());
        model.addAttribute("excursion", manipulationDTO);
        return "admin/excursion/edit";
    }

    /**
     * Handles excursion editing
     *
     * @param creationDTO        of the excursion
     * @param result             validation result
     * @param model              of the page
     * @param redirectAttributes redirection attributes
     * @param uriBuilder         of the redirection
     * @return redirection address
     */
    @RequestMapping(value = "editExcursion", method = RequestMethod.POST)
    public String submitEdit(@Valid @ModelAttribute("excursion") ExcursionCreateDTO creationDTO,
                             BindingResult result, Model model, RedirectAttributes redirectAttributes,
                             UriComponentsBuilder uriBuilder) {
        if (!ControllerUtils.isResultValid(result, model)) {
            return "admin/excursion/edit";
        }
        ExcursionDTO excursionDTO = mapper.mapManipulationToEntityDTO(creationDTO);
        excursionFacade.updateExcursion(excursionDTO);
        redirectAttributes.addFlashAttribute("alert_success", context.getMessage("success.excursionEdited",
                new Object[]{creationDTO.getId()}, LocaleContextHolder.getLocale()));
        return "redirect:" + uriBuilder.path("/admin/excursion/detail/" + excursionDTO.getId()).build().encode().toUriString();
    }
}
