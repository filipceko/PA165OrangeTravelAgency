package cz.muni.fi.travelAgency.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.*;

/**
 * Reservation entity managing connection between {@link Trip}, {@link Customer}
 * and booked {@link Excursion} for the trip
 *
 * @author Rithy
 */
@Entity
@Table(name = "RESERVATION")
public class Reservation {

    /**
     * Unique ID set by the DB
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Customer who made this reservation
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    private Customer customer;

    /**
     * Trip this reservation is related to
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRIP_ID", nullable = false)
    private Trip trip;

    /**
     * Date this reservation was made on
     */
    @NotNull
    @Column(nullable = false)
    private LocalDate reserveDate;

    /**
     * Excursions reserved with this trip
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "RESERVATIONS_EXCURSIONS")
    private Collection<Excursion> excursions = new HashSet<>();

    /**
     * Basic non-parametric constructor.
     */
    public Reservation() {
    }

    /**
     * All fields constructor.
     */
    public Reservation(Customer customer, Trip trip, LocalDate reserveDate, Set<Excursion> excursions) {
        this(customer, trip, reserveDate);
        this.excursions = excursions;
    }

    /**
     * Non-null fields constructor.
     */
    public Reservation(Customer customer, Trip trip, LocalDate reserveDate) {
        this.customer = customer;
        customer.addReservation(this);
        this.trip = trip;
        trip.addReservation(this);
        this.reserveDate = reserveDate;
    }

    /**
     * ID getter.
     *
     * @return ID
     */
    public Long getId() {
        return id;
    }

    /**
     * ID setter, should be used by the DB.
     *
     * @param id of the reservation
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return customer who made the reservation
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Customer setter.
     *
     * @param customer who made the reservation
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
        customer.addReservation(this);
    }

    /**
     * @return trip related to this reservation
     */
    public Trip getTrip() {
        return trip;
    }

    /**
     * Related trip setter.
     *
     * @param trip related to this reservation
     */
    public void setTrip(Trip trip) {
        this.trip = trip;
        trip.addReservation(this);
    }

    /**
     * @return Date the reservation was made
     */
    public LocalDate getReserveDate() {
        return reserveDate;
    }

    /**
     * Reservation date setter.
     *
     * @param reserveDate the Date this reservation was made
     */
    public void setReserveDate(LocalDate reserveDate) {
        this.reserveDate = reserveDate;
    }

    /**
     * @return excursions booked with this reservation
     */
    public Collection<Excursion> getExcursions() {
        return Collections.unmodifiableCollection(excursions);
    }

    /**
     * Booked excursions setter.
     *
     * @param excursions booked with this reservation
     */
    public void setExcursions(Collection<Excursion> excursions) {
        this.excursions = excursions;
    }

    /**
     * Adds excursion for this reservation.
     *
     * @param excursion to be added
     */
    public void addExcursion(Excursion excursion) {
        excursions.add(excursion);
    }

    /**
     * Removes one of the excursions from this reservations
     *
     * @param excursion to be removed
     */
    public void removeExcursion(Excursion excursion) {
        excursions.remove(excursion);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation)) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(getCustomer(), that.getCustomer()) &&
                Objects.equals(getTrip(), that.getTrip());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCustomer(), getTrip());
    }
}
