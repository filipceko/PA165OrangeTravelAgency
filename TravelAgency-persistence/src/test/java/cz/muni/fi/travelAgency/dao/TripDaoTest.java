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

    private Trip trip1;
    private LocalDate firstDate = LocalDate.of(2018, 10, 20);
    private LocalDate secondDate = LocalDate.of(2018, 10, 25);

    /**
     * create trip
     */
    @BeforeClass
    public void createTripTest() {
        trip1 = new Trip();
        trip1.setFromDate(firstDate);
        trip1.setToDate(secondDate);
        trip1.setDestination("Lake");
        trip1.setCapacity(10);
        tripDao.create(trip1);
    }

    /**
     * Tests trip to find by id
     */
    @Test
    public void testFindTripById() {
        Assert.assertNotNull(trip1.getId());
        Trip found = tripDao.findById(trip1.getId());
        Assert.assertEquals(found.getFromDate(), firstDate);
        Assert.assertEquals(found.getToDate(), secondDate);
        Assert.assertEquals(found.getDestination(), "Lake");
        Assert.assertEquals(found.getCapacity(), 10);
    }

    /**
     * Tests trip to find by destination
     */
    @Test
    public void findByDestination() {
        Assert.assertEquals(tripDao.findByDestination("Lake").size(), 1);
        Assert.assertEquals(tripDao.findByDestination("Hill").size(), 0);
    }

    /**
     * Tests trip to find by Interval date
     */
    @Test
    public void findByInterval() {
        LocalDate startDate = LocalDate.of(2018, 7, 29);
        LocalDate endDate = LocalDate.of(2018, 11, 24);
        Assert.assertEquals(tripDao.findByInterval(startDate, endDate).size(), 0);
    }

    /**
     * Tests trip to find all
     */
    @Test
    public void testFindAllTrip() {
        //create new trip
        Trip trip2 = new Trip();
        trip2.setFromDate(firstDate);
        trip2.setToDate(secondDate);
        trip2.setDestination("Brno Dragon");
        trip2.setCapacity(5);
        tripDao.create(trip2);
        Collection<Trip> founds = tripDao.findAll();
        Assert.assertEquals(founds.size(), 2);
    }

    /**
     * Tests trip to update by destination
     */
    @Test
    public void testUpdateTrip() {

        Trip found = tripDao.findById(trip1.getId());
        Assert.assertNotNull(found);
        found.setDestination("Brno Dam");
        tripDao.update(found);
        Trip updatedTrip = tripDao.findById(found.getId());
        Assert.assertEquals(updatedTrip.getDestination(), "Brno Dam");
    }

    /**
     * Tests trip to delete
     */
    @Test
    public void testDeleteTrip() {
        Assert.assertNotNull(tripDao.findById(trip1.getId()));
        tripDao.delete(trip1);
        Assert.assertNull(tripDao.findById(trip1.getId()));
    }

}
