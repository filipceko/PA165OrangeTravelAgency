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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class TripFacadeTest extends AbstractTestNGSpringContextTests{

    private TripDTO tripDTO;
    private Trip tripBrno;
    private LocalDate firstDate = LocalDate.of(2017, 11, 20);
    private LocalDate secondDate = LocalDate.of(2017, 11, 25);
    private final String destination = "Lake Island";

    @Mock
    private TripService tripService;

    @InjectMocks
    private TripFacade tripFacade = new TripFacadeImpl();

    @Spy
    @Inject
    private BeanMappingService beanMappingService;

    @BeforeClass
    public void InitClass() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void SetupTripDTO(){
        tripDTO = new TripDTO();
        tripDTO.setId(1L);
        tripDTO.setFromDate(firstDate);
        tripDTO.setToDate(secondDate);
        tripDTO.setDestination(destination);
        tripDTO.setCapacity(10);
        tripDTO.setPrice(100.20);
    }

    @Test
    public void createTripTest(){
        tripFacade.createTrip(tripDTO);
        tripBrno = beanMappingService.mapTo(tripDTO, Trip.class);
        Assert.assertEquals(tripBrno.getDestination(),destination);
        Assert.assertEquals(tripBrno.getCapacity(),10);
        Mockito.verify(tripService).createTrip(tripBrno);
    }

    @Test
    public void updateTripTest() {
        tripDTO.setFromDate(firstDate);
        tripDTO.setToDate(secondDate);
        tripDTO.setDestination("New Lake Island");
        tripDTO.setCapacity(15);
        tripDTO.setPrice(150.20);
        tripFacade.updateTrip(tripDTO);
        tripBrno = beanMappingService.mapTo(tripDTO, Trip.class);
        Assert.assertEquals(tripBrno.getDestination(),"New Lake Island");
        Assert.assertEquals(tripBrno.getCapacity(),15);
        Mockito.verify(tripService).updateTrip(tripBrno);
    }

    @Test
    public void removeTripTest() {
        Assert.assertNotNull(tripDTO);
        tripFacade.removeTrip(tripDTO);
        tripBrno = beanMappingService.mapTo(tripDTO, Trip.class);
        Mockito.verify(tripService).removeTrip(tripBrno);
    }

    @Test
    public void testFindById(){
        tripBrno = beanMappingService.mapTo(tripDTO, Trip.class);
        Mockito.when(tripService.findById(tripDTO.getId())).thenReturn(tripBrno);
        TripDTO tripFromFacade = tripFacade.getTripById(tripDTO.getId());
        Assert.assertEquals(tripFromFacade.getDestination(),destination);
    }

    @Test
    public void getAvailableSlotsTripTest() {
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
        List<Trip> allTrips = Arrays.asList(trip1,trip2,trip3);

        Mockito.when(tripService.findTripBySlot(2)).thenReturn(allTrips);
        Collection<TripDTO> fromfacade = new ArrayList<>(tripFacade.getTripBySlot(2));
        Collection<Trip> entitiesFromfacade = beanMappingService.mapTo(fromfacade,Trip.class);
        Mockito.verify(tripService).findTripBySlot(3);

    }
}