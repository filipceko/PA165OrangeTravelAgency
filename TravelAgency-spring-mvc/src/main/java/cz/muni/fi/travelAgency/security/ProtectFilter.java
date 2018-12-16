package cz.muni.fi.travelAgency.security;

import cz.muni.fi.travelAgency.DTO.CustomerAuthenticateDTO;
import cz.muni.fi.travelAgency.DTO.CustomerDTO;
import cz.muni.fi.travelAgency.facade.CustomerFacade;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

import org.slf4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;


@WebFilter(urlPatterns = {"/admin/*"})
public class ProtectFilter implements Filter{

    private final static Logger logger = LoggerFactory.getLogger(ProtectFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("Running in {}", filterConfig.getServletContext().getServerInfo());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String auth = request.getHeader("Authorization");
        if (auth == null) {
            response401(response);
            return;
        }
        String[] creds = parseAuthHeader(auth);
        String logname = creds[0];
        String password = creds[1];

        CustomerFacade customerFacade = WebApplicationContextUtils.getWebApplicationContext(servletRequest.getServletContext()).getBean(CustomerFacade.class);
        CustomerDTO matchingUser = customerFacade.findCustomerByEmail(logname);
        if (matchingUser == null) {
            logger.warn("no user with email {}", logname);
            response401(response);
            return;
        }

        CustomerAuthenticateDTO customerAuthenticateDTO = new CustomerAuthenticateDTO();
        customerAuthenticateDTO.setCustomerId(matchingUser.getId());
        customerAuthenticateDTO.setPassword(password);
        if (!customerFacade.isAdmin(matchingUser)) {
            logger.warn("user not admin {}", matchingUser);
            response401(response);
            return;
        }

        if (!customerFacade.authenticate(customerAuthenticateDTO)) {
            logger.warn("wrong credentials: user={} password={}", creds[0], creds[1]);
            response401(response);
            return;
        }
        request.setAttribute("authenticatedUser", matchingUser);
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    private void response401(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("WWW-Authenticate", "Basic realm=\"type email and password\"");
        response.getWriter().println("<html><body><h1>401 Unauthorized</h1> Go away ...</body></html>");
    }

    private String[] parseAuthHeader(String auth) {
        return new String(Base64.getDecoder().decode(auth.split(" ")[1])).split(":", 2);
    }
}
