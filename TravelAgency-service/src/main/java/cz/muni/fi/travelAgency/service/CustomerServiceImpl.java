package cz.muni.fi.travelAgency.service;

import cz.muni.fi.travelAgency.dao.CustomerDao;
import cz.muni.fi.travelAgency.entities.Customer;
import cz.muni.fi.travelAgency.exceptions.DataAccessLayerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link CustomerService}. This class is part of the service module of the application that
 * provides the implementation of the business logic (main logic of the application).
 *
 * @author Simona Raucinova
 */

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerDao customerDao;

    @Override
    public void registerCustomer(Customer customer, String unencryptedPassword) {
        if(validateCustomerRegistration(customer,unencryptedPassword)){
            customer.setPasswordHash(BCrypt.hashpw(unencryptedPassword, BCrypt.gensalt()));
            customerDao.create(customer);
        }
    }

    @Override
    public void registerAdmin(Customer customer, String unencryptedPassword) {
        if(validateCustomerRegistration(customer,unencryptedPassword)){
            customer.setAdmin(true);
            customer.setPasswordHash(BCrypt.hashpw(unencryptedPassword,BCrypt.gensalt()));
            customerDao.create(customer);
        }
    }

    @Override
    public boolean authenticate(Customer customer, String password) {
        if (customer == null){
            throw new IllegalArgumentException("Cannot authenticate null customer.");
        }
        if(customer.getEmail() == null){
            throw new IllegalArgumentException("Cannot authenticate customer without email address.");
        }
        if(password == null){
            throw new IllegalArgumentException("Cannot authenticate customer without password.");
        }

        return BCrypt.checkpw(password, customer.getPasswordHash());
    }

    @Override
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customerDao.findAll());
    }


    @Override
    public boolean isAdmin(Customer customer) {
        try {
            return customerDao.findById(customer.getId()).isAdmin();
        } catch (Exception exp){
            throw new DataAccessLayerException(exp.getMessage(),exp.getCause());
        }

    }

    @Override
    public Customer findCustomerById(Long customerId) {
        try {
            return customerDao.findById(customerId);
        } catch (Exception exp){
            throw new DataAccessLayerException(exp.getMessage(),exp.getCause());
        }
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        try {
            return customerDao.findByEmail(email);
        } catch (Exception exp){
            throw new DataAccessLayerException(exp.getMessage(),exp.getCause());
        }

    }

    @Override
    public void updateCustomer(Customer customer) {
        try {
            customerDao.update(customer);
        } catch (Exception exp){
            throw new DataAccessLayerException(exp.getMessage(),exp.getCause());
        }
    }

    @Override
    public void deleteCustomer(Customer customer) {
        try {
            customerDao.remove(customer);
        } catch (Exception exp){
            throw new DataAccessLayerException(exp.getMessage(),exp.getCause());
        }
    }

    /**
     * Helping method for validation data of registration.
     *
     * @param customer to be registered
     * @param unencryptedPassword unencrypted form of customers password
     * @return if registration can be completed
     */
    private boolean validateCustomerRegistration(Customer customer, String unencryptedPassword){
        if(customer == null){
            throw new IllegalArgumentException("Cannot register inserted null customer.");
        }
        if (customer.getEmail() == null){
            throw new IllegalArgumentException("Cannot register customer with null email address.");
        }
        if (unencryptedPassword == null){
            throw new IllegalArgumentException("Cannot register customer with null password.");
        }
        return true;
    }


}
