package cz.muni.fi.travelAgency.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class representing Customer entity.
 *
 * @author Filip Cekovsky
 */
@Entity
@Table(name = "CUSTOMERS")
public class Customer {
    /**
     * Unique ID set by the DB
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the customer
     */
    @NotNull
    @Column(nullable = false)
    private String name;

    /**
     * Surname of the customer
     */
    @NotNull
    @Column(nullable = false)
    private String surname;

    /**
     * Email address - compulsory
     */
    @NotNull
    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String passwordHash;

    @Column
    private boolean admin;

    /**
     * Nullable phone number
     */
    @Column
    private String phoneNumber;

    /**
     * Nullable passport number
     */
    @Column
    private String passportNumber;

    /**
     * Nullable date of birth
     */
    @Column
    private LocalDate dateOfBirth;

    /**
     * Set of reservations made by the customer
     */
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Reservation> reservations = new HashSet<>();

    /**
     * Basic non-parametric constructor
     */
    public Customer() {
    }

    /**
     * All fields constructor
     */
    public Customer(@NotNull String name, @NotNull String surname, @NotNull String email,
            String phoneNumber, String passportNumber, LocalDate dateOfBirth) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.passportNumber = passportNumber;
        this.dateOfBirth = dateOfBirth;
        this.admin = false;
    }

    /**
     * Non-null fields constructor
     */
    public Customer(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    /**
     * Retrieves ID of this customer
     *
     * @return unique ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID, should be used primary by the DB
     *
     * @param id of the entity
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves customer's name
     *
     * @return name of the customer
     */
    public String getName() {
        return name;
    }

    /**
     * Name setter
     *
     * @param name of the customer
     */
    public void setName(String name) {
        this.name = name;
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

    /**
     * Retrieves customer's surname
     *
     * @return surname of the customer
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Surname setter
     *
     * @param surname of the customer
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Retrieves customer's email
     *
     * @return email of the customer
     */
    public String getEmail() {
        return email;
    }

    /**
     * Email setter
     *
     * @param email non-null email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves customers phone number
     *
     * @return customer's phone number ro null if none set
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Phone number setter
     *
     * @param phoneNumber of the customer
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Retrieves customers passport number
     *
     * @return customer's passport number or null if none set
     */
    public String getPassportNumber() {
        return passportNumber;
    }

    /**
     * Passport number setter
     *
     * @param passportNumber of the customer
     */
    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    /**
     * Retrieves all reservations for this customer
     *
     * @return unmodifiable set of reservations
     */
    public Set<Reservation> getReservations() {
        return reservations;
    }

    /**
     * Sets reservations to this customer entity
     *
     * @param reservations set of {@link Reservation}s
     */
    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    /**
     * Adds reservation for this customer
     *
     * @param reservation to be added
     */
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    /**
     * Removes one of customer's reservations
     *
     * @param reservation to be removed
     */
    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
    }

    /**
     * Retrieves customers date of birth
     *
     * @return Date of birth
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Date of birth setter
     *
     * @param dateOfBirth Date of birth of this customer
     */
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        Customer customer = (Customer) o;
        return Objects.equals(getName(), customer.getName())
                && Objects.equals(getSurname(), customer.getSurname())
                && Objects.equals(getEmail(), customer.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSurname(), getEmail());
    }
}
