package cz.muni.fi.travelAgency.service;

        import cz.muni.fi.travelAgency.config.ServiceConfiguration;
        import cz.muni.fi.travelAgency.dao.TripDao;
        import cz.muni.fi.travelAgency.entities.Trip;
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

@ContextConfiguration(classes = ServiceConfiguration.class)
public class TripServiceTest extends AbstractTestNGSpringContextTests {

    private Trip trip;
    private LocalDate firstDate = LocalDate.of(2017, 11, 20);
    private LocalDate secondDate = LocalDate.of(2017, 11, 25);
    private final String destination = "Lake Island";

    @Mock
    private TripDao tripDao;

    @InjectMocks
    private final TripService tripService = new TripServiceImpl();

    @BeforeClass
    public void initMockito(){
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void setUpTest(){
        trip = new Trip();
        trip.setId(1L);
        trip.setFromDate(firstDate);
        trip.setToDate(secondDate);
        trip.setDestination(destination);
        trip.setCapacity(10);
        trip.setPrice(100.20);
        tripService.createTrip(trip);
        Assert.assertEquals(trip.getDestination(),"Lake Island");
        Assert.assertEquals(trip.getCapacity(),10);
    }

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

        List<Trip> allTrips = Arrays.asList(trip1,trip2,trip3);
        Mockito.when(tripDao.findAll()).thenReturn(allTrips);
        List<Trip> getTrips = new ArrayList<>(tripService.findTripBySlot(5));
        Assert.assertEquals(1,getTrips.size());
    }

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

        List<Trip> allTrips = Arrays.asList(trip1,trip2,trip3);
        Mockito.when(tripDao.findAll()).thenReturn(allTrips);
        List<Trip> getTrips = new ArrayList<>(tripService.findAll());
        Assert.assertEquals(3,getTrips.size());
        Assert.assertEquals(getTrips.size(), 3);
        Assert.assertEquals(getTrips.get(0),trip1);
        Assert.assertEquals(getTrips.get(1),trip2);
        Assert.assertEquals(getTrips.get(2),trip3);
    }

    @Test
    public void updateTripTest() {
        Assert.assertEquals(trip.getDestination(),"Lake Island");

        trip.setDestination("New Lake Island");
        trip.setCapacity(15);
        trip.setPrice(150.20);
        tripService.updateTrip(trip);
        Mockito.verify(tripDao, Mockito.times(1)).update(trip);
    }

    @Test
    public void removeTripTest(){
        tripService.removeTrip(trip);
        Mockito.verify(tripDao, Mockito.times(1)).remove(trip);
    }

    @Test
    public  void findByIdTest(){
        Mockito.when(tripDao.findById(trip.getId())).thenReturn(trip);
        Trip foundTrip = tripService.findById(trip.getId());
        Assert.assertEquals(foundTrip,trip);
        Assert.assertEquals(foundTrip.getDestination(),trip.getDestination());
    }
}
