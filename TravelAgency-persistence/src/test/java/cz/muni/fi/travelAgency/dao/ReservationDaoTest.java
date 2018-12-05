package cz.muni.fi.travelAgency.dao;

import cz.muni.fi.travelAgency.PersistenceTestAppContext;
import cz.muni.fi.travelAgency.entities.Customer;
import cz.muni.fi.travelAgency.entities.Excursion;
import cz.muni.fi.travelAgency.entities.Reservation;
import cz.muni.fi.travelAgency.entities.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.PersistenceException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Reservation DAO test class
 *
 * @author Filip Cekovsky
 */
@ContextConfiguration(classes = PersistenceTestAppContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class ReservationDaoTest extends AbstractTestNGSpringContextTests {


    @Autowired
    private ReservationDao reservationDao;

    @Autowired
    private TripDao tripDao;

    @Autowired
    private ExcursionDao excursionDao;

    @Autowired
    private CustomerDao customerDao;

    private LocalDate firstDate = LocalDate.of(1995, 7, 28);
    private LocalDate secondDate = LocalDate.of(2018, 11, 24);

    private Trip trip;
    private Customer customer1;
    private Customer customer2;
    private Customer customer3;
    private Reservation reservation1;
    private Reservation reservation2;
    private Excursion excursion2;

    /**
     * Prepare testing environment
     */
    @BeforeMethod
    public void setUp() {
        trip = new Trip(firstDate, secondDate, "Test", 10, 100.20);
        customer1 = new Customer("Filip", "Cekovsky", "filip@ceko.com");
        customer2 = new Customer("Frodo", "Zemiak", "frodo@zemiak.com");
        customer3 = new Customer("Imrich", "Piskotka", "piskota@sucha.com");
        Excursion excursion1 = new Excursion("Test excursion", "Test", 10.00,
                firstDate, Duration.ZERO, trip);
        excursion2 = new Excursion("Test excursion 2.0", "Tale", 11.50,
                secondDate, Duration.ofHours(5), trip);
        reservation1 = new Reservation(customer1, trip, firstDate);
        reservation2 = new Reservation(customer2, trip, secondDate);
        tripDao.create(trip);
        customerDao.create(customer1);
        customerDao.create(customer2);
        customerDao.create(customer3);
        excursionDao.create(excursion1);
        excursionDao.create(excursion2);

        reservation1.addExcursion(excursion1);
        reservation1.addExcursion(excursion2);
        reservationDao.create(reservation1);
        reservationDao.create(reservation2);
    }

    /**
     * Validates remove throws expected exceptions, remove functionality.
     */
    @Test
    public void removeTest() {
        reservationDao.remove(reservation1);
        reservationDao.remove(reservation2);
        //Test remove was successful
        assertNull(reservationDao.findById(reservation1.getId()));
        assertNull(reservationDao.findById(reservation2.getId()));
        assertThrows(IllegalArgumentException.class, () -> reservationDao.remove(new Reservation()));
        assertThrows(IllegalArgumentException.class, () -> reservationDao.remove(null));
    }

    /**
     * Validates excursions are saved as expected and valid exceptions are
     * thrown on error.
     */
    @Test
    public void createTest() {
        Reservation stored = reservationDao.findById(reservation1.getId());
        assertEquals(reservation1, stored);
        assertEquals(firstDate, reservation1.getReserveDate());
        Collection<Excursion> retrievedExcursions = stored.getExcursions();
        assertEquals(2, retrievedExcursions.size());
        assertEquals(stored.getId(), reservation1.getId());
        assertThrows(PersistenceException.class, () -> reservationDao.create(new Reservation()));
        assertThrows(IllegalArgumentException.class, () -> reservationDao.create(null));
    }

    /**
     * Tests findAll method
     */
    @Test
    public void findAllTest() {
        Collection<Reservation> found = reservationDao.findAll();
        assertEquals(2, found.size());
    }

    /**
     * Validates findById, asserts results and exceptions
     */
    @Test
    public void findByIdTest() {
        assertNotNull(reservationDao.findById(reservation1.getId()));
        assertNull(reservationDao.findById(1823L));
        assertNull(reservationDao.findById(-1823L));
        assertNull(reservationDao.findById(0L));
        assertThrows(IllegalArgumentException.class, () -> reservationDao.findById(null));
    }

    /**
     * Validates findByCustomerId, asserts results
     */
    @Test
    public void findByCustomerIdTest() {
        Collection<Reservation> found = reservationDao.findByCustomerId(customer1.getId());
        assertNotNull(found);
        assertEquals(1, found.size());
        found = reservationDao.findByCustomerId(customer2.getId());
        assertEquals(1, found.size());
        assertEquals(0, reservationDao.findByCustomerId(customer3.getId()).size());
        assertEquals(0, reservationDao.findByCustomerId(null).size());
    }

    /**
     * Validates findByTripId, asserts results
     */
    @Test
    public void findByTripIdTest() {
        Collection<Reservation> found = reservationDao.findByTripId(trip.getId());
        assertNotNull(found);
        assertEquals(2, found.size());
        assertEquals(0, reservationDao.findByTripId(null).size());
    }

    /**
     * Validates update(), asserts results and exceptions
     */
    @Test
    public void updateTest() {
        reservation1.setReserveDate(secondDate);
        reservationDao.update(reservation1);
        assertEquals(secondDate, reservationDao.findById(reservation1.getId()).getReserveDate());
        reservation1.setReserveDate(firstDate);
        reservationDao.update(reservation1);

        assertEquals(0, reservationDao.findById(reservation2.getId()).getExcursions().size());
        reservation2.addExcursion(excursion2);
        reservationDao.update(reservation2);
        assertEquals(1, reservationDao.findById(reservation2.getId()).getExcursions().size());

        Reservation reservation3 = new Reservation(customer3, trip, firstDate);
        assertThrows(IllegalArgumentException.class, () -> reservationDao.update(reservation3));
        assertThrows(IllegalArgumentException.class, () -> reservationDao.update(null));
    }

    /**
     * findBetween() functionality validation. Null should represent +- infinity
     */
    @Test
    public void findBetweenTest() {
        LocalDate before = LocalDate.of(1888, 1, 1);
        LocalDate after = LocalDate.of(2020, 1, 1);
        Collection<Reservation> result = reservationDao.findReservationBetween(firstDate, secondDate);
        assertEquals(2, result.size());
        result = reservationDao.findReservationBetween(firstDate, firstDate);
        assertEquals(1, result.size());
        result = reservationDao.findReservationBetween(before, after);
        assertEquals(2, result.size());
        result = reservationDao.findReservationBetween(before, before);
        assertEquals(0, result.size());
        result = reservationDao.findReservationBetween(before, firstDate);
        assertEquals(1, result.size());
        result = reservationDao.findReservationBetween(before, null);
        assertEquals(2, result.size());
        result = reservationDao.findReservationBetween(null, after);
        assertEquals(2, result.size());
        result = reservationDao.findReservationBetween(null, null);
        assertEquals(2, result.size());
    }
}
