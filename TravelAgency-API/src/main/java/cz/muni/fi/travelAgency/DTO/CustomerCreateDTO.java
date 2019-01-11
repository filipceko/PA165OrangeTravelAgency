package cz.muni.fi.travelAgency.DTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * DTO for creation of customer.
 *
 * @author Simona Raucinova
 */
public class CustomerCreateDTO {

    /**
     * Customer's id
     */
    private Long id;

    /**
     * Customer's name
     */
    @NotNull
    @Size(min = 3, max = 20)
    private String name;

    /**
     * Customer's surname
     */
    @NotNull
    @Size(min = 3, max = 20)
    private String surname;

    /**
     * Customer's email
     */
    @NotNull
    @Email
    private String email;

    /**
     * Plain text password
     */
    @NotNull
    @Size(min = 8, max = 30)
    private String password;

    /**
     * Phone number
     */
    private String phoneNumber;

    /**
     * Passport number
     */
    private String passportNumber;

    /**
     * Customer's date of birth
     */
    private LocalDate dateOfBirth;

    public CustomerCreateDTO() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerCreateDTO)) {
            return false;
        }
        CustomerCreateDTO that = (CustomerCreateDTO) o;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) {
            return false;
        }
        if (getSurname() != null ? !getSurname().equals(that.getSurname()) : that.getSurname() != null) {
            return false;
        }
        return getEmail() != null ? getEmail().equals(that.getEmail()) : that.getEmail() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getSurname() != null ? getSurname().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        return result;
    }
}
