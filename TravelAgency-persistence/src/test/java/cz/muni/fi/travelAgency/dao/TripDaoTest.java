package cz.muni.fi.travelAgency.dao;

import cz.muni.fi.travelAgency.PersistenceTestAppContext;
import cz.muni.fi.travelAgency.entities.Trip;
import java.time.LocalDate;
import java.util.Collection;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

/**
 *
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
    @BeforeClass
    public void createTripTest() {
        tripBrno = new Trip();
        tripBrno.setFromDate(firstDate);
        tripBrno.setToDate(secondDate);
        tripBrno.setDestination("Lake");
        tripBrno.setCapacity(10);
        tripDao.create(tripBrno);
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
    }

    /**
     * Tests trip to find by Interval date
     */
    @Test
    public void findByInterval() {
        LocalDate startDate = LocalDate.of(2018, 10, 20);
        LocalDate endDate = LocalDate.of(2018, 10, 25);
        Assert.assertEquals(1, tripDao.findByInterval(startDate, endDate).size());
    }

    /**
     * Tests trip to find all
     */
    @Test
    public void testFindAllTrip() {
        //create new trip
        Trip tripBali = new Trip();
        tripBali.setFromDate(firstDate);
        tripBali.setToDate(secondDate);
        tripBali.setDestination("Ubud");
        tripBali.setCapacity(5);
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
    }

    /**
     * Tests trip to delete
     */
    @Test
    public void testDeleteTrip() {
        Assert.assertNotNull(tripDao.findById(tripBrno.getId()));
        tripDao.delete(tripBrno);
        Assert.assertNull(tripDao.findById(tripBrno.getId()));
    }
}
