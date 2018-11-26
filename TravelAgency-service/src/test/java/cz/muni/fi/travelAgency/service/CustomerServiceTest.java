package cz.muni.fi.travelAgency.service;

import cz.muni.fi.travelAgency.config.ServiceConfiguration;
import cz.muni.fi.travelAgency.dao.CustomerDao;
import cz.muni.fi.travelAgency.entities.Customer;
import cz.muni.fi.travelAgency.exceptions.DataAccessLayerException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class CustomerServiceTest extends AbstractTestNGSpringContextTests {

    private final String name = "Jan";
    private final String surname = "Novak";
    private final String email = "novak@pa165.com";
    private final LocalDate dateOfBirth = LocalDate.of(2000,1,1);
    private final String passportNumber = "123AB";
    private final boolean admin = false;
    private final String phoneNumber = "012345678";

    private final Long id = 1L;
    private Customer customer;

    @Mock
    private CustomerDao customerDao;

    @InjectMocks
    private final CustomerService customerService = new CustomerServiceImpl();

    @BeforeClass
    public void initMockito(){
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void setUpCustomer(){
        customer = new Customer();
        customer.setId(id);
        customer.setName(name);
        customer.setSurname(surname);
        customer.setEmail(email);
        customer.setDateOfBirth(dateOfBirth);
        customer.setPassportNumber(passportNumber);
        customer.setAdmin(admin);
        customer.setPhoneNumber(phoneNumber);
    }

    @Test
    public void testRegister(){
        customerService.registerCustomer(customer,"Password");
        Mockito.verify(customerDao).create(customer);
    }

    @Test
    public void testUpdate(){
        when(customerDao.findById(id)).thenReturn(customer);
        Customer retrievedCustomer = customerService.findCustomerById(id);
        retrievedCustomer.setName("Karol");
        customerService.updateCustomer(retrievedCustomer);
        Mockito.verify(customerDao).update(customer);
        retrievedCustomer = customerService.findCustomerById(id);

        Assert.assertEquals(id,retrievedCustomer.getId());
        Assert.assertEquals("Karol",retrievedCustomer.getName());
        Assert.assertEquals(email,retrievedCustomer.getEmail());
        Assert.assertEquals(dateOfBirth,retrievedCustomer.getDateOfBirth());
        Assert.assertEquals(passportNumber,retrievedCustomer.getPassportNumber());
        Assert.assertEquals(admin,retrievedCustomer.isAdmin());
        Assert.assertEquals(phoneNumber,retrievedCustomer.getPhoneNumber());
    }

    @Test
    public  void testFindById(){
        when(customerDao.findById(id)).thenReturn(customer);
        Assert.assertEquals(customer,customerService.findCustomerById(id));
    }

    @Test
    public void testFindByEmail(){
        when(customerDao.findByEmail(email)).thenReturn(customer);
        Assert.assertEquals(customer,customerService.findCustomerByEmail(email));
    }

    @Test
    public void testGetAllUsers(){
        Customer anotherCustomer = new Customer();
        anotherCustomer.setId(2L);
        anotherCustomer.setName("Milan");
        anotherCustomer.setSurname("Svoboda");
        anotherCustomer.setEmail("svoboda@pa165.com");
        anotherCustomer.setAdmin(false);
        anotherCustomer.setDateOfBirth(LocalDate.of(1999,1,1));
        anotherCustomer.setPassportNumber("456CD");

        Customer anotherCustomer2 = new Customer();
        anotherCustomer.setId(3L);
        anotherCustomer.setName("Matej");
        anotherCustomer.setSurname("Kovac");
        anotherCustomer.setEmail("kovac@pa165.com");
        anotherCustomer.setAdmin(false);
        anotherCustomer.setDateOfBirth(LocalDate.of(1998,1,1));
        anotherCustomer.setPassportNumber("789EF");

        List<Customer> allCustomers = Arrays.asList(customer,anotherCustomer,anotherCustomer2);
        when(customerDao.findAll()).thenReturn(allCustomers);
        List<Customer> retrievedFromService = new ArrayList<>(customerService.getAllCustomers());
        Assert.assertEquals(3,retrievedFromService.size());
        for (int i = 0; i < 3; i++){
            Assert.assertNotNull(retrievedFromService.get(i));
            Assert.assertEquals(retrievedFromService.get(i),allCustomers.get(i));
        }

    }

    @Test
    public void testAuthenticate(){
        customer.setPasswordHash(BCrypt.hashpw("pswd",BCrypt.gensalt()));
        Assert.assertFalse(customerService.authenticate(customer,"123"));
        Assert.assertTrue(customerService.authenticate(customer,"pswd"));
    }

    @Test
    public void testDelete(){
        customerService.deleteCustomer(customer);
        Mockito.verify(customerDao).remove(customer);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testRegisterNull(){
        customerService.registerCustomer(null,"0000");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testRegisterWithNullPassword(){
        customerService.registerCustomer(customer,null);
    }

    @Test(expectedExceptions = DataAccessLayerException.class)
    public void testUpdateNull(){
        Mockito.doThrow(IllegalArgumentException.class).when(customerDao).update(null);
        customerService.updateCustomer(null);
    }

    @Test(expectedExceptions = DataAccessLayerException.class)
    public void testDeleteNull(){
        Mockito.doThrow(IllegalArgumentException.class).when(customerDao).remove(null);
        customerService.deleteCustomer(null);
    }

}
