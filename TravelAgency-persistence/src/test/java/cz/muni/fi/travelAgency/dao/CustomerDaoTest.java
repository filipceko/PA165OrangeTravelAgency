package cz.muni.fi.travelAgency.dao;

import cz.muni.fi.travelAgency.PersistenceTestAppContext;
import cz.muni.fi.travelAgency.entities.Customer;
import cz.muni.fi.travelAgency.entities.Excursion;
import cz.muni.fi.travelAgency.entities.Reservation;
import cz.muni.fi.travelAgency.entities.Trip;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;


/**
 * Implementation of {@link AbstractTestNGSpringContextTests}
 * @author Rithy 
 */
@ContextConfiguration(classes = PersistenceTestAppContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class CustomerDaoTest extends AbstractTestNGSpringContextTests{

    @Autowired
    private CustomerDao customerDao;
    
    private Customer customer;
    private Trip tripParis1;
    private Excursion excursionParis1;
    private Reservation reservationParis1;

     /**
     * Init and create customer entity 
     */
    @BeforeClass
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

    /**
     * Tests customer entity creation and retrieval
     */
    @Test
    public void testFindCustomerById() {
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
     * Tests get all customers entity from database
     */
    @Test
    public void testFindAllCustomers() {
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
    public void testUpdateCustomer() {
        //Update customer
        Customer found = customerDao.findById(customer.getId());
        Assert.assertNotNull(found);

        found.setName("Rajiv");
        customerDao.update(found);

        Customer updatedCustomer = customerDao.findById(found.getId());
        Assert.assertEquals(updatedCustomer.getName(), "Rajiv");
    }

    /**
     * Test customer entity deletion
     */
    @Test
    public void testDeleteCustomer() {
        Assert.assertNotNull(customerDao.findById(customer.getId()));
        customerDao.remove(customer);
        Assert.assertNull(customerDao.findById(customer.getId()));
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
