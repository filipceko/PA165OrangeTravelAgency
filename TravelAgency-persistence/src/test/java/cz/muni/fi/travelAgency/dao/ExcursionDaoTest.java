package cz.muni.fi.travelAgency.dao;

import cz.muni.fi.travelAgency.PersistenceTestAppContext;
import cz.muni.fi.travelAgency.entities.Excursion;
import cz.muni.fi.travelAgency.entities.Trip;
import javax.validation.ConstraintViolationException;

import org.junit.AfterClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Simona Raucinova
 */
@ContextConfiguration(classes = PersistenceTestAppContext.class)
public class ExcursionDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory managerFactory;

    @Autowired
    private ExcursionDao excursionDao;

    private Trip newYorkTrip;
    private Excursion excursionStatueOfLiberty;
    private Excursion excursionObservatory;
    private Trip parisTrip;
    private Excursion excursionEiffel;
    private Excursion excursionObservatory2;

    @BeforeClass
    public void setUp(){
        EntityManager manager = managerFactory.createEntityManager();
        manager.getTransaction().begin();

        Trip newYorkTrip = new Trip();
        newYorkTrip.setFromDate(LocalDate.of(2018,7,1));
        newYorkTrip.setToDate(LocalDate.of(2018,7,15));
        newYorkTrip.setDestination("New York");
        newYorkTrip.setCapacity(25);

        Excursion excursionStatueOfLiberty = new Excursion();
        excursionStatueOfLiberty.setDescription("Visit the America’s most notable visitor experiences " +
                "– the Statue of Liberty National Monument on Liberty Island");
        excursionStatueOfLiberty.setDestination("Statue of Liberty National Monument on Liberty Island");
        excursionStatueOfLiberty.setPrice(new BigDecimal("30.25"));
        excursionStatueOfLiberty.setExcursionDate(LocalDate.of(2018,7,8));
        excursionStatueOfLiberty.setExcursionDuration(Duration.ofMinutes(253));
        excursionStatueOfLiberty.setTrip(newYorkTrip);

        Excursion excursionObservatory = new Excursion();
        excursionObservatory.setDescription("See New York City at your feet with admission to the One World Observatory," +
                " a 3-story destination on top of the western hemisphere’s tallest building. ");
        excursionObservatory.setDestination("NYC One World Observatory");
        excursionObservatory.setPrice(new BigDecimal("34.00"));
        excursionObservatory.setExcursionDate(LocalDate.of(2018,7,10));
        excursionObservatory.setExcursionDuration(Duration.ofMinutes(120));
        excursionObservatory.setTrip(newYorkTrip);

        Excursion excursionObservatory2 = new Excursion();
        excursionObservatory2.setDescription("See New York City at your feet with admission to the One World Observatory," +
                " a 3-story destination on top of the western hemisphere’s tallest building. ");
        excursionObservatory2.setDestination("NYC One World Observatory");
        excursionObservatory2.setPrice(new BigDecimal("34.00"));
        excursionObservatory2.setExcursionDate(LocalDate.of(2018,7,12));
        excursionObservatory2.setExcursionDuration(Duration.ofMinutes(120));
        excursionObservatory2.setTrip(newYorkTrip);

        Trip parisTrip = new Trip();
        parisTrip.setFromDate(LocalDate.of(2018,9,25));
        parisTrip.setToDate(LocalDate.of(2018,10,15));
        parisTrip.setDestination("Paris");
        parisTrip.setCapacity(40);

        Excursion excursionEiffel = new Excursion();
        excursionEiffel.setDescription("Listen as your host adds colorful history and facts to the sights below as " +
                "you look over Les Invalides, Notre Dame Cathedral and other city landmarks.");
        excursionEiffel.setDestination("Eiffel Tower");
        excursionEiffel.setPrice(new BigDecimal("47.58"));
        excursionEiffel.setExcursionDate(LocalDate.of(2018,9,27));
        excursionEiffel.setExcursionDuration(Duration.ofMinutes(60));
        excursionEiffel.setTrip(parisTrip);

        manager.persist(parisTrip);
        manager.persist(newYorkTrip);
        manager.persist(excursionStatueOfLiberty);
        manager.persist(excursionObservatory);
        manager.persist(excursionObservatory2);
        manager.persist(excursionEiffel);

        manager.getTransaction().commit();
        manager.close();

        this.newYorkTrip = newYorkTrip;
        this.parisTrip = parisTrip;
        this.excursionEiffel = excursionEiffel;
        this.excursionObservatory = excursionObservatory;
        this.excursionStatueOfLiberty = excursionStatueOfLiberty;
        this.excursionObservatory2 = excursionObservatory2;
    }

    /**
     * Validates excursion is created as expected and valid exceptions are thrown on error.
     */
    @Test
    public void createTest(){
        EntityManager manager = managerFactory.createEntityManager();
        LocalDate excursionDate = LocalDate.of(2018,7,13);
        Excursion testExcursion = new Excursion("Description","Destination",new BigDecimal("10.00"),
                                                excursionDate ,Duration.ofMinutes(20),newYorkTrip);
        excursionDao.create(testExcursion);
        Excursion stored = manager.createQuery("select e from Excursion e where e.excursionDate = :date",
                Excursion.class).setParameter("date", excursionDate).getSingleResult();
        assertEquals(testExcursion, stored);
        assertEquals(testExcursion.getDescription(),"Description");
        Trip retrievedTrip = stored.getTrip();
        assertEquals(retrievedTrip, newYorkTrip);
        assertEquals(stored.getId(), testExcursion.getId());

        assertThrows(ConstraintViolationException.class, () -> excursionDao.create(new Excursion()));
        assertThrows(ConstraintViolationException.class, () -> excursionDao.create(new Excursion(null,
                                                                        "Destination",new BigDecimal("10"),
                                                                    excursionDate ,Duration.ofMinutes(20),newYorkTrip)));
        assertThrows(ConstraintViolationException.class, () -> excursionDao.create(new Excursion("Description",
                null,new BigDecimal("10"),
                excursionDate ,Duration.ofMinutes(20),newYorkTrip)));
        assertThrows(ConstraintViolationException.class, () -> excursionDao.create(new Excursion("Description",
                "Destination",null,
                excursionDate ,Duration.ofMinutes(20),newYorkTrip)));
        assertThrows(ConstraintViolationException.class, () -> excursionDao.create(new Excursion("Description",
                "Destination",new BigDecimal("10"),
                null ,Duration.ofMinutes(20),newYorkTrip)));
        assertThrows(ConstraintViolationException.class, () -> excursionDao.create(new Excursion("Description",
                "Destination",new BigDecimal("10"),
                excursionDate ,null,newYorkTrip)));
        assertThrows(NullPointerException.class, () -> excursionDao.create(new Excursion("Description",
                "Destination",new BigDecimal("10"),
                excursionDate ,Duration.ofMinutes(20),null)));

        assertThrows(IllegalArgumentException.class, () -> excursionDao.create(null));
        excursionDao.remove(testExcursion);
    }

    @Test
    public void findByIdTest() {
        Excursion libertyStatue = excursionDao.findById(excursionStatueOfLiberty.getId());
        assertEquals(excursionStatueOfLiberty, libertyStatue);

        Excursion observatory = excursionDao.findById(excursionObservatory.getId());
        assertEquals(excursionObservatory,observatory);

        Excursion eiffel = excursionDao.findById(excursionEiffel.getId());
        assertEquals(excursionEiffel,eiffel);

        Excursion observatory2 = excursionDao.findById(excursionObservatory2.getId());
        assertEquals(excursionObservatory2,observatory2);

        assertNull(excursionDao.findById(23L));
        assertThrows(IllegalArgumentException.class, () -> excursionDao.findById(null));

    }

    @Test
    public void findAllTest(){
        Collection<Excursion> retrievedExcursions = excursionDao.findAll();
        assertEquals(4,retrievedExcursions.size());
    }

    @Test
    public void findByTripTest(){
        Collection<Excursion> retrievedExcursionsNY = excursionDao.findByTrip(newYorkTrip);
        assertEquals(3,retrievedExcursionsNY.size());
        Collection<Excursion> retrievedExcursionsParis = excursionDao.findByTrip(parisTrip);
        assertEquals(1,retrievedExcursionsParis.size());
    }

    @Test
    public void findByDestinationTest(){
        Collection<Excursion> retrievedExcursions = excursionDao.findByDestination("NYC One World Observatory");
        assertEquals(2,retrievedExcursions.size());
        assertTrue(retrievedExcursions.contains(excursionObservatory));
        assertTrue(retrievedExcursions.contains(excursionObservatory2));

        retrievedExcursions = excursionDao.findByDestination("Eiffel Tower");
        assertEquals(1,retrievedExcursions.size());
        assertTrue(retrievedExcursions.contains(excursionEiffel));
    }

    @Test
    public void updateTest(){
        excursionEiffel.setPrice(new BigDecimal("36.00"));
        excursionDao.update(excursionEiffel);
        Excursion retrievedExcursion = excursionDao.findById(excursionEiffel.getId());
        assertEquals(excursionEiffel,retrievedExcursion);
        assertEquals(new BigDecimal("36.00"),retrievedExcursion.getPrice());
    }

    @Test
    public void removeTest(){
        LocalDate excursionDate = LocalDate.of(2018,7,13);
        Excursion testExcursion = new Excursion("Description","Destination",new BigDecimal("10.00"),
                excursionDate ,Duration.ofMinutes(20),newYorkTrip);
        excursionDao.create(testExcursion);
        assertEquals(5,excursionDao.findAll().size());
        excursionDao.remove(testExcursion);
        assertEquals(4,excursionDao.findAll().size());
        assertThrows(IllegalArgumentException.class, () -> excursionDao.remove(null));

    }

    @AfterClass
    public void tearDown(){
        excursionDao.remove(excursionEiffel);
        excursionDao.remove(excursionObservatory);
        excursionDao.remove(excursionStatueOfLiberty);
        //Test remove was successful
        assertNull(excursionDao.findById(excursionEiffel.getId()));
        assertNull(excursionDao.findById(excursionObservatory.getId()));
        assertNull(excursionDao.findById(excursionStatueOfLiberty.getId()));
        //Delete the rest
        EntityManager manager = managerFactory.createEntityManager();
        manager.remove(newYorkTrip);
        manager.remove(parisTrip);
        manager.getTransaction().commit();
        manager.close();
    }
}
