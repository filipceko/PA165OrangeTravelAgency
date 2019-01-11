package cz.muni.fi.travelAgency.DTO;

import javax.validation.constraints.Email;

/**
 * DTO for authentication of customer.
 *
 * @author Simona Raucinova
 */
public class CustomerAuthenticateDTO {

    /**
     * Customers email
     */
    @Email
    private String customerEmail;

    /**
     * Customer's password.
     */
    private String password;

    public CustomerAuthenticateDTO(@Email String customerEmail, String password) {
        this.customerEmail = customerEmail;
        this.password = password;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
