package cz.muni.fi.travelAgency.DTO;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO class of Excursion.
 * @author Rajivv
 */
public class ExcursionDTO {

    private Long id;
    private String description;
    private String destination;
    private BigDecimal price;
    private LocalDate excursionDate;
    private Duration excursionDuration;
    private Long tripId;

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

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExcursionDTO)) {
            return false;
        }
        ExcursionDTO that = (ExcursionDTO) o;
        return Objects.equals(getDestination(), that.getDestination())
                && Objects.equals(getPrice(), that.getPrice())
                && Objects.equals(getExcursionDate(), that.getExcursionDate())
                && Objects.equals(getExcursionDuration(), that.getExcursionDuration())
                && Objects.equals(getTripId(), that.getTripId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDestination(), getPrice(), getExcursionDate(), getExcursionDuration(), getTripId());
    }
}
