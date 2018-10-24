package cz.muni.fi.travelAgency.dao;

import static org.junit.Assert.*;

import cz.muni.fi.travelAgency.PersistenceTestAppContext;
import cz.muni.fi.travelAgency.entities.Customer;
import cz.muni.fi.travelAgency.entities.Reservation;
import cz.muni.fi.travelAgency.entities.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@ContextConfiguration(classes = PersistenceTestAppContext.class)
public class ReservationDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private ReservationDao reservationDao;

    private Date yesterday;

    @BeforeClass
    public void setup() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        yesterday = cal.getTime();

        EntityManager manager = entityManagerFactory.createEntityManager();
        manager.getTransaction().begin();

        Customer customer1 = new Customer();
        customer1.setName("Filip");
        customer1.setSurname("Cekovsky");
        customer1.setEmail("filip@ceko.com");

        Trip trip1 = new Trip();

        Reservation reservation1 = new Reservation();
        reservation1.setReserveDate(yesterday);
        reservation1.setTrip(trip1);
        reservation1.setCustomer(customer1);

        manager.persist(trip1);
        manager.persist(customer1);

        manager.getTransaction().commit();
        manager.close();
        reservationDao.create(reservation1);
    }

    /**
     * Test saved product
     */
    @Test
    public void findProducts() {
        List<Reservation> found = reservationDao.findAll();
        Assert.assertEquals(found.size(), 1);
        Reservation example = reservationDao.findById(1L);
        assertNotNull(example.getReserveDate());
        assert (1L == example.getTrip().getId());
        assert (1L == example.getCustomer().getId());
    }


}
