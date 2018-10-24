package cz.muni.fi.travelAgency.entities;

import javax.persistence.*;
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

    /** Unique ID set by the DB */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Customer who made this reservation */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    /** Trip this reservation is related to */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRIP_ID")
    private Trip trip;

    /** Date this reservation was made on */
    @Temporal(value = TemporalType.DATE)
    private java.util.Date reserveDate;

    /** Excursions reserved with this trip */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "RESERVATION_EXCURSIONS", joinColumns = @JoinColumn(name = "EXCURSION_ID"))
    private Set<Excursion> excursions = new HashSet<>();

    /**
     * Basic non-parametric constructor.
     */
    public Reservation(){}

    /**
     * ID getter
     * @return ID
     */
    public Long getId() {
        return id;
    }

    /**
     * ID setter, should be used by the DB.
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
     * @param trip related to this reservation
     */
    public void setTrip(Trip trip) {
        this.trip = trip;
        trip.addReservation(this);
    }

    /**
     * @return Date the reservation was made
     */
    public Date getReserveDate() {
        return reserveDate;
    }

    /**
     * Reservation date setter.
     * @param reserveDate the Date this reservation was made
     */
    public void setReserveDate(Date reserveDate) {
        this.reserveDate = reserveDate;
    }

    /**
     * @return excursions booked with this reservation
     */
    public Set<Excursion> getExcursions() {
        return Collections.unmodifiableSet(excursions);
    }

    /**
     * Booked excursions setter.
     * @param excursions booked with this reservation
     */
    public void setExcursions(Set<Excursion> excursions) {
        this.excursions = excursions;
    }

    /**
     * Adds excursion for this reservation.
     * @param excursion to be added
     */
    public void addExcursion(Excursion excursion) {
        excursions.add(excursion);
    }

    /**
     * Removes one of the excursions from this reservations
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
