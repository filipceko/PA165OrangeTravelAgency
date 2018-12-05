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
     * Duration of the excursion
     */
    @NotNull
    private Duration excursionDuration;

    /**
     * Trip the excursion was made for.
     */
    @NotNull
    private TripDTO trip;

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
     * @param trip              the excursion was made for
     */

    public ExcursionCreateDTO(@NotNull String description, @NotNull String destination, @NotNull Double price, @NotNull LocalDate excursionDate, @NotNull Duration excursionDuration, @NotNull TripDTO trip) {
        this.description = description;
        this.destination = destination;
        this.price = price;
        this.excursionDate = excursionDate;
        this.excursionDuration = excursionDuration;
        this.trip = trip;
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
        if (!(o instanceof ExcursionCreateDTO)) {
            return false;
        }
        ExcursionCreateDTO that = (ExcursionCreateDTO) o;
        return Objects.equals(getDescription(), that.getDescription())
                && Objects.equals(getPrice(), that.getPrice())
                && Objects.equals(getDestination(), that.getDestination())
                && Objects.equals(getTrip(), that.getTrip());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDestination(), getPrice(), getDescription(), getTrip());
    }

}
