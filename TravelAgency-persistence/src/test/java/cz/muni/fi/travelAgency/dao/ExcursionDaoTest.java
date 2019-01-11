package cz.muni.fi.travelAgency.dao;

import cz.muni.fi.travelAgency.PersistenceTestAppContext;
import cz.muni.fi.travelAgency.entities.Excursion;
import cz.muni.fi.travelAgency.entities.Trip;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.PersistenceException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Excursion DAO testing class
 *
 * @author Simona Raucinova
 */
@ContextConfiguration(classes = PersistenceTestAppContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class ExcursionDaoTest extends AbstractTestNGSpringContextTests {

    /**
     * Data access object for excursions
     */
    @Autowired
    private ExcursionDao excursionDao;

    /**
     * Data access object for trips
     */
    @Autowired
    private TripDao tripDao;

    /**
     * Trip used for testing
     */
    private Trip newYorkTrip;
    /**
     * Trip used for testing
     */
    private Trip parisTrip;
    /**
     * Excursion used for testing
     */
    private Excursion excursionEiffel;
    /**
     * Excursion used for testing
     */
    private Excursion excursionStatueOfLiberty;
    /**
     * Excursion used for testing
     */
    private Excursion excursionObservatory;

    /**
     * Creates testing objects
     */
    @BeforeMethod
    public void setUp() {
        Trip newYorkTrip = new Trip();
        newYorkTrip.setFromDate(LocalDate.of(2018, 7, 1));
        newYorkTrip.setToDate(LocalDate.of(2018, 7, 15));
        newYorkTrip.setDestination("New York");
        newYorkTrip.setCapacity(25);
        newYorkTrip.setPrice(100.20);

        Excursion excursionStatueOfLiberty = new Excursion();
        excursionStatueOfLiberty.setDescription("Visit the America’s most notable visitor experiences " +
                "– the Statue of Liberty National Monument on Liberty Island");
        excursionStatueOfLiberty.setDestination("Statue of Liberty National Monument on Liberty Island");
        excursionStatueOfLiberty.setPrice(30.25);
        excursionStatueOfLiberty.setExcursionDate(LocalDate.of(2018, 7, 8));
        excursionStatueOfLiberty.setExcursionDuration(Duration.ofMinutes(253));
        excursionStatueOfLiberty.setTrip(newYorkTrip);

        Excursion excursionObservatory = new Excursion();
        excursionObservatory.setDescription("See New York City at your feet with admission to the One World Observatory," +
                " a 3-story destination on top of the western hemisphere’s tallest building. ");
        excursionObservatory.setDestination("NYC One World Observatory");
        excursionObservatory.setPrice(34.00);
        excursionObservatory.setExcursionDate(LocalDate.of(2018, 7, 10));
        excursionObservatory.setExcursionDuration(Duration.ofMinutes(120));
        excursionObservatory.setTrip(newYorkTrip);

        Trip parisTrip = new Trip();
        parisTrip.setFromDate(LocalDate.of(2018, 9, 25));
        parisTrip.setToDate(LocalDate.of(2018, 10, 15));
        parisTrip.setDestination("Paris");
        parisTrip.setCapacity(40);
        parisTrip.setPrice(100.50);

        Excursion excursionEiffel = new Excursion();
        excursionEiffel.setDescription("Listen as your host adds colorful history and facts to the sights below as " +
                "you look over Les Invalides, Notre Dame Cathedral and other city landmarks.");
        excursionEiffel.setDestination("Eiffel Tower");
        excursionEiffel.setPrice(47.58);
        excursionEiffel.setExcursionDate(LocalDate.of(2018, 9, 27));
        excursionEiffel.setExcursionDuration(Duration.ofMinutes(60));
        excursionEiffel.setTrip(parisTrip);

        tripDao.create(parisTrip);
        tripDao.create(newYorkTrip);
        excursionDao.create(excursionStatueOfLiberty);
        excursionDao.create(excursionObservatory);
        excursionDao.create(excursionEiffel);

        this.newYorkTrip = newYorkTrip;
        this.parisTrip = parisTrip;
        this.excursionEiffel = excursionEiffel;
        this.excursionObservatory = excursionObservatory;
        this.excursionStatueOfLiberty = excursionStatueOfLiberty;
    }

    @Test
    public void createTest() {
        LocalDate excursionDate = LocalDate.of(2018, 7, 13);
        Excursion testExcursion = new Excursion(newYorkTrip, "Destination", excursionDate, Duration.ofMinutes(20), 10.00, "Description"
        );
        excursionDao.create(testExcursion);
        Excursion stored = excursionDao.findById(testExcursion.getId());
        assertEquals(testExcursion, stored);
        assertEquals(testExcursion.getDescription(), "Description");
        Trip retrievedTrip = stored.getTrip();
        assertEquals(retrievedTrip, newYorkTrip);
        assertEquals(stored.getId(), testExcursion.getId());

        assertThrows(PersistenceException.class, () -> excursionDao.create(new Excursion()));
        assertThrows(PersistenceException.class, () -> excursionDao.create(new Excursion(newYorkTrip, "Destination", excursionDate, Duration.ofMinutes(20), 10.0, null
        )));
        assertThrows(PersistenceException.class, () -> excursionDao.create(new Excursion(newYorkTrip, null, excursionDate, Duration.ofMinutes(20), 10.0, "Description"
        )));
        assertThrows(PersistenceException.class, () -> excursionDao.create(new Excursion(newYorkTrip, "Destination", excursionDate, Duration.ofMinutes(20), null, "Description"
        )));
        assertThrows(PersistenceException.class, () -> excursionDao.create(new Excursion(newYorkTrip, "Destination", null, Duration.ofMinutes(20), 10.0, "Description"
        )));
        assertThrows(PersistenceException.class, () -> excursionDao.create(new Excursion(newYorkTrip, "Destination", excursionDate, null, 10.0, "Description"
        )));
        assertThrows(NullPointerException.class, () -> excursionDao.create(new Excursion(null, "Destination", excursionDate, Duration.ofMinutes(20), 10.0, "Description"
        )));

        assertThrows(IllegalArgumentException.class, () -> excursionDao.create(null));
    }

    @Test
    public void findByIdTest() {
        Excursion libertyStatue = excursionDao.findById(excursionStatueOfLiberty.getId());
        assertEquals(excursionStatueOfLiberty, libertyStatue);

        Excursion observatory = excursionDao.findById(excursionObservatory.getId());
        assertEquals(excursionObservatory, observatory);

        Excursion eiffel = excursionDao.findById(excursionEiffel.getId());
        assertEquals(excursionEiffel, eiffel);

        assertNull(excursionDao.findById(23L));
        assertThrows(IllegalArgumentException.class, () -> excursionDao.findById(null));

    }

    @Test
    public void findAllTest() {
        Collection<Excursion> retrievedExcursions = excursionDao.findAll();
        assertEquals(3, retrievedExcursions.size());
    }

    @Test
    public void findByTripIdTest() {
        Collection<Excursion> retrievedExcursionsNY = excursionDao.findByTripId(newYorkTrip.getId());
        assertEquals(2, retrievedExcursionsNY.size());
        Collection<Excursion> retrievedExcursionsParis = excursionDao.findByTripId(parisTrip.getId());
        assertEquals(1, retrievedExcursionsParis.size());
    }

    @Test
    public void updateTest() {
        excursionEiffel.setPrice(36.00);
        excursionDao.update(excursionEiffel);
        Excursion retrievedExcursion = excursionDao.findById(excursionEiffel.getId());
        assertEquals(excursionEiffel, retrievedExcursion);
        assertEquals(new Double("36.00"), retrievedExcursion.getPrice());
    }

    @Test
    public void removeTest() {
        LocalDate excursionDate = LocalDate.of(2018, 7, 13);
        Excursion testExcursion = new Excursion(newYorkTrip, "Destination", excursionDate, Duration.ofMinutes(20), 10.00, "Description"
        );
        excursionDao.create(testExcursion);
        assertEquals(4, excursionDao.findAll().size());
        excursionDao.remove(testExcursion);
        assertEquals(3, excursionDao.findAll().size());
        excursionDao.remove(excursionEiffel);
        excursionDao.remove(excursionObservatory);
        assertNull(excursionDao.findById(excursionEiffel.getId()));
        assertNull(excursionDao.findById(excursionObservatory.getId()));
        assertFalse(newYorkTrip.getExcursions().contains(excursionObservatory));
        assertFalse(parisTrip.getExcursions().contains(excursionEiffel));
        assertThrows(IllegalArgumentException.class, () -> excursionDao.remove(null));
    }

    @Test
    public void findByDestination() {
        Assert.assertEquals(1, excursionDao.findByDestination("Eiffel Tower").size());
        Assert.assertEquals(0, excursionDao.findByDestination("Museum").size());

        Assert.assertNotNull(excursionEiffel.getDestination());
        Collection<Excursion> found = excursionDao.findByDestination(excursionEiffel.getDestination());
        Assert.assertNotNull(found);
        List<Excursion> expected = new LinkedList<>();
        expected.add(excursionEiffel);
        Assert.assertEquals(expected, found);
        assertThrows(IllegalArgumentException.class, () -> excursionDao.findByDestination(null));

    }

}
