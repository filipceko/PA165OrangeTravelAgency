package cz.muni.fi.travelAgency.facade;

import cz.muni.fi.travelAgency.DTO.CustomerAuthenticateDTO;
import cz.muni.fi.travelAgency.DTO.CustomerDTO;

import java.util.List;

/**
 * Interface for customer facade.
 *
 * @author Simona Raucinova
 */
public interface CustomerFacade {

    /**
     * Register the given customer with the given unencrypted password.
     *
     * @param customer            to be registered
     * @param unencryptedPassword unencrypted form of password
     */
    void registerCustomer(CustomerDTO customer, String unencryptedPassword);

    /**
     * Get all registered customers.
     */
    List<CustomerDTO> getAllCustomers();

    /**
     * Try to authenticate a customer. Return true only if the hashed password matches the records.
     */
    boolean authenticate(CustomerAuthenticateDTO customerAuthenticateDTO);

    /**
     * Check if the given user is admin.
     *
     * @param customer to be checked if is admin
     */
    boolean isAdmin(CustomerDTO customer);

    /**
     * Retrieves customer with given id.
     *
     * @param customerId id of customer
     * @return customer with given id
     */
    CustomerDTO findCustomerById(Long customerId);

    /**
     * Retrieves customer with given email.
     *
     * @param email of customer
     * @return customer with given email
     */
    CustomerDTO findCustomerByEmail(String email);

    /**
     * Updates customer.
     *
     * @param customer to be updated
     */
    void updateCustomer(CustomerDTO customer);

    /**
     * Deletes customer.
     *
     * @param customer to be deleted
     */
    void deleteCustomer(CustomerDTO customer);
}
