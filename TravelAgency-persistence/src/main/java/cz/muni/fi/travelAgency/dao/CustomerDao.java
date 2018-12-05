package cz.muni.fi.travelAgency.dao;

import cz.muni.fi.travelAgency.entities.Customer;

import java.util.Collection;

/**
 * DAO class for {@link Customer} entity.
 *
 * @author Filip Cekovsky
 */
public interface CustomerDao {

    /**
     * Stores customer into the DB.
     *
     * @param customer to be stored
     * @throws IllegalArgumentException                      if input is NULL
     * @throws javax.validation.ConstraintViolationException if parameter violates constraints
     */
    void create(Customer customer);

    /**
     * Retrieves all customers.
     *
     * @return collection of all customer
     */
    Collection<Customer> findAll();

    /**
     * Retrieves customer with given ID.
     *
     * @param id of the customer
     * @return customer with given ID or NULL if none found
     * @throws IllegalArgumentException if id is NULL
     */
    Customer findById(Long id);

    /**
     * Retrieves customer with given name.
     *
     * @param name    of the customer
     * @param surname of the custome
     * @return customer with given name or null if none found
     * @throws IllegalArgumentException if one of name parts is NULL
     */
    Customer findByName(String name, String surname);

    /**
     * Retrieves customer with given email.
     *
     * @param email of the customer
     * @return customer with given email or null if none found
     * @throws IllegalArgumentException if email is NULL
     */
    Customer findByEmail(String email);

    /**
     * Updates given customer's data in the DB
     *
     * @param customer to be updated
     * @throws IllegalArgumentException                                   if parameter is NULL or not saved yet.
     * @throws org.springframework.transaction.TransactionSystemException if constraints are violated
     */
    void update(Customer customer);

    /**
     * Erases data for given customer form the DB.
     *
     * @param customer to be erased
     * @throws IllegalArgumentException                      when NULL as parameter
     * @throws javax.validation.ConstraintViolationException when parameter is not valid
     */
    void remove(Customer customer);
}
