package cz.muni.fi.travelAgency.service;

import cz.muni.fi.travelAgency.entities.Customer;

import java.util.List;

/**
 * An interface that defines a service access to the {@link Customer} entity.
 *
 * @author Simona Raucinova
 */

public interface CustomerService {

    /**
     * Register the given customer with the given unencrypted password.
     * @param customer to be registered
     * @param unencryptedPassword unencrypted form of password
     */
    void registerCustomer(Customer customer, String unencryptedPassword);

    /**
     * Register admin with the giver unencrypted password.
     * @param customer admin to be registered
     * @param unencryptedPassword unencrypted form of password
     */
    void registerAdmin(Customer customer, String unencryptedPassword);

    /**
     * Get all registered customers.
     */
    List<Customer> getAllCustomers();

    /**
     * Try to authenticate a customer. Return true only if the hashed password matches the records.
     * @param customer to be authenticated
     * @param password inputted password
     */
    boolean authenticate(Customer customer, String password);

    /**
     * Check if the given user is admin.
     * @param customer to be checked if is admin
     */
    boolean isAdmin(Customer customer);

    /**
     * Retrieves customer with given id.
     * @param customerId id of customer
     * @return customer with given id
     */
    Customer findCustomerById(Long customerId);

    /**
     * Retrieves customer with given email.
     * @param email of customer
     * @return customer with given email
     */
    Customer findCustomerByEmail(String email);

    void updateCustomer(Customer customer);

    void deleteCustomer(Customer customer);
}
