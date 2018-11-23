package cz.muni.fi.travelAgency.DTO;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author Rajivv
 */
public class ExcursionDTO {

    private Long id;
    private String description;
    private String destination;
    private BigDecimal price;
    private LocalDate excursionDate;
    private Duration excursionDuration;
    private TripDTO trip;

    public ExcursionDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getExcursionDate() {
        return excursionDate;
    }

    public void setExcursionDate(LocalDate excursionDate) {
        this.excursionDate = excursionDate;
    }

    public Duration getExcursionDuration() {
        return excursionDuration;
    }

    public void setExcursionDuration(Duration excursionDuration) {
        this.excursionDuration = excursionDuration;
    }

    public TripDTO getTrip() {
        return trip;
    }

    public void setTrip(TripDTO trip) {
        this.trip = trip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExcursionDTO)) {
            return false;
        }
        ExcursionDTO excursion = (ExcursionDTO) o;
        return Objects.equals(getDestination(), excursion.getDestination())
                && Objects.equals(getPrice(), excursion.getPrice())
                && Objects.equals(getExcursionDate(), excursion.getExcursionDate())
                && Objects.equals(getExcursionDuration(), excursion.getExcursionDuration())
                && Objects.equals(getTrip(), excursion.getTrip());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDestination(), getPrice(), getExcursionDate(), getExcursionDuration(), getTrip());
    }
}
