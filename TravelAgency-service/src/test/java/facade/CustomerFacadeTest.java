package facade;

import cz.muni.fi.travelAgency.DTO.CustomerDTO;
import cz.muni.fi.travelAgency.DTO.ReservationDTO;
import cz.muni.fi.travelAgency.config.ServiceConfiguration;
import cz.muni.fi.travelAgency.entities.Customer;
import cz.muni.fi.travelAgency.facade.CustomerFacade;
import cz.muni.fi.travelAgency.facade.CustomerFacadeImpl;
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

    @Spy
    @Inject
    private BeanMappingService beanMappingService;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerFacade customerFacade = new CustomerFacadeImpl();

    private Customer customer;

    @BeforeClass
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateUser(){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(id);
        customerDTO.setEmail(email);
        customerDTO.setName(name);
        customerDTO.setSurname(surname);
        customerDTO.setAdmin(admin);
        customerDTO.setPasswordHash(passwordHash);
        customerDTO.setDateOfBirth(dateOfBirth);
        customerDTO.setPassportNumber(passportNumber);
        customer = beanMappingService.mapTo(customerDTO,Customer.class);
        customerFacade.registerCustomer(customerDTO,passwordHash);
        Mockito.verify(customerService).registerCustomer(customer,passwordHash);
    }

    @Test
    public void testUpdateUser(){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(id);
        customerDTO.setEmail(email);
        customerDTO.setName("Karol");
        customerDTO.setSurname("Kovac");
        customerDTO.setAdmin(admin);
        customerDTO.setPasswordHash(passwordHash);
        customerDTO.setDateOfBirth(dateOfBirth);
        customerDTO.setPassportNumber(passportNumber);
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




}
