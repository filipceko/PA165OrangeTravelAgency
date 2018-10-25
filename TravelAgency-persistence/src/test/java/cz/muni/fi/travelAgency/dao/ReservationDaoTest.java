package cz.muni.fi.travelAgency.dao;

import cz.muni.fi.travelAgency.PersistenceTestAppContext;
import cz.muni.fi.travelAgency.entities.Customer;
import cz.muni.fi.travelAgency.entities.Excursion;
import cz.muni.fi.travelAgency.entities.Reservation;
import cz.muni.fi.travelAgency.entities.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ContextConfiguration(classes = PersistenceTestAppContext.class)
public class ReservationDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Autowired
    private ReservationDao reservationDao;

    private LocalDate firstDate = LocalDate.of(1995, 7, 28);
    private LocalDate secondDate = LocalDate.of(2018, 11, 24);

    private Reservation reservation1;
    private Excursion excursion1;

    @BeforeClass
    public void setUp() {
        EntityManager manager = emf.createEntityManager();
        manager.getTransaction().begin();

        Customer customer1 = new Customer("Filip", "Cekovsky", "filip@ceko.com");
        //Customer customer2 = new Customer("Janko", "Hrasko", "janko@hrasko.com");
        Trip trip1 = new Trip(firstDate, secondDate, "Test", 10);
        excursion1 = new Excursion("Test excursion", "Test",
                BigDecimal.valueOf(10.00), firstDate, Duration.ZERO, trip1);
        reservation1 = new Reservation(customer1, trip1, firstDate);

        manager.persist(trip1);
        manager.persist(customer1);
        manager.persist(excursion1);

        manager.getTransaction().commit();
        manager.close();
    }

    /**
     * Test saved product
     */
    @Test
    public void createRemoveTest(){
        EntityManager manager = emf.createEntityManager();
        reservation1.addExcursion(excursion1);
        reservationDao.create(reservation1);
        manager.getTransaction().begin();
        Reservation stored = manager.createQuery("select c from Reservation c", Reservation.class).getSingleResult();
        assertEquals(reservation1, stored);
        assertEquals(firstDate, reservation1.getReserveDate());
        Collection<Excursion> retrievedExcursions = stored.getExcursions();
        assertEquals(1, retrievedExcursions.size());
        reservationDao.remove(reservation1);
        assertNull(reservationDao.findById(reservation1.getId()));
    }


}
