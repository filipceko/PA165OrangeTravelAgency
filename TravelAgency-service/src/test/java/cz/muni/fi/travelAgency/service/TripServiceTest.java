package cz.muni.fi.travelAgency.service;

import cz.muni.fi.travelAgency.config.ServiceConfiguration;
import cz.muni.fi.travelAgency.dao.TripDao;
import cz.muni.fi.travelAgency.entities.Customer;
import cz.muni.fi.travelAgency.entities.Excursion;
import cz.muni.fi.travelAgency.entities.Reservation;
import cz.muni.fi.travelAgency.entities.Trip;
import cz.muni.fi.travelAgency.exceptions.DataAccessLayerException;
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

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.when;

/**
 * Simple Trip service tests using Mockito.
 *
 * @author Rithy Ly
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class TripServiceTest extends AbstractTestNGSpringContextTests {

    /**
     * Service tested with mocked injections.
     */
    @InjectMocks
    private final TripService tripService = new TripServiceImpl();
    private final String destination = "Lake Island";
    private Trip trip;
    private LocalDate firstDate = LocalDate.now().plusDays(5);
    private LocalDate secondDate = LocalDate.now().plusDays(7);
    /**
     * Mocked trip data access object for testing.
     */
    @Mock
    private TripDao tripDao;

    /**
     * Set's up the Mockito injections.
     */
    @BeforeClass
    public void initMockito() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Setts up the Trip with required fields.
     */
    @BeforeMethod
    public void setUpTest() {
        Mockito.reset(tripDao);
        trip = new Trip();
        trip.setId(1L);
        trip.setFromDate(firstDate);
        trip.setToDate(secondDate);
        trip.setDestination(destination);
        trip.setCapacity(10);
        trip.setPrice(100.20);
    }

    /**
     * Tests create calls create on the DAO.
     */
    @Test
    public void createTripTest() {
        tripService.createTrip(trip);
        Mockito.verify(tripDao).create(trip);
    }

    /**
     * Tests update on DAO is called.
     */
    @Test
    public void updateTripTest() {
        trip.setDestination("New Lake Island");
        trip.setCapacity(15);
        trip.setPrice(150.20);
        tripService.updateTrip(trip);
        Mockito.verify(tripDao, Mockito.times(1)).update(trip);
    }

    /**
     * Tests remove on DAO is called.
     */
    @Test
    public void removeTripTest() {
        tripService.removeTrip(trip);
        Mockito.verify(tripDao, Mockito.times(1)).remove(trip);
    }

    /**
     * Tests valid result is returned.
     */
    @Test
    public void getAvailableSlotsTripTest() {
        Trip trip1 = new Trip();
        trip1.setId(11L);
        trip1.setFromDate(firstDate);
        trip1.setToDate(secondDate);
        trip1.setDestination(destination);
        trip1.setCapacity(5);
        trip1.setPrice(100.20);

        Trip trip2 = new Trip();
        trip2.setId(12L);
        trip2.setFromDate(firstDate);
        trip2.setToDate(secondDate);
        trip2.setDestination("Prague");
        trip2.setCapacity(4);
        trip2.setPrice(120.20);

        Trip trip3 = new Trip();
        trip3.setId(13L);
        trip3.setFromDate(firstDate);
        trip3.setToDate(secondDate);
        trip3.setDestination("Paris");
        trip3.setCapacity(3);
        trip3.setPrice(320.20);

        List<Trip> allTrips = Arrays.asList(trip1, trip2, trip3);
        Mockito.when(tripDao.findAll()).thenReturn(allTrips);
        List<Trip> getTrips = new ArrayList<>(tripService.findTripBySlot(5));
        Assert.assertEquals(1, getTrips.size());
    }

    /**
     * Tests valid result is returned.
     */
    @Test
    public void findAvailableFutureTrip() {
        Trip trip1 = new Trip();
        trip1.setId(11L);
        trip1.setFromDate(firstDate);
        trip1.setToDate(secondDate);
        trip1.setDestination(destination);
        trip1.setCapacity(5);
        trip1.setPrice(100.20);

        Trip trip2 = new Trip();
        trip2.setId(12L);
        trip2.setFromDate(firstDate);
        trip2.setToDate(secondDate);
        trip2.setDestination("Prague");
        trip2.setCapacity(4);
        trip2.setPrice(120.20);

        List<Trip> allTrips = Arrays.asList(trip1, trip2);
        Mockito.when(tripDao.findAll()).thenReturn(allTrips);
        List<Trip> getTrips = new ArrayList<>(tripService.findAvailableFutureTrip());
        Assert.assertEquals(getTrips.size(), 2);
    }

    /**
     * Tests valid result is returned.
     */
    @Test
    public void getAllTripTest() {
        Trip trip1 = new Trip();
        trip1.setId(11L);
        trip1.setFromDate(firstDate);
        trip1.setToDate(secondDate);
        trip1.setDestination(destination);
        trip1.setCapacity(5);
        trip1.setPrice(100.20);

        Trip trip2 = new Trip();
        trip2.setId(12L);
        trip2.setFromDate(firstDate);
        trip2.setToDate(secondDate);
        trip2.setDestination("Prague");
        trip2.setCapacity(4);
        trip2.setPrice(120.20);

        Trip trip3 = new Trip();
        trip3.setId(13L);
        trip3.setFromDate(firstDate);
        trip3.setToDate(secondDate);
        trip3.setDestination("Paris");
        trip3.setCapacity(3);
        trip3.setPrice(320.20);

        List<Trip> allTrips = Arrays.asList(trip1, trip2, trip3);
        Mockito.when(tripDao.findAll()).thenReturn(allTrips);

        List<Trip> getTrips = new ArrayList<>(tripService.findAll());
        Assert.assertEquals(3, getTrips.size());
        Assert.assertEquals(getTrips.size(), 3);
        Assert.assertEquals(getTrips.get(0), trip1);
        Assert.assertEquals(getTrips.get(1), trip2);
        Assert.assertEquals(getTrips.get(2), trip3);
    }

    /**
     * Tests valid result is returned.
     */
    @Test
    public void findByIdTest() {
        Mockito.when(tripDao.findById(trip.getId())).thenReturn(trip);
        Trip foundTrip = tripService.findById(trip.getId());
        Assert.assertEquals(foundTrip, trip);
        Assert.assertEquals(foundTrip.getDestination(), trip.getDestination());
    }

    /**
     * Tests service validates the argument.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findByIdNullTest() {
        tripService.findById(null);
    }

    /**
     * Tests that exceptions thrown by the DAO are thrown as DataAccessException
     */
    @Test(expectedExceptions = DataAccessLayerException.class)
    public void findByIdThrowsTest() {
        Mockito.when(tripDao.findById(1L)).thenThrow(IllegalArgumentException.class);
        tripService.findById(1L);
    }

    /**
     * Tests service validates the argument.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findByDestinationNullTest() {
        tripService.findByDestination(null);
    }

    /**
     * Tests that exceptions thrown by the DAO are thrown as DataAccessException
     */
    @Test(expectedExceptions = DataAccessLayerException.class)
    public void findByDestinationThrowsTest() {
        Mockito.when(tripDao.findByDestination("search")).thenThrow(IllegalArgumentException.class);
        tripService.findByDestination("search");
    }

    /**
     * Tests that exceptions thrown by the DAO are thrown as DataAccessException
     */
    @Test(expectedExceptions = DataAccessLayerException.class)
    public void findByIntervalThrowsTest() {
        Mockito.when(tripDao.findByInterval(firstDate, secondDate)).thenThrow(IllegalArgumentException.class);
        tripService.findByInterval(firstDate, secondDate);
    }

    /**
     * Tests service validates the argument.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findTripBySlotErrorTest() {
        tripService.findTripBySlot(-1);
    }

    /**
     * Tests service validates the argument.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void createTripNullTest() {
        tripService.createTrip(null);
    }

    /**
     * Tests service validates the argument.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void removeNullTest() {
        tripService.removeTrip(null);
    }

    /**
     * Tests service validates the argument.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void removeNoIdTest() {
        try {
            trip.setId(null);
            tripService.removeTrip(trip);
        } finally {
            Mockito.verify(tripDao, Mockito.never()).update(Mockito.any());
        }
    }

    /**
     * Tests service validates the argument.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateNoIdTest() {
        try {
            trip.setId(null);
            tripService.updateTrip(trip);
        } finally {
            Mockito.verify(tripDao, Mockito.never()).update(Mockito.any());
        }
    }

    /**
     * Tests that exceptions thrown by the DAO are thrown as DataAccessException
     */
    @Test(expectedExceptions = DataAccessLayerException.class)
    void updateThrowsTest() {
        trip.setId(100L);
        Mockito.doThrow(IllegalArgumentException.class).when(tripDao).update(Mockito.any(Trip.class));
        tripService.updateTrip(trip);
    }

    /**
     * @author Simona Raucinova
     */
    @Test
    public void getAllCustomers() {
        Set<Reservation> reservations = createReservations(createCustomers(), trip);
        for (Reservation reservation : reservations) {
            trip.addReservation(reservation);
        }
        when(tripDao.findById(1L)).thenReturn(trip);
        Collection<Customer> returnedCustomers = tripService.getAllCustomers(trip);
        Set<Customer> returnedCustomerSet = new HashSet<>(returnedCustomers);
        Assert.assertEquals(createCustomers(), returnedCustomerSet);
        Assert.assertEquals(returnedCustomers.size(), 3);
    }

    /**
     * @author Simona Raucinova
     */
    private Set<Reservation> createReservations(Set<Customer> customers, Trip trip) {
        Set<Reservation> reservations = new HashSet<>();
        long counter = 0L;
        for (Customer customer : customers) {
            counter++;
            Reservation reservation = new Reservation();
            reservation.setExcursions(new HashSet<>());
            reservation.setTrip(trip);
            reservation.setCustomer(customer);
            reservation.setId(counter);
            reservation.setReserveDate(LocalDate.of(2018, 11, 25));
            reservations.add(reservation);
        }

        return reservations;
    }

    /**
     * @author Simona Raucinova
     */
    private Set<Customer> createCustomers() {
        Set<Customer> customers = new HashSet<>();

        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setName("Karol");
        customer1.setSurname("Kovac");
        customer1.setEmail("karol@pa165.com");
        customers.add(customer1);

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setName("Matej");
        customer2.setSurname("Svoboda");
        customer2.setEmail("matej@pa165.com");
        customers.add(customer2);

        Customer customer3 = new Customer();
        customer3.setId(3L);
        customer3.setName("Jan");
        customer3.setSurname("Novak");
        customer3.setEmail("novak@pa165.com");
        customers.add(customer3);

        return customers;
    }

    /**
     * Tests argument constraint violation
     *
     * @author Filip Cekovsky
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    void tripsForMoneyViolateTest() {
        tripService.tripsForMoney(0.0);
    }

    /**
     * Tests argument constraint violation
     *
     * @author Filip Cekovsky
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    void tripsForMoneyViolate2Test() {
        tripService.tripsForMoney(-19909.0);
    }

    /**
     * Test that DAO exception is rethrown as DataAccessException.
     *
     * @author Filip Cekovsky
     */
    @Test(expectedExceptions = DataAccessLayerException.class)
    void tripForMoneyTrowTest() {
        Mockito.when(tripDao.findAll()).thenThrow(IllegalArgumentException.class);
        tripService.tripsForMoney(80.00);
    }

    /**
     * Tests tripFormMoney when there are no excursions
     *
     * @author Filip Cekovsky
     */
    @Test
    void tripForMonetOnlyTripsTest() {
        LocalDate from = LocalDate.now();
        LocalDate to = LocalDate.now().plusDays(5);
        Trip trip1 = new Trip(from, to, "Prague", 5, 30.0);
        Trip trip2 = new Trip(from, to, "Paris", 5, 40.0);
        Trip trip3 = new Trip(from, to, "Bratislava", 5, 30.1);
        Trip trip4 = new Trip(from, to, "Brno", 5, 60.0);
        Trip trip5 = new Trip(from, to, "Oslo", 5, 50.0);
        Trip trip6 = new Trip(from, to, "Praha-Venkov", 0, 0.5);
        Collection<Trip> trips = Arrays.asList(trip1, trip2, trip3, trip4, trip5, trip6);
        Mockito.when(tripDao.findAll()).thenReturn(trips);
        Map<Trip, Collection<Excursion>> result = tripService.tripsForMoney(155.99);
        Assert.assertEquals(result.size(), 4);
        for (Map.Entry<Trip, Collection<Excursion>> entry : result.entrySet()) {
            Assert.assertTrue(entry.getValue().isEmpty());
        }
        Assert.assertNotNull(result.get(trip1));
        Assert.assertNotNull(result.get(trip2));
        Assert.assertNotNull(result.get(trip3));
        Assert.assertNotNull(result.get(trip5));
        Assert.assertNull(result.get(trip4));
    }

    /**
     * Tests tripFormMoney with excursions
     *
     * @author Filip Cekovsky
     */
    @Test
    void tripForMonetTest() {
        LocalDate from = LocalDate.now();
        LocalDate to = LocalDate.now().plusDays(5);
        Trip trip1 = new Trip(from, to, "Prague", 5, 30.0);
        Trip trip2 = new Trip(from, to, "Paris", 5, 40.0);
        Trip trip3 = new Trip(from, to, "Bratislava", 5, 30.1);
        Trip trip4 = new Trip(from, to, "Brno", 5, 60.0);
        Trip trip5 = new Trip(from, to, "Oslo", 5, 50.0);
        new Excursion("test1", "any1", 0.05, from, Duration.ZERO, trip1);
        new Excursion("test2", "any2", 2.00, from, Duration.ZERO, trip2);
        new Excursion("test3", "any3", 1.99, from, Duration.ZERO, trip1);
        new Excursion("test4", "any4", 2.00, from, Duration.ZERO, trip5);
        new Excursion("test5", "any5", 0.50, from, Duration.ZERO, trip4);
        new Excursion("test6", "any6", 11.50, from, Duration.ZERO, trip2);
        Collection<Trip> trips = Arrays.asList(trip1, trip2, trip3, trip4, trip5);
        Mockito.when(tripDao.findAll()).thenReturn(trips);
        Map<Trip, Collection<Excursion>> result = tripService.tripsForMoney(155.99);
        Assert.assertEquals(result.size(), 4);
        Assert.assertEquals(result.get(trip1).size(), 2);
        Assert.assertEquals(result.get(trip2).size(), 1);
        Assert.assertTrue(result.get(trip3).isEmpty());
        Assert.assertTrue(result.get(trip5).isEmpty());
        Assert.assertNull(result.get(trip4));
    }
}
