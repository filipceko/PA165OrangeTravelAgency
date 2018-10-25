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
     * @param customer to be stored
     */
    void create(Customer customer);

    /**
     * Retrieves all customers.
     * @return collection of all customer
     */
    Collection<Customer> findAll();

    /**
     * Retrieves customer with given ID.
     * @param id of the customer
     * @return customer with given ID
     */
    Customer findById(Long id);

    /**
     * Retrieves customer with given name.
     * @param name    of the customer
     * @param surname of the custome
     * @return customer with given name
     */
    Customer findByName(String name, String surname);

    /**
     * Updates given customer's data in the DB
     * @param customer to be updated
     */
    void update(Customer customer);

    /**
     * Erases data for given customer form the DB.
     * @param customer to be erased
     */
    void remove(Customer customer);
}
