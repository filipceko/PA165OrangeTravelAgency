package cz.muni.fi.travelAgency.facade;

import cz.muni.fi.travelAgency.DTO.TripDTO;
import cz.muni.fi.travelAgency.config.ServiceConfiguration;
import cz.muni.fi.travelAgency.entities.Trip;
import cz.muni.fi.travelAgency.service.BeanMappingService;
import cz.muni.fi.travelAgency.service.TripService;
import org.mockito.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.*;

/**
 * Tests for ReservationFacade.
 *
 * @author Rithy Ly
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class TripFacadeTest extends AbstractTestNGSpringContextTests {

    /**
     * Trip for testing purposes.
     */
    private final String destination = "Lake Island";
    private TripDTO tripDTO;
    private Trip tripBrno;
    private TripDTO tripDTO;
    private LocalDate firstDate = LocalDate.of(2017, 11, 20);
    private LocalDate secondDate = LocalDate.of(2017, 11, 25);
    private final String destination = "Lake Island";

    /**
     * Mocked trip to test with
     */
    @Mock
    private TripService tripService;

    /**
     * Tested facade with mocked injections.
     */
    @InjectMocks
    private TripFacade tripFacade = new TripFacadeImpl();

    /**
     * Spy injected to the Facade
     */
    @Spy
    @Inject
    private BeanMappingService beanMappingService;

    /**
     * Sets up mockito before running tests.
     */
    @BeforeClass
    public void InitClass() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Initializes the Reservations used for testing
     */
    @BeforeMethod
    public void SetupTripDTO() {
        tripDTO = new TripDTO();
        tripDTO.setId(1L);
        tripDTO.setFromDate(firstDate);
        tripDTO.setToDate(secondDate);
        tripDTO.setDestination(destination);
        tripDTO.setCapacity(10);
        tripDTO.setPrice(100.20);
    }

    /**
     * Create test
     */
    @Test
    public void createTripTest() {
        tripFacade.createTrip(tripDTO);
        tripBrno = beanMappingService.mapTo(tripDTO, Trip.class);
        Mockito.verify(tripService).createTrip(tripBrno);
    }

    /**
     * Update test
     */
    @Test
    public void updateTripTest() {
        tripDTO.setFromDate(firstDate);
        tripDTO.setToDate(secondDate);
        tripDTO.setDestination("New Lake Island");
        tripDTO.setCapacity(15);
        tripDTO.setPrice(150.20);
        tripFacade.updateTrip(tripDTO);
        tripBrno = beanMappingService.mapTo(tripDTO, Trip.class);
        Mockito.verify(tripService).updateTrip(tripBrno);
    }

    /**
     * Remove test
     */
    @Test
    public void removeTripTest() {
        Assert.assertNotNull(tripDTO);
        tripFacade.removeTrip(tripDTO);
        Mockito.verify(tripService).removeTrip(Mockito.any(Trip.class));
    }

    /**
     * Get by ID test
     */
    @Test
    public void findByIdTest() {
        Assert.assertNotNull(tripDTO);
        tripBrno = beanMappingService.mapTo(tripDTO, Trip.class);
        Mockito.when(tripService.findById(tripDTO.getId())).thenReturn(tripBrno);
        TripDTO tripFromFacade = tripFacade.getTripById(tripDTO.getId());
        Assert.assertEquals(tripFromFacade.getDestination(), destination);
    }

    /**
     * Get by all test
     */
    @Test
    public void findByAllTest() {
        Trip trip1 = new Trip();
        trip1.setId(11L);
        trip1.setFromDate(firstDate);
        trip1.setToDate(secondDate);
        trip1.setDestination(destination);
        trip1.setCapacity(1);
        trip1.setPrice(100.20);

        Trip trip2 = new Trip();
        trip2.setId(12L);
        trip2.setFromDate(firstDate);
        trip2.setToDate(secondDate);
        trip2.setDestination("Prague");
        trip2.setCapacity(6);
        trip2.setPrice(120.20);

        Trip trip3 = new Trip();
        trip3.setId(13L);
        trip3.setFromDate(firstDate);
        trip3.setToDate(secondDate);
        trip3.setDestination("Paris");
        trip3.setCapacity(8);
        trip3.setPrice(320.20);
        List<Trip> allTrips = Arrays.asList(trip1, trip2, trip3);

        Mockito.when(tripService.findAll()).thenReturn(allTrips);
        Collection<TripDTO> getAllTripsFacade = tripFacade.getAllTrips();
        Collection<Trip> getAllTrips = beanMappingService.mapTo(getAllTripsFacade, Trip.class);
        Assert.assertEquals(getAllTrips.size(), 3);
        Assert.assertTrue(getAllTrips.contains(trip1));
        Assert.assertTrue(getAllTrips.contains(trip2));
        Assert.assertTrue(getAllTrips.contains(trip3));
    }

    /**
     * Get by available slots test
     */
    @Test
    public void getAvailableSlotsTripTest() {
        int amount = 3;
        Trip trip2 = new Trip();
        trip2.setId(12L);
        trip2.setFromDate(firstDate);
        trip2.setToDate(secondDate);
        trip2.setDestination("Prague");
        trip2.setCapacity(6);
        trip2.setPrice(120.20);

        Trip trip3 = new Trip();
        trip3.setId(13L);
        trip3.setFromDate(firstDate);
        trip3.setToDate(secondDate);
        trip3.setDestination("Paris");
        trip3.setCapacity(1);
        trip3.setPrice(320.20);
        Collection<Trip> listTrips = Arrays.asList(trip2, trip3);
        Mockito.when(tripService.findTripBySlot(amount)).thenReturn(listTrips);

        Collection<TripDTO> availableTrips = tripFacade.getTripBySlot(amount);
        Collection<Trip> result = beanMappingService.mapTo(availableTrips, Trip.class);
        Assert.assertEquals(result.size(), 2);
    }
}
