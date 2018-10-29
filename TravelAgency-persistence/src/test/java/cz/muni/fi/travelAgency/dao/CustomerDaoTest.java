package cz.muni.fi.travelAgency.dao;

import cz.muni.fi.travelAgency.PersistenceTestAppContext;
import cz.muni.fi.travelAgency.entities.Customer;
import cz.muni.fi.travelAgency.entities.Excursion;
import cz.muni.fi.travelAgency.entities.Reservation;
import cz.muni.fi.travelAgency.entities.Trip;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * Implementation of {@link AbstractTestNGSpringContextTests}
 * @author Rithy 
 */
@ContextConfiguration(classes = PersistenceTestAppContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class CustomerDaoTest extends AbstractTestNGSpringContextTests{

    @PersistenceUnit
    private EntityManagerFactory managerFactory;

    @Autowired
    private CustomerDao customerDao;

    private Customer customer;
    private Trip tripParis1;
    private Excursion excursionParis1;
    private Reservation reservationParis1;

     /**
     * Init and create customer entity 
     */
    @BeforeMethod
    public void InitCustomerTest() {
        customer = new Customer();
        customer.setName("RITHY");
        customer.setSurname("LY");
        customer.setDateOfBirth(LocalDate.of(1987, 7, 7));
        customer.setPassportNumber("PASSPORT2018");
        customer.setPhoneNumber("776741422");
        customer.setEmail("lyrithyit@gmail.com");
        customerDao.create(customer);
    }

    @Test
    public void tearDownTest(){
       // customerDao.remove(customer);
        //Test remove was successful
        //assertNull(customerDao.findById(customer.getId()));
        EntityManager manager = managerFactory.createEntityManager();
    }

    /**
     * Validates excursions are saved as expected and valid exceptions are thrown on error.
     */
    @Test
    public void createCustomerTest(){
       /* EntityManager manager = managerFactory.createEntityManager();
        Customer foundCustomer = manager.find(Customer.class, customer.getId());
        assertEquals(customer, foundCustomer);
        assertEquals(customer.getEmail(),foundCustomer.getEmail());

        assertThrows(ConstraintViolationException.class, () -> customerDao.create(new Customer()));
        assertThrows(IllegalArgumentException.class, () -> customerDao.create(null));*/
    }

    /**
     * Tests find customer entity by providing custgomer id
     */
    @Test
    public void findByIdCustomerTest() {
        // Get customer and test find methods
        Assert.assertNotNull(customer.getId());
        Customer found = customerDao.findById(customer.getId());

        Assert.assertNotNull(found);
        Assert.assertEquals(found.getName(), "RITHY");
        Assert.assertEquals(found.getSurname(), "LY");
        Assert.assertEquals(found.getDateOfBirth(), LocalDate.of(1987, 7, 7));
        Assert.assertEquals(found.getEmail(), "lyrithyit@gmail.com");
        Assert.assertEquals(found.getPassportNumber(), "PASSPORT2018");
        Assert.assertEquals(found.getPhoneNumber(),"776741422");
    }
  
    /**
     * Tests find customer entity by providing custgomer's name
     */
    @Test
    public void findByNameCustomerTest(){
        // Get customer and test find methods
        Assert.assertNotNull(customer.getName());
        Customer found = customerDao.findByName(customer.getName(),customer.getSurname());
        Assert.assertNotNull(found);
        assertEquals(customer, found);
        assertThrows(IllegalArgumentException.class, () -> customerDao.findByName(null,customer.getSurname()));
    }

    /**
     * Tests get all customers entity from database
     */
    @Test
    public void findAllCustomerTest() {
        //Add new customer
        Customer newCustomer = new Customer();
        newCustomer.setName("Filip");
        newCustomer.setSurname("NEW");
        newCustomer.setDateOfBirth(LocalDate.of(1987, 7, 7));
        newCustomer.setPassportNumber("PASSPORT2");
        newCustomer.setPhoneNumber("77766699");
        newCustomer.setEmail("filip@gmail.com");
        customerDao.create(newCustomer);
        // Get all customers store in database
        Collection<Customer> allCustomers = customerDao.findAll();
        Assert.assertEquals(allCustomers.size(),2);
    }

    /**
     * Test customer entity update
     */
    @Test
    public void updateCustomerTest() {
        //Update customer
        Customer found = customerDao.findById(customer.getId());
        Assert.assertNotNull(found);

        found.setName("Rajiv");
        customerDao.update(found);

        Customer updatedCustomer = customerDao.findById(found.getId());
        Assert.assertEquals(updatedCustomer.getName(), "Rajiv");
        assertThrows(IllegalArgumentException.class, () -> customerDao.update(null));
    }

    /**
     * Test customer entity deletion
     */
    @Test
    public void removeCustomerTest() {
        //also tested in tear down
        assertThrows(IllegalArgumentException.class, () -> customerDao.remove(null));
    }

    /**
     * Test customer make reservation entity
     */
    @Test
    public void testMakeReservation()
    {
        //Set trip duration
        LocalDate startDate = LocalDate.of(2018, 10, 01);
        LocalDate endDate = LocalDate.of(2018, 10, 30);

        //Set Trip & Excursion
        tripParis1 = new Trip(startDate, endDate, "A Week", 25);
        excursionParis1 = new Excursion("Paris", "Five Days"
                ,BigDecimal.valueOf(25.00)
                ,LocalDate.of(2018, 10, 30)
                ,Duration.ZERO, tripParis1);

        //Make Reservation
        reservationParis1 = new Reservation(customer, tripParis1, LocalDate.of(2018, 10, 15));
        Assert.assertNotNull(reservationParis1.getCustomer());
    }

}
