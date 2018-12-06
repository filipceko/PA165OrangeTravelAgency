package cz.muni.fi.travelAgency.facade;

import cz.muni.fi.travelAgency.DTO.ExcursionDTO;
import cz.muni.fi.travelAgency.DTO.TripDTO;
import cz.muni.fi.travelAgency.config.ServiceConfiguration;
import cz.muni.fi.travelAgency.entities.Excursion;
import cz.muni.fi.travelAgency.entities.Trip;
import cz.muni.fi.travelAgency.service.BeanMappingService;
import cz.muni.fi.travelAgency.service.ExcursionService;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.inject.Inject;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for ExcursionFacade.
 *
 * @author Rajivv
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class ExcursionFacadeTest extends AbstractTestNGSpringContextTests {

    @Spy
    @Inject
    private BeanMappingService beanMappingService;

    /**
     * Mocked service to test with
     */
    @Mock
    private ExcursionService excursionService;

    /**
     * Tested facade with mocked injections.
     */
    @InjectMocks
    private ExcursionFacade excursionFacade = new ExcursionFacadeImpl();

    /**
     * Excursions for testing purposes.
     */
    private Excursion excursionKutaBeach;
    private ExcursionDTO excursionKutaBeachDTO;

    private Excursion excursionStatueOfMonas;
    private ExcursionDTO excursionStatueOfMonasDTO;

    /**
     * Sets up mockito before running tests.
     */
    @BeforeClass
    public void setUpMockito() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Initializes the Excursions used for testing
     */
    @BeforeMethod
    public void setUp() {

        LocalDate from1 = LocalDate.now();
        LocalDate to1 = LocalDate.now().plusMonths(1).plusDays(7);
        Trip baliTrip = new Trip(from1, to1, "Bali", 20, 4000.50);
        TripDTO baliTripDTO = new TripDTO();
        baliTripDTO.setFromDate(from1);
        baliTripDTO.setToDate(to1);
        baliTripDTO.setDestination("Bali");
        baliTripDTO.setCapacity(20);
        baliTripDTO.setPrice(4000.50);

        LocalDate from2 = LocalDate.now().plusMonths(2).plusDays(10);
        LocalDate to2 = LocalDate.now().plusMonths(2).plusDays(18);
        Trip jakartaTrip = new Trip(from2, to2, "Jakarta", 15, 3000.00);
        TripDTO jakartaTripDTO = new TripDTO();
        jakartaTripDTO.setFromDate(from2);
        jakartaTripDTO.setToDate(to2);
        jakartaTripDTO.setDestination("Jakarta");
        jakartaTripDTO.setCapacity(15);
        jakartaTripDTO.setPrice(3000.00);

        LocalDate baliTime = LocalDate.now();
        Double baliPrice = (200.10);
        Duration baliDuration = Duration.ofMinutes(120);
        String baliDescription = "Visit the Bali's most Famous Beach with the beautiful sunset";
        String baliDestination = "Kuta Beach";
        excursionKutaBeach = new Excursion(baliTrip, baliDestination, baliTime, baliDuration, baliPrice, baliDescription);
        excursionKutaBeach.setId(15L);
        excursionKutaBeachDTO = new ExcursionDTO();
        excursionKutaBeachDTO.setDescription(baliDescription);
        excursionKutaBeachDTO.setDestination(baliDestination);
        excursionKutaBeachDTO.setPrice(baliPrice);
        excursionKutaBeachDTO.setExcursionDate(baliTime);
        excursionKutaBeachDTO.setExcursionDuration(baliDuration);
        excursionKutaBeachDTO.setTrip(baliTripDTO);

        LocalDate jakartaTime = LocalDate.now().plusMonths(2).plusDays(10);
        Double jakartaPrice = (500.50);
        Duration jakartaDuration = Duration.ofMinutes(240);
        String jakartaDescription = "The National Monument (Indonesian: Monumen Nasional, abbreviated Monas) is a 132 m (433 ft) tower in the centre of Merdeka Square, Central Jakarta, symbolizing the fight for Indonesia";
        String jakartaDestination = "Statue Of Monas";
        excursionStatueOfMonas = new Excursion(jakartaTrip, jakartaDestination, jakartaTime, jakartaDuration, jakartaPrice, jakartaDescription);
        excursionStatueOfMonas.setId(20L);
        excursionStatueOfMonasDTO = new ExcursionDTO();
        excursionStatueOfMonasDTO.setDescription(jakartaDescription);
        excursionStatueOfMonasDTO.setDestination(jakartaDestination);
        excursionStatueOfMonasDTO.setPrice(jakartaPrice);
        excursionStatueOfMonasDTO.setExcursionDate(jakartaTime);
        excursionStatueOfMonasDTO.setExcursionDuration(jakartaDuration);
        excursionStatueOfMonasDTO.setTrip(jakartaTripDTO);
    }

    /**
     * Create test
     */
    @Test
    public void createExcursionTest() {
        Mockito.doAnswer(invocationOnMock -> {
            Object argument = invocationOnMock.getArgument(0);
            assert (argument instanceof Excursion);
            ((Excursion) argument).setId(excursionKutaBeach.getId());
            return null;
        }).when(excursionService).createExcursion(Mockito.any(Excursion.class));
        excursionFacade.createExcursion(excursionKutaBeachDTO);
        Assert.assertEquals(excursionKutaBeachDTO.getId(), excursionKutaBeach.getId());
    }

    /**
     * Get all test
     */
    @Test
    public void getAllExcurisonsTest() {
        List<Excursion> list = new LinkedList<>();
        list.add(excursionKutaBeach);
        list.add(excursionStatueOfMonas);
        Mockito.when(excursionService.getAllExcursions()).thenReturn(list);
        Collection<ExcursionDTO> result = excursionFacade.getAllExcursions();
        Assert.assertEquals(result.size(), 2);
        result.stream().findFirst().ifPresent(excursionDTO -> Assert.assertTrue(excursionDTO.equals(excursionKutaBeachDTO) || excursionDTO.equals(excursionStatueOfMonasDTO)));
    }

    /**
     * Find by ID test
     */
    @Test
    public void findExcursionById() {
        Mockito.when(excursionService.findExcursionById(15L)).thenReturn(excursionKutaBeach);
        ExcursionDTO result = excursionFacade.findExcursionById(15L);
        Assert.assertEquals(result, excursionKutaBeachDTO);
    }

    /**
     * Find by destination test
     */
    @Test
    public void findExcursionByDestination() {
        List<Excursion> list = new LinkedList<>();
        list.add(excursionKutaBeach);
        Mockito.when(excursionService.findExcursionByDestination("Kuta Beach")).thenReturn(list);
        Collection<ExcursionDTO> result = excursionFacade.findExcursionByDestination("Kuta Beach");
        Assert.assertEquals(result.size(), 1);
        result.stream().findFirst().ifPresent(excursionDTO -> Assert.assertEquals(excursionDTO, excursionKutaBeachDTO));
    }

    /**
     * Find by trip test
     */
    @Test
    public void findExcursionByTripId() {
        List<Excursion> list = new LinkedList<>();
        list.add(excursionKutaBeach);
        Mockito.when(excursionService.findExcursionByTripId(16L)).thenReturn(list);
        Collection<ExcursionDTO> result = excursionFacade.findExcursionByTripId(16L);
        Assert.assertEquals(result.size(), 1);
        result.stream().findFirst().ifPresent(excursionDTO -> Assert.assertEquals(excursionDTO, excursionKutaBeachDTO));
    }

    /**
     * Update test
     */
    @Test
    public void updateTest() {
        excursionKutaBeachDTO.setId(16L);
        excursionFacade.updateExcursion(excursionKutaBeachDTO);
        Mockito.verify(excursionService).updateExcursion(Mockito.any(Excursion.class));
    }

    /**
     * Delete test
     */
    @Test
    public void deleteTest() {
        excursionKutaBeachDTO.setId(16L);
        excursionFacade.deleteExcursion(excursionKutaBeachDTO);
        Mockito.verify(excursionService).deleteExcursion(Mockito.any(Excursion.class));
    }

}
