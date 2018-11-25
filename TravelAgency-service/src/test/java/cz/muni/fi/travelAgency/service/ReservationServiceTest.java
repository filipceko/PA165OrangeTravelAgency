package cz.muni.fi.travelAgency.service;

import cz.muni.fi.travelAgency.config.ServiceConfiguration;
import cz.muni.fi.travelAgency.dao.ReservationDao;
import cz.muni.fi.travelAgency.entities.Customer;
import cz.muni.fi.travelAgency.entities.Reservation;
import cz.muni.fi.travelAgency.entities.Trip;
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
     * Instance of reservation used for testing.
     */
    private Reservation reservation;

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
        Customer customer = new Customer("Janko", "Hrasko", "janko@hrasko.com");
        Trip trip = new Trip(LocalDate.now(), LocalDate.now().plusDays(5L), "Paris", 5, 80.0);
        reservation = new Reservation(customer, trip, LocalDate.now());
    }

    /**
     * Tests create calls create on the DAO.
     */
    @Test
    public void createTest() {
        reservationService.create(reservation);
        Mockito.verify(reservationDao).create(reservation);
    }

    /**
     * Tests valid result is returned.
     */
    @Test
    public void findByIdTest() {
        Long id = 1L;
        reservation.setId(id);
        Mockito.when(reservationDao.findById(id)).thenReturn(reservation);
        Reservation result = reservationService.findById(id);
        Assert.assertEquals(reservation, result);
        Assert.assertEquals(id, reservation.getId());
    }

    /**
     * Tests valid result is returned.
     */
    @Test
    public void findByCustomerTest() {
        Set<Reservation> expected = new HashSet<>();
        expected.add(reservation);
        Mockito.when(reservationDao.findByCustomerId(Mockito.anyLong())).thenReturn(expected);
        Collection<Reservation> result = reservationService.findByCustomer(10L);
        Assert.assertEquals(result.size(), 1);
        Assert.assertTrue(result.contains(reservation));
    }

    /**
     * Tests valid result is returned.
     */
    @Test
    public void findByTripTest() {
        Set<Reservation> expected = new HashSet<>();
        expected.add(reservation);
        Mockito.when(reservationDao.findByTripId(Mockito.anyLong())).thenReturn(expected);
        Collection<Reservation> result = reservationService.findByCustomer(15L);
        Assert.assertEquals(result.size(), 1);
        Assert.assertTrue(result.contains(reservation));
    }

    /**
     * Tests update on DAO is called.
     */
    @Test
    public void updateTest() {
        reservation.setId(13L);
        reservation.setReserveDate(LocalDate.now().plusDays(2));
        reservationService.update(reservation);
        Mockito.verify(reservationDao, Mockito.times(1)).update(reservation);
    }

    /**
     * Tests remove on DAO is called.
     */
    @Test
    public void removeTest() {
        reservation.setId(14L);
        reservationService.remove(reservation);
        Mockito.verify(reservationDao, Mockito.times(1)).remove(reservation);
    }

    /**
     * Tests valid result is returned.
     */
    @Test
    public void findReservationBetweenTest() {
        Collection<Reservation> expected = new HashSet<>();
        expected.add(reservation);
        Mockito.when(reservationDao.findReservationBetween(Mockito.any(LocalDate.class), Mockito.any(LocalDate.class)))
                .thenReturn(expected);
        Collection<Reservation> result = reservationService.findReservationsBetween(LocalDate.now().minusDays(2),
                LocalDate.now().plusDays(15));
        Assert.assertEquals(result.size(), 1);
        Assert.assertTrue(result.contains(reservation));
    }
}
