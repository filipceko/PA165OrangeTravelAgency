package cz.muni.fi.travelAgency.DTO;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * Trip Data Transfer Object representing all the information about a single trip
 *
 * @author Rithy Ly
 */
public class TripDTO {

    /**
     * ID of the trip
     */
    private Long id;

    /**
     * Staring Date of the trip was made;
     */
    @NotNull
    private LocalDate fromDate;

    /**
     * Ending Date of the trip was made;
     */
    @NotNull
    private LocalDate toDate;

    /**
     * Description of the trip was made;
     */
    @Size(min = 3, max = 500)
    private String destination;

    /**
     * Capacity of the trip was made;
     */
    @Min(1)
    @Max(2000)
    private int capacity;

    /**
     * Price of the trip was made;
     */
    @Min(0)
    private Double price;

    /**
     * Excursions of the trip was made;
     */
    private Collection<ExcursionDTO> excursions = new HashSet<>();

    /**
     * Reservations of the trip was made;
     */
    private Collection<ReservationDTO> reservations = new HashSet<>();

    /**
     * Simple non-parametric constructor
     */
    public TripDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Collection<ExcursionDTO> getExcursions() {
        return excursions;
    }

    public void setExcursions(Collection<ExcursionDTO> excursions) {
        this.excursions = excursions;
    }

    public Collection<ReservationDTO> getReservations() {
        return reservations;
    }

    public void setReservations(Collection<ReservationDTO> reservations) {
        this.reservations = reservations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TripDTO)) return false;
        TripDTO tripDTO = (TripDTO) o;
        return getCapacity() == tripDTO.getCapacity() &&
                Objects.equals(getFromDate(), tripDTO.getFromDate()) &&
                Objects.equals(getDestination(), tripDTO.getDestination()) &&
                Objects.equals(getPrice(), tripDTO.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFromDate(), getDestination(), getCapacity(), getPrice());
    }

    @Override
    public String toString() {
        return "TripDTO{" +
                "id=" + id +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", destination='" + destination + '\'' +
                ", capacity=" + capacity +
                ", price=" + price +
                '}';
    }
}
