package cz.muni.fi.travelAgency.service;

import cz.muni.fi.travelAgency.config.ServiceConfiguration;
import cz.muni.fi.travelAgency.dao.ExcursionDao;
import cz.muni.fi.travelAgency.entities.Excursion;
import cz.muni.fi.travelAgency.entities.Trip;
import cz.muni.fi.travelAgency.exceptions.DataAccessException;
import java.math.BigDecimal;
import java.time.Duration;
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
 * Simple excursion service tests using Mockito.
 *
 * @author Rajivv
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class ExcursionServiceTest extends AbstractTestNGSpringContextTests {

    /**
     * Service tested with mocked injections.
     */
    @InjectMocks
    private final ExcursionService excursionService = new ExcursionServiceImpl();

    /**
     * Mocked excursion data access object for testing.
     */
    @Mock
    private ExcursionDao excursionDao;

    /**
     * Instance of excursion used for testing.
     */
    private Excursion excursionKutaBeach;
    private Excursion excursionUbud;
    private Excursion excursionPrambanan;
    private Excursion excursionBorobudur;

    /**
     * Set's up the Mockito injections.
     */
    @BeforeClass
    public void initMockito() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Setts up the Excursion with required fields.
     */
    @BeforeMethod
    public void initTest() {

        /**
         * Setts up the Trips.
         */
        Trip baliTrip = new Trip(LocalDate.now(), LocalDate.now().plusDays(7L), "Bali", 20, 100.20);
        Trip yogyaTrip = new Trip(LocalDate.now(), LocalDate.now().plusDays(5L), "Yogyakarta", 15, 500.50);

        /**
         * Setts up the Excursions.
         */
        excursionKutaBeach = new Excursion();
        excursionKutaBeach.setDescription("Visit the Bali's most Famous Beach with the beautiful sunset");
        excursionKutaBeach.setDestination("Kuta Beach");
        excursionKutaBeach.setPrice(new BigDecimal("25.10"));
        excursionKutaBeach.setExcursionDate(LocalDate.now());
        excursionKutaBeach.setExcursionDuration(Duration.ofMinutes(120));
        excursionKutaBeach.setTrip(baliTrip);

        excursionUbud = new Excursion();
        excursionUbud.setDescription("Visit the Bali's most Famous village");
        excursionUbud.setDestination("Ubud");
        excursionUbud.setPrice(new BigDecimal("35.20"));
        excursionUbud.setExcursionDate(LocalDate.now().plusDays(2L));
        excursionUbud.setExcursionDuration(Duration.ofMinutes(240));
        excursionUbud.setTrip(baliTrip);

        excursionPrambanan = new Excursion();
        excursionPrambanan.setDescription("or Rara Jonggrang (Javanese: ꦫꦫꦗꦺꦴꦁꦒꦿꦁ, translit. Rara Jonggrang) is a 9th-century Hindu temple compound in Special Region of Yogyakarta, Indonesia, dedicated to the Trimūrti, the expression of God as the Creator (Brahma), the Preserver (Vishnu) and the Transformer (Shiva)..");
        excursionPrambanan.setDestination("Prambanan");
        excursionPrambanan.setPrice(new BigDecimal("100.30"));
        excursionPrambanan.setExcursionDate(LocalDate.now().plusDays(1L));
        excursionPrambanan.setExcursionDuration(Duration.ofMinutes(1440));
        excursionPrambanan.setTrip(yogyaTrip);

        excursionBorobudur = new Excursion();
        excursionBorobudur.setDescription("is a 9th-century Mahayana Buddhist temple in Magelang Regency, not far from the town of Muntilan, in Central Java, Indonesia. It is the world's largest Buddhist temple.[1][2][3] The temple consists of nine stacked platforms, six square and three circular, topped by a central dome..");
        excursionBorobudur.setDestination("Borobudur");
        excursionBorobudur.setPrice(new BigDecimal("250.20"));
        excursionBorobudur.setExcursionDate(LocalDate.now().plusDays(2L));
        excursionBorobudur.setExcursionDuration(Duration.ofMinutes(2880));
        excursionBorobudur.setTrip(yogyaTrip);
    }

    /**
     * Tests create calls create on the DAO.
     */
    @Test
    public void createTest() {
        excursionService.createExcursion(excursionKutaBeach);
        Mockito.verify(excursionDao).create(excursionKutaBeach);
    }

    /**
     * Tests service validates the argument.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    void createNullTest() {
        excursionService.createExcursion(null);
    }

    /**
     * Tests that exceptions thrown by the DAO are thrown as DataAccessException
     */
    @Test(expectedExceptions = DataAccessException.class)
    void createThrowsTest() {
        Mockito.doThrow(IllegalArgumentException.class).when(excursionDao).create(excursionUbud);
        excursionService.createExcursion(excursionUbud);
    }

    /**
     * Tests find by Id.
     */
    @Test
    public void findByIdTest() {
        Long id = 5L;
        excursionKutaBeach.setId(id);
        Mockito.when(excursionDao.findById(id)).thenReturn(excursionKutaBeach);
        Excursion result = excursionService.findExcursionById(id);
        Assert.assertEquals(excursionKutaBeach, result);
        Assert.assertEquals(id, excursionKutaBeach.getId());
    }

    /**
     * Tests service validates the argument.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findByNullIdTest() {
        excursionService.findExcursionById(null);
    }

    /**
     * Tests that exceptions thrown by the DAO are thrown as DataAccessException
     */
    @Test(expectedExceptions = DataAccessException.class)
    void findByIdThrowsTest() {
        Mockito.when(excursionDao.findById(11L)).thenThrow(IllegalArgumentException.class);
        excursionService.findExcursionById(11L);
    }

    /**
     * Tests get all excursion.
     */
    @Test
    public void getAllExcursionsTest() {
        Set<Excursion> list = new HashSet<>();
        list.add(excursionKutaBeach);
        list.add(excursionUbud);
        list.add(excursionPrambanan);
        list.add(excursionBorobudur);
        Mockito.when(excursionDao.findAll()).thenReturn(list);
        Collection<Excursion> result = excursionService.getAllExcursions();
        Assert.assertEquals(result.size(), 4);
        Assert.assertTrue(result.contains(excursionKutaBeach));
        Assert.assertTrue(result.contains(excursionUbud));
    }

    /**
     * Tests that exceptions thrown by the DAO are thrown as DataAccessException
     */
    @Test(expectedExceptions = DataAccessException.class)
    void getAllThrowsTest() {
        Mockito.when(excursionDao.findAll()).thenThrow(IllegalArgumentException.class);
        excursionService.getAllExcursions();
    }

    /**
     * Tests find by Destination.
     */
    @Test
    public void findExcursionByDestinationTest() {
        Set<Excursion> list = new HashSet<>();
        list.add(excursionKutaBeach);
        Mockito.when(excursionDao.findByDestination(Mockito.anyString())).thenReturn(list);
        Collection<Excursion> result = excursionService.findExcursionByDestination("Kuta Beach");
        Assert.assertEquals(result.size(), 1);
        Assert.assertTrue(result.contains(excursionKutaBeach));
    }

    /**
     * Tests service validates the argument.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findByNullDestinationTest() {
        excursionService.findExcursionByDestination(null);
    }

    /**
     * Tests that exceptions thrown by the DAO are thrown as DataAccessException
     */
    @Test(expectedExceptions = DataAccessException.class)
    void findByDestinationThrowsTest() {
        Mockito.when(excursionDao.findByDestination("Legian")).thenThrow(IllegalArgumentException.class);
        excursionService.findExcursionByDestination("Legian");
    }

    /**
     * Tests find by Trip.
     */
    @Test
    public void findByTripTest() {
        Set<Excursion> list = new HashSet<>();
        list.add(excursionKutaBeach);
        list.add(excursionUbud);
        Mockito.when(excursionDao.findByTripId(Mockito.any())).thenReturn(list);
        Collection<Excursion> result = excursionService.findExcursionByTripId(10L);
        Assert.assertEquals(result.size(), 2);
        Assert.assertTrue(result.contains(excursionKutaBeach));
        Assert.assertTrue(result.contains(excursionUbud));
    }

    /**
     * Tests service validates the argument.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findByNullTripTest() {
        excursionService.findExcursionByTripId(null);
    }

    /**
     * Tests that exceptions thrown by the DAO are thrown as DataAccessException
     */
    @Test(expectedExceptions = DataAccessException.class)
    void findByTripThrowsTest() {
        Mockito.when(excursionDao.findByTripId(Mockito.anyLong())).thenThrow(IllegalArgumentException.class);
        excursionService.findExcursionByTripId(30L);
    }

    /**
     * Tests update on DAO is called.
     */
    @Test
    public void updateTest() {
        excursionKutaBeach.setId(15L);
        excursionKutaBeach.setDestination("Beach Walk");
        excursionService.updateExcursion(excursionKutaBeach);
        Mockito.verify(excursionDao, Mockito.times(1)).update(excursionKutaBeach);
    }

    /**
     * Tests service validates the argument.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateNullTest() {
        excursionService.updateExcursion(null);
    }

    /**
     * Tests service validates the argument.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateNoIdTest() {
        try {
            excursionService.updateExcursion(excursionUbud);
        } finally {
            Mockito.verify(excursionDao, Mockito.never()).update(Mockito.any());
        }
    }

    /**
     * Tests that exceptions thrown by the DAO are thrown as DataAccessException
     */
    @Test(expectedExceptions = DataAccessException.class)
    void updateThrowsTest() {
        excursionBorobudur.setId(85L);
        Mockito.doThrow(IllegalArgumentException.class).when(excursionDao).update(Mockito.any(Excursion.class));
        excursionService.updateExcursion(excursionBorobudur);
    }

    /**
     * Tests delete on DAO is called.
     */
    @Test
    public void deleteTest() {
        excursionKutaBeach.setId(17L);
        excursionService.deleteExcursion(excursionKutaBeach);
        Mockito.verify(excursionDao, Mockito.times(1)).remove(excursionKutaBeach);
    }

    /**
     * Tests service validates the argument.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void deleteNullTest() {
        excursionService.deleteExcursion(null);
    }

    /**
     * Tests service validates the argument.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void deleteNoIdTest() {
        try {
            excursionService.deleteExcursion(excursionUbud);
        } finally {
            Mockito.verify(excursionDao, Mockito.never()).remove(Mockito.any());
        }
    }

    /**
     * Tests that exceptions thrown by the DAO are thrown as DataAccessException
     */
    @Test(expectedExceptions = DataAccessException.class)
    void deleteThrowsTest() {
        excursionPrambanan.setId(24L);
        Mockito.doThrow(IllegalArgumentException.class).when(excursionDao).remove(excursionPrambanan);
        excursionService.deleteExcursion(excursionPrambanan);
    }

}
