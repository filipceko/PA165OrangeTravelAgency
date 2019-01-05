package cz.muni.fi.travelAgency.facade;


import cz.muni.fi.travelAgency.DTO.CustomerDTO;
import cz.muni.fi.travelAgency.DTO.ReservationDTO;
import cz.muni.fi.travelAgency.DTO.TripDTO;
import cz.muni.fi.travelAgency.config.ServiceConfiguration;
import cz.muni.fi.travelAgency.entities.Customer;
import cz.muni.fi.travelAgency.entities.Reservation;
import cz.muni.fi.travelAgency.entities.Trip;
import cz.muni.fi.travelAgency.service.BeanMappingService;
import cz.muni.fi.travelAgency.service.ReservationService;
import org.mockito.*;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Tests for ReservationFacade.
 *
 * @author Filip Cekovsky
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class ReservationFacadeTest extends AbstractTestNGSpringContextTests {
    /**
     * Mocked service to test with
     */
    @Mock
    private ReservationService service;

    /**
     * Spy injected to the Facade
     */
    @Spy
    @Inject
    private BeanMappingService mappingService;

    /**
     * Tested facade with mocked injections.
     */
    @InjectMocks
    private ReservationFacade facade = new ReservationFacadeImpl();

    /**
     * Reservations for testing purposes.
     */
    private Reservation reservation1;
    private ReservationDTO reservation1DTO;

    private Reservation reservation2;
    private ReservationDTO reservation2DTO;

    /**
     * Sets up mockito before running tests.
     */
    @BeforeClass
    public void setUpMockito() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Initializes the Reservations used for testing
     */
    @BeforeMethod
    public void setUp() {
        Customer customer1 = new Customer("Albert", "Alojz", "alojz@gmail.com",
                BCrypt.hashpw("password", BCrypt.gensalt()));
        CustomerDTO customer1DTO = new CustomerDTO();
        customer1DTO.setName("Albert");
        customer1DTO.setSurname("Alojz");
        customer1DTO.setEmail("alojz@gmail.com");

        LocalDate from1 = LocalDate.now().plusMonths(1);
        LocalDate to1 = LocalDate.now().plusMonths(1).plusDays(7);
        Trip trip1 = new Trip(from1, to1, "Albania", 10, 80.00);
        TripDTO trip1DTO = new TripDTO();
        trip1DTO.setFromDate(from1);
        trip1DTO.setToDate(to1);
        trip1DTO.setDestination("Albania");
        trip1DTO.setCapacity(10);
        trip1DTO.setPrice(80.00);

        LocalDate from2 = LocalDate.now().plusMonths(2).plusDays(9);
        LocalDate to2 = LocalDate.now().plusMonths(2).plusDays(19);
        Trip trip2 = new Trip(from2, to2, "Bali", 22, 1200.00);
        TripDTO trip2DTO = new TripDTO();
        trip2DTO.setFromDate(from2);
        trip2DTO.setToDate(to2);
        trip2DTO.setDestination("Bali");
        trip2DTO.setCapacity(22);
        trip2DTO.setPrice(1200.00);

        LocalDate reservation1Time = LocalDate.now().minusDays(5);
        reservation1 = new Reservation(customer1, trip1, reservation1Time);
        reservation1.setId(10L);
        reservation1DTO = new ReservationDTO(customer1DTO, trip1DTO, reservation1Time);

        LocalDate reservation2Time = LocalDate.now().minusDays(3);
        reservation2 = new Reservation(customer1, trip2, reservation2Time);
        reservation2.setId(15L);
        reservation2DTO = new ReservationDTO(customer1DTO, trip2DTO, reservation2Time);
    }

    /**
     * Create test
     */
    @Test
    public void createTest() {
        Mockito.doAnswer(invocationOnMock -> {
            Object argument = invocationOnMock.getArgument(0);
            assert (argument instanceof Reservation);
            ((Reservation) argument).setId(reservation1.getId());
            return null;
        }).when(service).create(Mockito.any(Reservation.class));
        facade.createReservation(reservation1DTO);
        Assert.assertEquals(reservation1DTO.getId(), reservation1.getId());
    }

    /**
     * Get all test
     */
    @Test
    public void getAllTest() {
        List<Reservation> list = new LinkedList<>();
        list.add(reservation1);
        list.add(reservation2);
        Mockito.when(service.findAll()).thenReturn(list);
        Collection<ReservationDTO> result = facade.getAllReservations();
        Assert.assertEquals(result.size(), 2);
        result.stream().findFirst().ifPresent(
                reservationDTO -> Assert.assertTrue(reservationDTO.equals(reservation1DTO)
                        || reservationDTO.equals(reservation2DTO)));
    }

    /**
     * Get by ID test
     */
    @Test
    public void getByIdTest() {
        Mockito.when(service.findById(10L)).thenReturn(reservation1);
        ReservationDTO result = facade.getById(10L);
        Assert.assertEquals(result, reservation1DTO);
    }

    /**
     * Get by customer test
     */
    @Test
    public void getByCustomerTest() {
        List<Reservation> list = new LinkedList<>();
        list.add(reservation1);
        list.add(reservation2);
        Mockito.when(service.findByCustomer(11L)).thenReturn(list);
        Collection<ReservationDTO> result = facade.getByCustomer(11L);
        Assert.assertEquals(result.size(), 2);
        result.stream().findFirst().ifPresent(
                reservationDTO -> Assert.assertTrue(reservationDTO.equals(reservation1DTO)
                        || reservationDTO.equals(reservation2DTO)));
    }

    /**
     * Get by trip test
     */
    @Test
    public void getByTripTest() {
        List<Reservation> list = new LinkedList<>();
        list.add(reservation1);
        Mockito.when(service.findByTrip(11L)).thenReturn(list);
        Collection<ReservationDTO> result = facade.getByTrip(11L);
        Assert.assertEquals(result.size(), 1);
        result.stream().findFirst().ifPresent(
                reservationDTO -> Assert.assertEquals(reservationDTO, reservation1DTO));
    }

    /**
     * Update test
     */
    @Test
    public void updateTest() {
        reservation2DTO.setId(11L);
        facade.update(reservation2DTO);
        Mockito.verify(service).update(Mockito.any(Reservation.class));
    }

    /**
     * Delete test
     */
    @Test
    public void deleteTest() {
        reservation2DTO.setId(11L);
        facade.delete(reservation2DTO);
        Mockito.verify(service).remove(Mockito.any(Reservation.class));
    }

    /**
     * Get by interval test
     */
    @Test
    public void getByIntervalTest() {
        List<Reservation> list = new LinkedList<>();
        list.add(reservation2);
        Mockito.when(service.findReservationsBetween(Mockito.any(LocalDate.class), Mockito.any(LocalDate.class)))
                .thenReturn(list);
        Collection<ReservationDTO> result = facade.getReservationByInterval(LocalDate.now(), LocalDate.now().plusDays(17));
        Assert.assertEquals(result.size(), 1);
    }
}
