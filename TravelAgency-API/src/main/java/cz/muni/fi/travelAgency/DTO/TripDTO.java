package cz.muni.fi.travelAgency.DTO;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class TripDTO {

    private Long id;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String destination;
    private int capacity;
    private Double price;
    private Set<ExcursionDTO> excursions = new HashSet<>();
    private Set<ReservationDTO> reservations = new HashSet<>();

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

    public Set<ExcursionDTO> getExcursions() {
        return excursions;
    }

    public void setExcursions(Set<ExcursionDTO> excursions) {
        this.excursions = excursions;
    }

    public Set<ReservationDTO> getReservations() {
        return reservations;
    }

    public void setReservations(Set<ReservationDTO> reservations) {
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
