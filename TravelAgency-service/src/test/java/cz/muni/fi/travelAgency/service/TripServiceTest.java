package cz.muni.fi.travelAgency.service;

import cz.muni.fi.travelAgency.config.ServiceConfiguration;
import cz.muni.fi.travelAgency.dao.TripDao;
import cz.muni.fi.travelAgency.entities.Trip;
import cz.muni.fi.travelAgency.exceptions.DataAccessException;
import org.mockito.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Simple Trip service tests using Mockito.
 *
 * @author Rithy Ly
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class TripServiceTest extends AbstractTestNGSpringContextTests {

    private Trip trip;
    private LocalDate firstDate = LocalDate.of(2018, 11, 27);
    private LocalDate secondDate = LocalDate.of(2018, 11, 29);
    private final String destination = "Lake Island";

    /**
     * Mocked trip data access object for testing.
     */
    @Mock
    private TripDao tripDao;

    /**
     * Service tested with mocked injections.
     */
    @InjectMocks
    private final TripService tripService = new TripServiceImpl();

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
        Assert.assertEquals(2, getTrips.size());
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
    @Test(expectedExceptions = DataAccessException.class)
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
    @Test(expectedExceptions = DataAccessException.class)
    public void findByDestinationThrowsTest() {
        Mockito.when(tripDao.findByDestination("search")).thenThrow(IllegalArgumentException.class);
        tripService.findByDestination("search");
    }

    /**
     * Tests service validates the argument.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findByIntervalNullTest() {
        tripService.findByInterval(null, null);
    }

    /**
     * Tests that exceptions thrown by the DAO are thrown as DataAccessException
     */
    @Test(expectedExceptions = DataAccessException.class)
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
    @Test(expectedExceptions = DataAccessException.class)
    void updateThrowsTest() {
        trip.setId(100L);
        Mockito.doThrow(IllegalArgumentException.class).when(tripDao).update(Mockito.any(Trip.class));
        tripService.updateTrip(trip);
    }
}
