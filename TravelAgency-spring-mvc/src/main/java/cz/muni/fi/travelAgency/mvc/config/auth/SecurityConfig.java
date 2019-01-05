package cz.muni.fi.travelAgency.mvc.config.auth;

import cz.muni.fi.travelAgency.DTO.CustomerAuthenticateDTO;
import cz.muni.fi.travelAgency.DTO.CustomerDTO;
import cz.muni.fi.travelAgency.exceptions.DataAccessLayerException;
import cz.muni.fi.travelAgency.facade.CustomerFacade;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Inject
    private CustomerFacade customerFacade;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String userEmail = "";
                String password = "";
                Set<GrantedAuthority> authorities = new HashSet<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_" + "Guest"));

                Object principal = authentication.getPrincipal();
                if (principal instanceof String) {
                    userEmail = (String) principal;
                }
                Object credentials = authentication.getCredentials();
                if (credentials instanceof String) {
                    password = (String) credentials;
                }

                boolean isAuthenticated = customerFacade.authenticate(new CustomerAuthenticateDTO(userEmail, password));
                if (isAuthenticated) {
                    CustomerDTO user = customerFacade.findCustomerByEmail(userEmail);
                    if (user.isAdmin()) {
                        authorities.add(new SimpleGrantedAuthority("ROLE_" + "Admin"));
                    } else {
                        authorities.add(new SimpleGrantedAuthority("ROLE_" + "Customer"));
                    }
                    return new UsernamePasswordAuthenticationToken(principal, credentials, authorities);
                }
                return null;
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return true;
            }
        });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()

                // Permit anonymous access
                .antMatchers("/").permitAll()
                .antMatchers("/auth/login", "/customer/registration", "/customer/create").permitAll()
                .antMatchers("/home", "/trips/**").permitAll()
                .antMatchers("/reservation/**").hasAnyRole("Customer")
                .antMatchers("/admin/**").hasAnyRole("Admin")
                .antMatchers("/customer/edit", "/customer/editCustomer", "/customer/delete/*").hasAnyRole("Admin", "User")

                .antMatchers("/**").hasAnyRole("Admin")
                .anyRequest().authenticated()

                // Login
                .and()
                .formLogin()
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/login")
                .failureUrl("/auth/login")
                .usernameParameter("email").passwordParameter("password")
                .successHandler(
                        (request, response, authentication) -> {
                            String email = (String) authentication.getPrincipal();
                            CustomerDTO customer = customerFacade.findCustomerByEmail(email);
                            HttpSession session = request.getSession(true);
                            session.setAttribute("authenticatedUser", customer);
                            response.sendRedirect("/travel.agency/");
                        })
                .permitAll()

                // Logout
                .and()
                .logout()
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll();
    }
}