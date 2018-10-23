package cz.muni.fi.travelAgency.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "RESERVATION")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Trip trip;

    @Temporal(value = TemporalType.DATE)
    private java.util.Date reserveDate;


    public void Reservation(){};

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        customer.addReservation(this);
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Date getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(Date reserveDate) {
        this.reserveDate = reserveDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation)) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(getCustomer(), that.getCustomer()) &&
                Objects.equals(getTrip(), that.getTrip()) &&
                Objects.equals(getReserveDate(), that.getReserveDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCustomer(), getTrip(), getReserveDate());
    }
}
