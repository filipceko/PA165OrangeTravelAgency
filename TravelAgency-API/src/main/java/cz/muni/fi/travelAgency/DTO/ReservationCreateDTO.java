package cz.muni.fi.travelAgency.DTO;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Data Transfer Object for Reservation Creation.
 */
public class ReservationCreateDTO {
    /**
     * Customer who created the reservation.
     */
    @NotNull
    private CustomerDTO customer;

    /**
     * Trip the reservation was made for.
     */
    @NotNull
    private TripDTO trip;

    /**
     * Excursions booked for the reservation.
     */
    @NotNull
    private Set<ExcursionDTO> excursions = new HashSet<>();

    /**
     * Simple non-parametric constructor.
     */
    public ReservationCreateDTO() {
    }

    /**
     * Constructor without excursions.
     *
     * @param customer who created the reservation
     * @param trip     the reservation was made for
     */
    public ReservationCreateDTO(@NotNull CustomerDTO customer, @NotNull TripDTO trip) {
        this.customer = customer;
        this.trip = trip;
    }

    /**
     * Constructor with excursions.
     *
     * @param customer   who created the reservation
     * @param trip       the reservation was made for
     * @param excursions booked with this reservation
     */
    public ReservationCreateDTO(@NotNull CustomerDTO customer, @NotNull TripDTO trip, @NotNull Set<ExcursionDTO> excursions) {
        this(customer, trip);
        this.excursions = excursions;
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

    /**
     * Excursions getter.
     *
     * @return Set of selected excursions
     */
    public Set<ExcursionDTO> getExcursions() {
        return excursions;
    }

    /**
     * Sets excursions for this reservation.
     *
     * @param excursions Set of excursions selected
     */
    public void setExcursions(@NotNull Set<ExcursionDTO> excursions) {
        this.excursions = excursions;
    }

    /**
     * Adds given excursion to the excursions already included.
     *
     * @param excursion to be added
     */
    public void addExcursion(ExcursionDTO excursion) {
        excursions.add(excursion);
    }

    /**
     * Removes an excursion from the reservation.
     *
     * @param excursion to be removed
     */
    public void removeExcursion(ExcursionDTO excursion) {
        excursions.remove(excursion);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationCreateDTO)) return false;
        ReservationCreateDTO that = (ReservationCreateDTO) o;
        return Objects.equals(getCustomer(), that.getCustomer()) &&
                Objects.equals(getTrip(), that.getTrip());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCustomer(), getTrip());
    }
}
