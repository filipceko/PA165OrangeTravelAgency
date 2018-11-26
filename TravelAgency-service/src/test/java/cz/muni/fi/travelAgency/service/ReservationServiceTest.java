package cz.muni.fi.travelAgency.service;

import cz.muni.fi.travelAgency.config.ServiceConfiguration;
import cz.muni.fi.travelAgency.dao.ReservationDao;
import cz.muni.fi.travelAgency.entities.Customer;
import cz.muni.fi.travelAgency.entities.Reservation;
import cz.muni.fi.travelAgency.entities.Trip;
import cz.muni.fi.travelAgency.exceptions.DataAccessException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Simple reservation service tests using Mockito.
 *
 * @author Filip Cekovsky
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class ReservationServiceTest extends AbstractTestNGSpringContextTests {
    /**
     * Service tested with mocked injections.
     */
    @InjectMocks
    private final ReservationService reservationService = new ReservationServiceImpl();

    /**
     * Mocked reservation data access object for testing.
     */
    @Mock
    private ReservationDao reservationDao;

    /**
     * Instances of reservation used for testing.
     */
    private Reservation reservation1;
    private Reservation reservation2;
    private Reservation reservation3;
    private Reservation reservation4;

    /**
     * Set's up the Mockito injections.
     */
    @BeforeClass
    public void initMockito() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Setts up the Reservation with required fields.
     */
    @BeforeMethod
    public void initTest() {
        Mockito.reset(reservationDao);
        Customer customer1 = new Customer("Janko", "Hrasko", "janko@hrasko.com");
        Customer customer2 = new Customer("Jozko", "Mrkvicka", "jozef@mrkva.com");
        Trip trip1 = new Trip(LocalDate.now(), LocalDate.now().plusDays(5L), "Paris", 5, 80.0);
        Trip trip2 = new Trip(LocalDate.now(), LocalDate.now().plusDays(5L), "Hong Kong", 17, 1200.0);
        reservation1 = new Reservation(customer1, trip1, LocalDate.now().plusDays(3));
        reservation2 = new Reservation(customer1, trip2, LocalDate.now().plusDays(2));
        reservation3 = new Reservation(customer2, trip1, LocalDate.now().plusDays(1));
        reservation4 = new Reservation(customer2, trip2, LocalDate.now());
    }

    /**
     * Tests create calls create on the DAO.
     */
    @Test
    public void createTest() {
        reservationService.create(reservation1);
        Mockito.verify(reservationDao).create(reservation1);
    }

    /**
     * Tests service validates the argument.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    void createNullTest() {
        reservationService.create(null);
    }

    /**
     * Tests that exceptions thrown by the DAO are thrown as DataAccessException
     */
    @Test(expectedExceptions = DataAccessException.class)
    void createThrowsTest() {
        Mockito.doThrow(IllegalArgumentException.class).when(reservationDao).create(reservation2);
        reservationService.create(reservation2);
    }

    /**
     * Tests that exceptions thrown by the DAO are thrown as DataAccessException
     */
    @Test(expectedExceptions = DataAccessException.class)
    void createThrows2Test() {
        Mockito.doThrow(IllegalArgumentException.class).when(reservationDao).create(reservation2);
        reservationService.create(reservation2);
    }

    /**
     * Tests valid result is returned.
     */
    @Test
    public void findAllTest() {
        Set<Reservation> expected = new HashSet<>();
        expected.add(reservation1);
        expected.add(reservation2);
        expected.add(reservation3);
        expected.add(reservation4);
        Mockito.when(reservationDao.findAll()).thenReturn(expected);
        Collection<Reservation> result = reservationService.findAll();
        Assert.assertEquals(result.size(), 4);
        Assert.assertTrue(result.contains(reservation1));
        Assert.assertTrue(result.contains(reservation2));
    }

    /**
     * Tests that exceptions thrown by the DAO are thrown as DataAccessException
     */
    @Test(expectedExceptions = DataAccessException.class)
    void findAllThrowsTest() {
        Mockito.when(reservationDao.findAll()).thenThrow(IllegalArgumentException.class);
        reservationService.findAll();
    }

    /**
     * Tests valid result is returned.
     */
    @Test
    public void findByIdTest() {
        Long id = 1L;
        reservation1.setId(id);
        Mockito.when(reservationDao.findById(id)).thenReturn(reservation1);
        Reservation result = reservationService.findById(id);
        Assert.assertEquals(reservation1, result);
        Assert.assertEquals(id, reservation1.getId());
    }

    /**
     * Tests service validates the argument.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findByNullTest() {
        reservationService.findById(null);
    }

    /**
     * Tests that exceptions thrown by the DAO are thrown as DataAccessException
     */
    @Test(expectedExceptions = DataAccessException.class)
    void findByIdThrowsTest() {
        Mockito.when(reservationDao.findById(15L)).thenThrow(IllegalArgumentException.class);
        reservationService.findById(15L);
    }

    /**
     * Tests valid result is returned.
     */
    @Test
    public void findByCustomerTest() {
        Set<Reservation> expected = new HashSet<>();
        expected.add(reservation1);
        expected.add(reservation2);
        Mockito.when(reservationDao.findByCustomerId(Mockito.anyLong())).thenReturn(expected);
        Collection<Reservation> result = reservationService.findByCustomer(10L);
        Assert.assertEquals(result.size(), 2);
        Assert.assertTrue(result.contains(reservation1));
        Assert.assertTrue(result.contains(reservation2));
    }

    /**
     * Tests service validates the argument.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findByNullCustomerTest() {
        reservationService.findByCustomer(null);
    }

    /**
     * Tests that exceptions thrown by the DAO are thrown as DataAccessException
     */
    @Test(expectedExceptions = DataAccessException.class)
    void findByCustomerThrowsTest() {
        Mockito.doThrow(IllegalArgumentException.class).when(reservationDao).findByCustomerId(Mockito.anyLong());
        reservationService.findByCustomer(15L);
    }

    /**
     * Tests valid result is returned.
     */
    @Test
    public void findByTripTest() {
        Set<Reservation> expected = new HashSet<>();
        expected.add(reservation2);
        expected.add(reservation4);
        Mockito.when(reservationDao.findByTripId(Mockito.anyLong())).thenReturn(expected);
        Collection<Reservation> result = reservationService.findByTrip(25L);
        Assert.assertEquals(result.size(), 2);
        Assert.assertTrue(result.contains(reservation2));
        Assert.assertTrue(result.contains(reservation4));
    }

    /**
     * Tests service validates the argument.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findByNullTripTest() {
        reservationService.findByTrip(null);
    }

    /**
     * Tests that exceptions thrown by the DAO are thrown as DataAccessException
     */
    @Test(expectedExceptions = DataAccessException.class)
    void findByTripThrowsTest() {
        Mockito.when(reservationDao.findByTripId(Mockito.anyLong())).thenThrow(IllegalArgumentException.class);
        reservationService.findByTrip(27L);
    }

    /**
     * Tests update on DAO is called.
     */
    @Test
    public void updateTest() {
        reservation3.setId(13L);
        reservation3.setReserveDate(LocalDate.now().plusDays(2));
        reservationService.update(reservation3);
        Mockito.verify(reservationDao, Mockito.times(1)).update(reservation3);
    }

    /**
     * Tests service validates the argument.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateNullTest() {
        reservationService.update(null);
    }

    /**
     * Tests service validates the argument.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateNoIdTest() {
        try {
            reservationService.update(reservation4);
        } finally {
            Mockito.verify(reservationDao, Mockito.never()).update(Mockito.any());
        }
    }

    /**
     * Tests that exceptions thrown by the DAO are thrown as DataAccessException
     */
    @Test(expectedExceptions = DataAccessException.class)
    void updateThrowsTest() {
        reservation1.setId(85L);
        Mockito.doThrow(IllegalArgumentException.class).when(reservationDao).update(Mockito.any(Reservation.class));
        reservationService.update(reservation1);
    }

    /**
     * Tests remove on DAO is called.
     */
    @Test
    public void removeTest() {
        reservation3.setId(14L);
        reservationService.remove(reservation3);
        Mockito.verify(reservationDao, Mockito.times(1)).remove(reservation3);
    }

    /**
     * Tests service validates the argument.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void removeNullTest() {
        reservationService.remove(null);
    }

    /**
     * Tests service validates the argument.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void removeNoIdTest() {
        try {
            reservationService.update(reservation2);
        } finally {
            Mockito.verify(reservationDao, Mockito.never()).update(Mockito.any());
        }
    }

    /**
     * Tests that exceptions thrown by the DAO are thrown as DataAccessException
     */
    @Test(expectedExceptions = DataAccessException.class)
    void removeThrowsTest() {
        reservation1.setId(24L);
        Mockito.doThrow(IllegalArgumentException.class).when(reservationDao).remove(reservation1);
        reservationService.remove(reservation1);
    }

    /**
     * Tests valid result is returned.
     */
    @Test
    public void findReservationBetweenTest() {
        Collection<Reservation> expected = new HashSet<>();
        expected.add(reservation1);
        expected.add(reservation2);
        expected.add(reservation3);
        expected.add(reservation4);
        Mockito.when(reservationDao.findReservationBetween(Mockito.any(LocalDate.class), Mockito.any(LocalDate.class)))
                .thenReturn(expected);
        Collection<Reservation> result = reservationService.findReservationsBetween(LocalDate.now().minusDays(4),
                LocalDate.now().plusDays(15));
        Assert.assertEquals(result.size(), 4);
        Assert.assertTrue(result.contains(reservation1));
        Assert.assertTrue(result.contains(reservation2));
        Assert.assertTrue(result.contains(reservation3));
        Assert.assertTrue(result.contains(reservation4));
    }

    /**
     * Tests valid result is returned.
     */
    @Test
    public void findReservationBetweenNullsTest() {
        // null, null
        Collection<Reservation> expected1 = new HashSet<>();
        expected1.add(reservation1);
        expected1.add(reservation2);
        expected1.add(reservation3);
        expected1.add(reservation4);
        Mockito.when(reservationDao.findReservationBetween(null, null)).thenReturn(expected1);
        Collection<Reservation> result1 = reservationService.findReservationsBetween(null, null);
        Assert.assertEquals(result1.size(), 4);
        Assert.assertTrue(result1.contains(reservation1));
        Assert.assertTrue(result1.contains(reservation2));
        Assert.assertTrue(result1.contains(reservation3));
        Assert.assertTrue(result1.contains(reservation4));
        // from, null
        Collection<Reservation> expected2 = new HashSet<>();
        expected2.add(reservation3);
        expected2.add(reservation4);
        LocalDate from = LocalDate.now().minusDays(2);
        Mockito.when(reservationDao.findReservationBetween(from, null)).thenReturn(expected2);
        Collection<Reservation> result2 = reservationService.findReservationsBetween(from, null);
        Assert.assertEquals(result2.size(), 2);
        Assert.assertTrue(result2.contains(reservation3));
        Assert.assertTrue(result2.contains(reservation4));
        // null, to
        Collection<Reservation> expected3 = new HashSet<>();
        expected3.add(reservation1);
        expected3.add(reservation2);
        LocalDate to = LocalDate.now().minusDays(3);
        Mockito.when(reservationDao.findReservationBetween(null, to)).thenReturn(expected3);
        Collection<Reservation> result3 = reservationService.findReservationsBetween(null, to);
        Assert.assertEquals(result3.size(), 2);
        Assert.assertTrue(result3.contains(reservation1));
        Assert.assertTrue(result3.contains(reservation2));
    }

    /**
     * Tests that exceptions thrown by the DAO are thrown as DataAccessException
     */
    @Test(expectedExceptions = DataAccessException.class)
    void findBetweenThrowsTest() {
        Mockito.when(reservationDao.findReservationBetween(Mockito.any(), Mockito.any())).thenThrow(IllegalArgumentException.class);
        reservationService.findReservationsBetween(null, LocalDate.now());
    }

}
