package cz.muni.fi.travelAgency;

import cz.muni.fi.travelAgency.entities.Customer;
import org.junit.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Test class for the Customer entity
 */
public class CustomerEntityTest {
    /** Persistence entity manager factory */
    private static EntityManagerFactory managerFactory;

    /** Spring framework application context */
    private static AnnotationConfigApplicationContext appContext;

    /**
     * Initializes managerFactory and application context
     */
    @BeforeClass
    public static void setUp() {
        appContext = new AnnotationConfigApplicationContext(InMemoryDB.class);
        managerFactory = Persistence.createEntityManagerFactory("default");
    }

    /** Fries resources of the tests */
    @AfterClass
    public static void tearDown() {
        managerFactory.close();
        appContext.close();
    }

    /**
     * Creates a customer and tests that it has been saved successfully
     */
    @Test
    public void creationTest() {
        //Customer save sequence
        Customer firstCustomer = new Customer("Filip", "Cekovsky", "filipceko@gmail.com",
                "+421908925045", null);
        EntityManager manager = managerFactory.createEntityManager();
        try {
            manager.getTransaction().begin();
            manager.persist(firstCustomer);
            manager.getTransaction().commit();
        } finally {
            manager.close();
        }
        //load sequence
        EntityManager em = managerFactory.createEntityManager();
        em.getTransaction().begin();
        Customer validateCustomer = em.createQuery(
                "select c from Customer c order by c.name", Customer.class)
                .getSingleResult();
        em.getTransaction().commit();
        em.close();
        //Test that saved and loaded entities are the same
        assertEquals(firstCustomer, validateCustomer);
    }
}
