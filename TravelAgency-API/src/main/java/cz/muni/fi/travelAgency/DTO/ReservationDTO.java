package cz.muni.fi.travelAgency.DTO;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * Reservation Data Transfer Object representing all the information about a single reservation
 *
 * @author Filip Cekovsky
 */
public class ReservationDTO {

    /**
     * ID of the reservation
     */
    private Long id;

    /**
     * Customer who made this reservation
     */
    @NotNull
    private CustomerDTO customer;

    /**
     * Booked trip by this reservation
     */
    @NotNull
    private TripDTO trip;

    /**
     * Excursions booked with this reservation
     */
    @NotNull
    private Collection<ExcursionDTO> excursions;

    /**
     * Date the reservation was made;
     */
    @NotNull
    private LocalDate reserveDate;

    public ReservationDTO() {
    }

    public ReservationDTO(@NotNull CustomerDTO customer, @NotNull TripDTO trip, @NotNull LocalDate reserveDate) {
        this.customer = customer;
        this.trip = trip;
        this.reserveDate = reserveDate;
    }

    public ReservationDTO(@NotNull CustomerDTO customer, @NotNull TripDTO trip, @NotNull Set<ExcursionDTO> excursions, @NotNull LocalDate reserveDate) {
        this.customer = customer;
        this.trip = trip;
        this.excursions = excursions;
        this.reserveDate = reserveDate;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public TripDTO getTrip() {
        return trip;
    }

    public void setTrip(TripDTO trip) {
        this.trip = trip;
    }

    public Collection<ExcursionDTO> getExcursions() {
        return excursions;
    }

    public void setExcursions(Collection<ExcursionDTO> excursions) {
        this.excursions = excursions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(@NotNull LocalDate reserveDate) {
        this.reserveDate = reserveDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationDTO)) return false;
        ReservationDTO that = (ReservationDTO) o;
        return getCustomer().equals(that.getCustomer()) &&
                getTrip().equals(that.getTrip());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCustomer(), getTrip());
    }
}
