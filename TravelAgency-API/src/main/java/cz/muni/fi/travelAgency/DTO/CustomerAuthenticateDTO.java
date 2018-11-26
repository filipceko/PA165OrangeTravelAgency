package cz.muni.fi.travelAgency.DTO;

/**
 * DTO for authentication of customer.
 * @author Simona Raucinova
 */
public class CustomerAuthenticateDTO {

    /**
     * Id of customer.
     */
    private Long customerId;
    /**
     * Customer's password.
     */
    private String password;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
