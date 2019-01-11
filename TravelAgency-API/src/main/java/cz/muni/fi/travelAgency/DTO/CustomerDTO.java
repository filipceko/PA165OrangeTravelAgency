package cz.muni.fi.travelAgency.DTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * DTO for Customer.
 *
 * @author Simona Raucinova
 */
public class CustomerDTO {

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

    /**
     * Hash of the customer's password
     */
    @NotNull
    private String passwordHash;

    /**
     * True if user is admin, false otherwise
     */
    private boolean admin;

    /**
     * Reservations made by this customer
     */
    private Set<ReservationDTO> reservationDTOS = new HashSet<>();

    public CustomerDTO() {
    }

    public CustomerDTO(@NotNull String name, @NotNull String surname, @Email String email, @NotNull String passwordHash,
                       String phoneNumber, @NotNull String passportNumber, @NotNull LocalDate dateOfBirth) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.passwordHash = passwordHash;
        this.phoneNumber = phoneNumber;
        this.passportNumber = passportNumber;
        this.dateOfBirth = dateOfBirth;
        this.admin = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
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

    public Set<ReservationDTO> getReservationDTOS() {
        return reservationDTOS;
    }

    public void setReservationDTOS(Set<ReservationDTO> reservationDTOS) {
        this.reservationDTOS = reservationDTOS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerDTO)) {
            return false;
        }

        CustomerDTO that = (CustomerDTO) o;

        if (!getName().equals(that.getName())) {
            return false;
        }
        if (!getSurname().equals(that.getSurname())) {
            return false;
        }
        return getEmail().equals(that.getEmail());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getSurname().hashCode();
        result = 31 * result + getEmail().hashCode();
        return result;
    }
}
