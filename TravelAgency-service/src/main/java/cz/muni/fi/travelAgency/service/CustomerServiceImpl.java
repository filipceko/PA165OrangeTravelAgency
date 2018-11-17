package cz.muni.fi.travelAgency.service;

import cz.muni.fi.travelAgency.dao.CustomerDao;
import cz.muni.fi.travelAgency.entities.Customer;
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
        return customerDao.findById(customer.getId()).isAdmin();
    }

    @Override
    public Customer findCustomerById(Long customerId) {
        if (customerId == null){
            throw new IllegalArgumentException("Cannot find customer with null id.");
        }
        return customerDao.findById(customerId);
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        if (email == null){
            throw new IllegalArgumentException("Cannot find customer with null email.");
        }
        return customerDao.findByEmail(email);
    }

    @Override
    public void updateCustomer(Customer customer) {
        if (customer == null){
            throw new IllegalArgumentException("Cannot update null customer.");
        }
        if(customer.getEmail() == null){
            throw new IllegalArgumentException("Cannot update to customer with null email address.");
        }
        if(customer.getPasswordHash() == null){
            customer.setPasswordHash(customerDao.findById(customer.getId()).getPasswordHash());
        }
        customerDao.update(customer);
    }

    @Override
    public void deleteCustomer(Customer customer) {
        if(customer == null){
            throw new IllegalArgumentException("Cannot delete null customer.");
        }
        customerDao.remove(customer);
    }

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
