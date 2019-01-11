package cz.muni.fi.travelAgency.controllers.utils;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

/***
 * Common utilities required by controllers
 *
 * @author Filip Cekovsky
 */
public class ControllerUtils {
    public static boolean isResultValid(BindingResult result, Model model) {
        if (result.hasErrors()) {
            for (ObjectError ge : result.getGlobalErrors()) {
                model.addAttribute(ge.getObjectName() + "_error", true);
            }
            for (FieldError fe : result.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
            }
            return false;
        }
        return true;
    }
}
