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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.time.LocalDate;

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

    @Spy
    @Inject
    private BeanMappingService mappingService;

    /**
     * Tested facade with mocked injections.
     */
    @InjectMocks
    private ReservationFacade facade = new ReservationFacadeImpl();

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

    @BeforeMethod
    public void setUp() {
        Customer customer1 = new Customer("Albert", "Alojz", "alojz@gmail.com");
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
        trip1DTO.setFromDate(from2);
        trip1DTO.setToDate(to2);
        trip1DTO.setDestination("Bali");
        trip1DTO.setCapacity(22);
        trip1DTO.setPrice(1200.00);

        LocalDate reservation1Time = LocalDate.now().minusDays(5);
        reservation1 = new Reservation(customer1, trip1, reservation1Time);
        reservation1.setId(10L);
        reservation1DTO = new ReservationDTO(customer1DTO, trip1DTO, reservation1Time);

        LocalDate reservation2Time = LocalDate.now().minusDays(3);
        reservation2 = new Reservation(customer1, trip2, reservation2Time);
        reservation2.setId(15L);
        reservation2DTO = new ReservationDTO(customer1DTO, trip2DTO, reservation2Time);
    }

    @Test
    public void createTest() {
        Mockito.doAnswer(invocationOnMock -> {
            Object argument = invocationOnMock.getArgument(0);
            assert (argument instanceof Reservation);
            Assert.assertEquals(argument, reservation1);
            ((Reservation) argument).setId(reservation1.getId());
            return null;
        }).when(service).create(Mockito.any(Reservation.class));
        facade.createReservation(reservation1DTO);
        Assert.assertEquals(reservation1DTO.getId(), reservation1.getId());
    }

}
