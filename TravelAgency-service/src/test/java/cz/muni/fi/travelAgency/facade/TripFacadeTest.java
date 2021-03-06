package cz.muni.fi.travelAgency.facade;

import cz.muni.fi.travelAgency.DTO.CheapTravelDTO;
import cz.muni.fi.travelAgency.DTO.CustomerDTO;
import cz.muni.fi.travelAgency.DTO.ReservationDTO;
import cz.muni.fi.travelAgency.DTO.TripDTO;
import cz.muni.fi.travelAgency.config.ServiceConfiguration;
import cz.muni.fi.travelAgency.entities.Customer;
import cz.muni.fi.travelAgency.entities.Excursion;
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

    private LocalDate firstDate = LocalDate.of(2018, 11, 27);
    private LocalDate secondDate = LocalDate.of(2018, 11, 29);

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

    /**
     * Tests CheapTravelDTO is returned and valid
     */
    @Test
    public void tripsForMoneyTest() {
        Map<Trip, Collection<Excursion>> map = new HashMap<>();
        map.put(tripBrno, new HashSet<>());
        Mockito.when(tripService.tripsForMoney(Mockito.anyDouble())).thenReturn(map);
        CheapTravelDTO result = tripFacade.tripsForMoney(50.00);
        TripDTO mappedTrip = beanMappingService.mapTo(tripBrno, TripDTO.class);
        Assert.assertTrue(result.getTrips().contains(mappedTrip));
        Assert.assertTrue(result.getExcursions().isEmpty());
    }

    @Test
    public void getAvailableFutureTripTest() {
        Trip trip4 = new Trip();
        trip4.setId(11L);
        trip4.setFromDate(firstDate);
        trip4.setToDate(secondDate);
        trip4.setDestination(destination);
        trip4.setCapacity(5);
        trip4.setPrice(100.20);

        Trip trip5 = new Trip();
        trip5.setId(12L);
        trip5.setFromDate(firstDate);
        trip5.setToDate(secondDate);
        trip5.setDestination("Prague");
        trip5.setCapacity(4);
        trip5.setPrice(120.20);

        Collection<Trip> allTrips = Arrays.asList(trip4, trip5);
        Mockito.when(tripService.findAvailableFutureTrip()).thenReturn(allTrips);
        Collection<TripDTO> availableFutureTrips = tripFacade.getAvailableFutureTrip();
        Collection<Trip> getResult = beanMappingService.mapTo(availableFutureTrips, Trip.class);
        Assert.assertEquals(2, getResult.size());
    }

    /**
     * @author Simona Raucinova
     */
    @Test
    public void getAllCustomersTest() {
        Set<ReservationDTO> reservationDTOS = createReservations(createCustomers(), tripDTO);
        tripDTO.setReservations(reservationDTOS);
        Trip mappedTrip = beanMappingService.mapTo(tripDTO, Trip.class);
        Collection<Customer> customersToBeReturned = beanMappingService.mapTo(createCustomers(), Customer.class);
        Mockito.when(tripService.getAllCustomers(mappedTrip)).thenReturn(customersToBeReturned);
        Collection<CustomerDTO> customerDTOS = tripFacade.getAllCustomers(tripDTO);
        Collection<Customer> customersFromFacade = beanMappingService.mapTo(customerDTOS, Customer.class);
        Assert.assertEquals(customersToBeReturned, customersFromFacade);
        Assert.assertEquals(customersFromFacade.size(), 3);

    }

    /**
     * @author Simona Raucinova
     */
    private Set<CustomerDTO> createCustomers() {
        Set<CustomerDTO> customerDTOSet = new HashSet<>();

        CustomerDTO customer1 = new CustomerDTO();
        customer1.setId(1L);
        customer1.setName("Karol");
        customer1.setSurname("Kovac");
        customer1.setEmail("karol@pa165.com");
        customerDTOSet.add(customer1);

        CustomerDTO customer2 = new CustomerDTO();
        customer2.setId(2L);
        customer2.setName("Matej");
        customer2.setSurname("Svoboda");
        customer2.setEmail("matej@pa165.com");
        customerDTOSet.add(customer2);

        CustomerDTO customer3 = new CustomerDTO();
        customer3.setId(3L);
        customer3.setName("Jan");
        customer3.setSurname("Novak");
        customer3.setEmail("novak@pa165.com");
        customerDTOSet.add(customer3);

        return customerDTOSet;
    }

    /**
     * @author Simona Raucinova
     */
    private Set<ReservationDTO> createReservations(Set<CustomerDTO> customerDTOS, TripDTO tripDTO) {
        Set<ReservationDTO> reservationDTOS = new HashSet<>();
        long counter = 0L;

        for (CustomerDTO customerDTO : customerDTOS) {
            counter++;
            ReservationDTO reservationDTO = new ReservationDTO();
            reservationDTO.setExcursions(new HashSet<>());
            reservationDTO.setTrip(tripDTO);
            reservationDTO.setCustomer(customerDTO);
            reservationDTO.setId(counter);
            reservationDTO.setReserveDate(LocalDate.of(2018, 11, 25));
            reservationDTOS.add(reservationDTO);
        }

        return reservationDTOS;
    }
}
