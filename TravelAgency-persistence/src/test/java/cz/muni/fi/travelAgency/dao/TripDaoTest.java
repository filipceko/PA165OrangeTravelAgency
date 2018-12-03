package cz.muni.fi.travelAgency.dao;

import cz.muni.fi.travelAgency.PersistenceTestAppContext;
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
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Rajivv
 */
@ContextConfiguration(classes = PersistenceTestAppContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class TripDaoTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private TripDao tripDao;

    private Trip tripBrno;
    private LocalDate firstDate = LocalDate.of(2018, 10, 20);
    private LocalDate secondDate = LocalDate.of(2018, 10, 25);

    /**
     * create trip
     */
    @BeforeMethod
    public void setUpTest() {
        tripBrno = new Trip();
        tripBrno.setFromDate(firstDate);
        tripBrno.setToDate(secondDate);
        tripBrno.setDestination("Lake");
        tripBrno.setCapacity(10);
        tripBrno.setPrice(100.20);
        tripDao.create(tripBrno);
    }

    /**
     * Test creation
     */
    @Test
    public void createTripTest() {
        Trip foundTrip = tripDao.findById(tripBrno.getId());
        Assert.assertEquals(tripBrno, foundTrip);
        Assert.assertEquals(tripBrno.getDestination(), foundTrip.getDestination());
        assertThrows(PersistenceException.class, () -> tripDao.create(new Trip()));
        assertThrows(IllegalArgumentException.class, () -> tripDao.create(null));
    }

    /**
     * Tests trip to find by id
     */
    @Test
    public void testFindTripById() {
        Assert.assertNotNull(tripBrno.getId());
        Trip found = tripDao.findById(tripBrno.getId());
        Assert.assertEquals(firstDate, found.getFromDate());
        Assert.assertEquals(secondDate, found.getToDate());
        Assert.assertEquals("Lake", found.getDestination());
        Assert.assertEquals(10, found.getCapacity());
    }

    /**
     * Tests trip to find by destination
     */
    @Test
    public void findByDestination() {
        Assert.assertEquals(1, tripDao.findByDestination("Lake").size());
        Assert.assertEquals(0, tripDao.findByDestination("Hill").size());

        Assert.assertNotNull(tripBrno.getDestination());
        Collection<Trip> found = tripDao.findByDestination(tripBrno.getDestination());
        Assert.assertNotNull(found);
        List<Trip> expected = new LinkedList<>();
        expected.add(tripBrno);
        Assert.assertEquals(expected, found);
        assertThrows(IllegalArgumentException.class, () -> tripDao.findByDestination(null));
        assertThrows(IllegalArgumentException.class, () -> tripDao.findByDestination(""));
    }

    /**
     * Tests trip to find by Interval date
     */
    @Test
    public void findByInterval() {
        LocalDate startDate = LocalDate.of(2018, 10, 19);
        LocalDate endDate = LocalDate.of(2018, 10, 26);
        Assert.assertEquals(1, tripDao.findByInterval(startDate, endDate).size());
    }

    /**
     * Tests trip to find all
     */
    @Test
    public void testFindAllTrip() {
        Trip tripBali = new Trip();
        tripBali.setFromDate(firstDate);
        tripBali.setToDate(secondDate);
        tripBali.setDestination("Ubud");
        tripBali.setCapacity(5);
        tripBali.setPrice(50.10);
        tripDao.create(tripBali);
        Collection<Trip> founds = tripDao.findAll();
        Assert.assertEquals(2, founds.size());
    }

    /**
     * Tests trip to update by destination
     */
    @Test
    public void testUpdateTrip() {
        Trip found = tripDao.findById(tripBrno.getId());
        Assert.assertNotNull(found);
        found.setDestination("Brno Dam");
        tripDao.update(found);
        Trip updatedTrip = tripDao.findById(found.getId());
        Assert.assertEquals("Brno Dam", updatedTrip.getDestination());
        assertThrows(IllegalArgumentException.class, () -> tripDao.update(null));
    }

    /**
     * Tests trip to remove
     */
    @Test
    public void removeTest() {
        tripDao.remove(tripBrno);
        Assert.assertNull(tripDao.findById(tripBrno.getId()));
        assertThrows(IllegalArgumentException.class, () -> tripDao.remove(new Trip()));
        assertThrows(IllegalArgumentException.class, () -> tripDao.remove(null));
    }
}
