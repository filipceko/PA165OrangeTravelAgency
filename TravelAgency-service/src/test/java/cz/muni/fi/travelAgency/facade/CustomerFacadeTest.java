package cz.muni.fi.travelAgency.facade;

import cz.muni.fi.travelAgency.DTO.CustomerDTO;
import cz.muni.fi.travelAgency.config.ServiceConfiguration;
import cz.muni.fi.travelAgency.entities.Customer;
import cz.muni.fi.travelAgency.service.BeanMappingService;
import cz.muni.fi.travelAgency.service.CustomerService;
import org.mockito.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.when;

/**
 * Test for CustomerFacade class.
 * @author Simona Raucinova
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class CustomerFacadeTest extends AbstractTestNGSpringContextTests {

    private final Long id = 1L;
    private final String email = "test@travelAgency.com";
    private final String name = "Jan";
    private final String surname = "Novak";
    private final String passwordHash = "passwordHash";
    private final boolean admin = false;
    private final String passportNumber = "123AB";
    private final LocalDate dateOfBirth = LocalDate.of(1980,01,01);

    private CustomerDTO customerDTO;

    @Spy
    @Inject
    private BeanMappingService beanMappingService;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerFacade customerFacade = new CustomerFacadeImpl();

    private Customer customer = new Customer();

    @BeforeClass
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(id);
        customerDTO.setEmail(email);
        customerDTO.setName(name);
        customerDTO.setSurname(surname);
        customerDTO.setAdmin(admin);
        customerDTO.setPasswordHash(passwordHash);
        customerDTO.setDateOfBirth(dateOfBirth);
        customerDTO.setPassportNumber(passportNumber);

        this.customerDTO = customerDTO;
    }

    @Test
    public void testCreateUser(){
        customer = beanMappingService.mapTo(customerDTO,Customer.class);
        customerFacade.registerCustomer(customerDTO,passwordHash);
        Mockito.verify(customerService).registerCustomer(customer,passwordHash);
    }

    @Test
    public void testUpdateUser(){
        customerDTO.setName("Karol");
        customerDTO.setSurname("Kovac");
        customerFacade.updateCustomer(customerDTO);
        customer = beanMappingService.mapTo(customerDTO,Customer.class);
        Mockito.verify(customerService).updateCustomer(customer);
        Assert.assertEquals(customer.getName(),"Karol");
        Assert.assertEquals(customer.getSurname(),"Kovac");
        Assert.assertEquals(customer.getEmail(),email);
        Assert.assertEquals(customer.isAdmin(),admin);
        Assert.assertEquals(customer.getPasswordHash(),passwordHash);
        Assert.assertEquals(customer.getDateOfBirth(),dateOfBirth);
        Assert.assertEquals(customer.getPassportNumber(),passportNumber);
    }

    @Test
    public void testFindById(){
        Customer customer = beanMappingService.mapTo(customerDTO,Customer.class);
        when(customerService.findCustomerById(customerDTO.getId())).thenReturn(customer);
        CustomerDTO customerFromFacade = customerFacade.findCustomerById(customerDTO.getId());

        Assert.assertEquals(customerFromFacade.getName(),name);
        Assert.assertEquals(customerFromFacade.getSurname(),surname);
        Assert.assertEquals(customerFromFacade.getEmail(),email);
        Assert.assertEquals(customerFromFacade.isAdmin(),admin);
        Assert.assertEquals(customerFromFacade.getPasswordHash(),passwordHash);
        Assert.assertEquals(customerFromFacade.getDateOfBirth(),dateOfBirth);
        Assert.assertEquals(customerFromFacade.getPassportNumber(),passportNumber);
    }

    @Test
    public void testFindByEmail(){
        Customer customer = beanMappingService.mapTo(customerDTO,Customer.class);
        when(customerService.findCustomerByEmail(customerDTO.getEmail())).thenReturn(customer);
        CustomerDTO customerFromFacade = customerFacade.findCustomerByEmail(customerDTO.getEmail());

        Assert.assertEquals(customerFromFacade.getName(),name);
        Assert.assertEquals(customerFromFacade.getSurname(),surname);
        Assert.assertEquals(customerFromFacade.getEmail(),email);
        Assert.assertEquals(customerFromFacade.isAdmin(),admin);
        Assert.assertEquals(customerFromFacade.getPasswordHash(),passwordHash);
        Assert.assertEquals(customerFromFacade.getDateOfBirth(),dateOfBirth);
        Assert.assertEquals(customerFromFacade.getPassportNumber(),passportNumber);
    }

    @Test
    public void testFindAll(){
        Customer customer1 = new Customer();

        customer1.setId(2L);
        customer1.setName("Karol");
        customer1.setSurname("Kovac");
        customer1.setEmail("karol@pa165.com");

        Customer customer2 = new Customer();
        customer2.setId(3L);
        customer2.setName("Matej");
        customer2.setSurname("Svoboda");
        customer2.setEmail("matej@pa165.com");

        customer.setId(id);
        customer.setName(name);
        customer.setSurname(surname);
        customer.setEmail(email);
        List<Customer> allCustomers = Arrays.asList(customer1,customer2,customer);
        when(customerService.getAllCustomers()).thenReturn(allCustomers);
        List<CustomerDTO> fromfacade = new ArrayList<>(customerFacade.getAllCustomers());
        List<Customer> entitiesFromfacade = beanMappingService.mapTo(fromfacade,Customer.class);
        Assert.assertEquals(3,entitiesFromfacade.size());
        Assert.assertEquals(entitiesFromfacade.get(0),customer1);
        Assert.assertEquals(entitiesFromfacade.get(1),customer2);
        Assert.assertEquals(entitiesFromfacade.get(2),customer);
    }

    @Test
    public void testDeleteUser(){
        customerFacade.deleteCustomer(customerDTO);
        customer = beanMappingService.mapTo(customerDTO,Customer.class);
        customerService.deleteCustomer(customer);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testRegisterNullUser(){
        customerFacade.registerCustomer(null,"password");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testRegisterUserWithNullPassword(){
        customerFacade.registerCustomer(new CustomerDTO(),null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testRegisterNullEmail(){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(5L);
        customerDTO.setName("Name");
        customerDTO.setSurname("Surname");
        customerFacade.registerCustomer(customerDTO,"0000");
    }

    @Test
    public void testFindWithReservationsById(){
    }

    @Test
    public void testFindWithReservationsByEmail(){
    }

}
