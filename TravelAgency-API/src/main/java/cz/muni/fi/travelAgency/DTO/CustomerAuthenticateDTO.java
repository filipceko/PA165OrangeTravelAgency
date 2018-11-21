package cz.muni.fi.travelAgency.DTO;

/**
 * @author Simona Raucinova
 */
public class CustomerAuthenticateDTO {

    private Long customerId;
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
