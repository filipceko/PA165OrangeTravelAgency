package cz.muni.fi.travelAgency.mvc.config;

import cz.muni.fi.travelAgency.mvc.config.auth.SecurityConfig;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * Extends the class {@link org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer} that
 * <ul>
 * <li>initializes {@link org.springframework.web.servlet.DispatcherServlet Spring MVC dispatcher servlet}</li>
 * <li>maps dispatcher servlet to URL</li>
 * </ul>
 *
 * @author Filip Cekovsky
 */
public class TravelAgencyStartInit extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{TravelAgencySpringMvcConfig.class, SecurityConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("utf-8");
        return new Filter[]{encodingFilter};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }
}
