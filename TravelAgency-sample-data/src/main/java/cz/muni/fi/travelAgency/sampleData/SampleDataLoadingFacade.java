package cz.muni.fi.travelAgency.sampleData;

import cz.muni.fi.travelAgency.entities.Customer;
import cz.muni.fi.travelAgency.entities.Excursion;
import cz.muni.fi.travelAgency.entities.Reservation;
import cz.muni.fi.travelAgency.entities.Trip;
import cz.muni.fi.travelAgency.service.CustomerService;
import cz.muni.fi.travelAgency.service.ExcursionService;
import cz.muni.fi.travelAgency.service.ReservationService;
import cz.muni.fi.travelAgency.service.TripService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Facade loading all the sample data to.
 *
 * @author Filip Cekovsky
 */
@Component
@Transactional
public class SampleDataLoadingFacade implements DataLoadingFacade {

    final static Logger logger = LoggerFactory.getLogger(SampleDataLoadingFacade.class);

    @Autowired
    private TripService tripService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ExcursionService excursionService;

    @Autowired
    private ReservationService reservationService;

    private static LocalDate daysFromNow(int days) {
        if (days >= 0) {
            return LocalDate.now().plusDays(days);
        } else {
            return LocalDate.now().minusDays(Math.abs(days));
        }
    }

    @Override
    public void loadData() throws IOException {
        //TRIPS
        Trip rome = new Trip(daysFromNow(10), daysFromNow(17), "Rome, Italy", 30, 1000.0);
        Trip athens = new Trip(daysFromNow(80), daysFromNow(85), "Athens, Greece", 15, 850.0);
        Trip milan = new Trip(daysFromNow(-10), daysFromNow(0), "Milan, Italy", 10, 430.0);
        Trip machuPicchu = new Trip(daysFromNow(360), daysFromNow(375), "Machu Picchu, Peru", 8, 2500.0);
        Trip sydney = new Trip(daysFromNow(180), daysFromNow(200), "Sydney, Australia", 20, 3054.45);
        //persist
        tripService.createTrip(rome);
        tripService.createTrip(athens);
        tripService.createTrip(milan);
        tripService.createTrip(machuPicchu);
        tripService.createTrip(sydney);
        logger.info("Trips loaded.");

        //CUSTOMERS
        Customer admin = new Customer("Admin", "Admin", "admin@orange.org");
        admin.setAdmin(true);
        Customer filip = new Customer("Filip", "Cekovsky", "filipceko@gmail.com",
                "+421908925045", "EB428563", LocalDate.of(1995, 7, 30));
        filip.setAdmin(false);
        Customer simona = new Customer("Simona", "Raucinova", "simi@rauci.sk");
        simona.setAdmin(false);
        Customer rajiv = new Customer("Rajiv", "Rajiv", "rajiv@rajiv.com");
        rajiv.setAdmin(false);
        Customer rithy = new Customer("Rithy", "Li", "rithy.li@gmail.com");
        rithy.setAdmin(false);
        //persist
        customerService.registerAdmin(admin, "admin");
        customerService.registerCustomer(filip, "ceko");
        customerService.registerCustomer(simona, "PassWord4");
        customerService.registerCustomer(rajiv, "JavaIsLove");
        customerService.registerCustomer(rithy, "password");
        logger.info("Users loaded.");

        //EXCURSIONS
        Excursion colosseum = new Excursion(rome, "Colosseum", daysFromNow(12), Duration.ofHours(5),
                20.0, "Tour trough the ancient Colosseum");
        Excursion forum = new Excursion(rome, "Forum Romanum", daysFromNow(14), Duration.ofHours(6), 15.0,
                "Forum Romanum, ancient ruins from early roman empire, guided tour");
        Excursion romeCity = new Excursion(rome, "Rome city tour", daysFromNow(11), Duration.ofHours(10), 36.42,
                "Guided tour trough the city of Rome with entrances to the most significant sights guided by our most skilled guid.");
        Excursion acropolis = new Excursion(athens, "Acropolis", daysFromNow(81), Duration.ofHours(4), 11.0,
                "Visit with us the most famous sight in Athens, the Acropolis");
        Excursion mycanae = new Excursion(athens, "Mycanae", daysFromNow(82), Duration.ofDays(1), 35.0,
                "Full day trip to the nearby sight of Mycenae, TOTAL BESTSELLER");
        Excursion cathedral = new Excursion(milan, "Milan Cathedral", daysFromNow(-8), Duration.ofMinutes(150), 15.56,
                "Take a look with us in the most amazing structure in Milan, the Cathedral. We will also take " +
                        "you to the restricted area of the rooftops with only fraction of the original price");
        Excursion operaHouse = new Excursion(sydney, "Sydney Opera House", daysFromNow(185), Duration.ofHours(3), 70.0,
                "Guided tour trough the famous Sydney Opera house. Discover it's architecture and curiosities.");
        //persist
        excursionService.createExcursion(colosseum);
        excursionService.createExcursion(forum);
        excursionService.createExcursion(romeCity);
        excursionService.createExcursion(acropolis);
        excursionService.createExcursion(mycanae);
        excursionService.createExcursion(cathedral);
        excursionService.createExcursion(operaHouse);
        logger.info("Excursions loaded.");

        //RESERVATIONS
        Reservation filipRome = new Reservation(filip, rome, daysFromNow(0));
        Set<Excursion> excursions = new HashSet<>();
        Collections.addAll(excursions, colosseum, forum, romeCity);
        Reservation simonaRome = new Reservation(simona, rome, daysFromNow(-2), excursions);
        Reservation rajivMachu = new Reservation(rajiv, machuPicchu, daysFromNow(15));
        Reservation rithyMachu = new Reservation(rithy, machuPicchu, daysFromNow(-50));
        //persist
        reservationService.create(filipRome);
        reservationService.create(simonaRome);
        reservationService.create(rajivMachu);
        reservationService.create(rithyMachu);
        logger.info("Reservations loaded.");
    }
}
