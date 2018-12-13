package cz.muni.fi.travelAgency.DTO;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Data Transfer Object for Excursion Creation.
 *
 * @author Rajivv
 */
public class ExcursionCreateDTO {

    private Long id;
    /**
     * Description of the excursion
     */
    @NotNull
    @Size(min = 3, max = 500)
    private String description;

    /**
     * Destination of the excursion
     */
    @NotNull
    @Size(min = 3, max = 500)
    private String destination;

    /**
     * Price of the excursion
     */
    @NotNull
    @Min(0)
    private Double price;

    /**
     * Date of the excursion
     */
    @NotNull
    private LocalDate excursionDate;

    /**
     * Pretty print representation of duration.
     */
    private int durationMinutes;

    /**
     * Trip the excursion was made for.
     */
    @NotNull
    private Long tripId;

    /**
     * Simple non-parametric constructor.
     */
    public ExcursionCreateDTO() {
    }

    /**
     * Parametric constructor with trip.
     *
     * @param description       of this excursion
     * @param destination       of this excursion
     * @param price             of this excursion
     * @param excursionDate     of this excursion
     * @param excursionDuration of this excursion
     * @param tripId            id of trip the excursion was made for
     */

    public ExcursionCreateDTO(@NotNull String description, @NotNull String destination, @NotNull Double price, @NotNull LocalDate excursionDate, @NotNull Duration excursionDuration, @NotNull Long tripId) {
        this.description = description;
        this.destination = destination;
        this.price = price;
        this.excursionDate = excursionDate;
        this.tripId = tripId;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getExcursionDate() {
        return excursionDate;
    }

    public void setExcursionDate(LocalDate excursionDate) {
        this.excursionDate = excursionDate;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExcursionCreateDTO)) return false;
        ExcursionCreateDTO excursion = (ExcursionCreateDTO) o;
        return Objects.equals(getDestination(), excursion.getDestination()) &&
                Objects.equals(getPrice(), excursion.getPrice()) &&
                Objects.equals(getExcursionDate(), excursion.getExcursionDate()) &&
                Objects.equals(getDurationMinutes(), excursion.getDurationMinutes()) &&
                Objects.equals(getTripId(), excursion.getTripId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDestination(), getPrice(), getExcursionDate(), getDurationMinutes(), getTripId());
    }
}
