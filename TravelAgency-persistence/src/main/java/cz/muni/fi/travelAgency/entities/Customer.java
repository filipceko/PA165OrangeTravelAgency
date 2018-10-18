package cz.muni.fi.travelAgency.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Class representing Customer entity.
 *
 * @author Filip Cekovsky
 */
@Entity
@Table(name = "CUSTOMERS")
public class Customer {
    /** Unique ID set by the DB */
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    /** Name of the customer */
    @NotNull
    @Column(nullable = false)
    private String name;

    /** Surname of the customer */
    @NotNull
    @Column(nullable = false)
    private String surname;

    /** Email address - compulsory*/
    @NotNull
    @Column(nullable = false)
    private String email;

    /** Nullable phone number */
    @Column
    private String phoneNumber;

    /** Passport number */
    @Column
    private String passportNumber;

    /**
     * Basic non-parametric constructor
     */
    public Customer(){}

    /** All fields constructor */
    public Customer(String name, String surname, String email, String phoneNumber, String passportNumber) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.passportNumber = passportNumber;
    }

    /**
     * @return unique ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID, should be used primary by the DB
     * @param id of the entity
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return name of the customer
     */
    public String getName() {
        return name;
    }

    /**
     * Name setter
     * @param name of the customer
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return surname of the customer
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Surname setter
     * @param surname of the customer
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @return email of the customer
     */
    public String getEmail() {
        return email;
    }

    /**
     * Email setter
     * @param email non-null email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return customer's phone number ro null if none set
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Phone number setter
     * @param phoneNumber of the customer
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return customer's passport number or null if none set
     */
    public String getPassportNumber() {
        return passportNumber;
    }

    /**
     * Passport number setter
     * @param passportNumber of the customer
     */
    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(getName(), customer.getName()) &&
                Objects.equals(getSurname(), customer.getSurname()) &&
                Objects.equals(getEmail(), customer.getEmail()) &&
                Objects.equals(getPhoneNumber(), customer.getPhoneNumber()) &&
                Objects.equals(getPassportNumber(), customer.getPassportNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSurname(), getEmail(), getPhoneNumber(), getPassportNumber());
    }
}
