package cz.muni.fi.travelAgency.facade;

import cz.muni.fi.travelAgency.DTO.CustomerAuthenticateDTO;
import cz.muni.fi.travelAgency.DTO.CustomerDTO;
import cz.muni.fi.travelAgency.entities.Customer;
import cz.muni.fi.travelAgency.service.BeanMappingService;
import cz.muni.fi.travelAgency.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Implemetation of {@link CustomerFacade}.
 *
 * @author Simona Raucinova
 */
@Service
@Transactional
public class CustomerFacadeImpl implements CustomerFacade {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public void registerCustomer(CustomerDTO customer, String unencryptedPassword) {
        if (customer == null || unencryptedPassword == null || customer.getEmail() == null) {
            throw new IllegalArgumentException("Either customer, password or customer's email is null");
        }
        Customer customerEntity = beanMappingService.mapTo(customer, Customer.class);
        customerService.registerCustomer(customerEntity, unencryptedPassword);
        customer.setId(customerEntity.getId());
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return beanMappingService.mapTo(customerService.getAllCustomers(), CustomerDTO.class);
    }

    @Override
    public boolean authenticate(CustomerAuthenticateDTO customer) {
        return customerService.authenticate(customer.getCustomerEmail(), customer.getPassword());
    }

    @Override
    public boolean isAdmin(CustomerDTO customer) {
        return customerService.isAdmin(beanMappingService.mapTo(customer, Customer.class));
    }

    @Override
    public CustomerDTO findCustomerById(Long customerId) {
        Customer customer = customerService.findCustomerById(customerId);
        return (customer == null) ? null : beanMappingService.mapTo(customer, CustomerDTO.class);
    }

    @Override
    public CustomerDTO findCustomerByEmail(String email) {
        Customer customer = customerService.findCustomerByEmail(email);
        return (customer == null) ? null : beanMappingService.mapTo(customer, CustomerDTO.class);
    }

    @Override
    public void updateCustomer(CustomerDTO customer) {
        if (customer == null) {
            throw new IllegalArgumentException("CustomerDTO is null.");
        }
        Customer customerEntity = beanMappingService.mapTo(customer, Customer.class);
        customerService.updateCustomer(customerEntity);
    }

    @Override
    public void deleteCustomer(CustomerDTO customer) {
        if (customer == null) {
            throw new IllegalArgumentException("CustomerDTO is null.");
        }
        if (customer.getId() == null) {
            throw new IllegalArgumentException("Customer has null id.");
        }
        Customer customerEntity = beanMappingService.mapTo(customer, Customer.class);
        customerService.deleteCustomer(customerEntity);
    }
}
