package cz.muni.fi.travelAgency.DTO;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;

public class ExcursionEditDTO {

    /**
     * ID of the excursion
     */
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
     * Duration of the excursion
     */
    @NotNull
    private Duration excursionDuration;

    /**
     * Duration represented as number of minutes.
     */
    private int durationMinutes;

    /**
     * Trip the excursion was made for.
     */
    @NotNull
    private Long tripId;

    public ExcursionEditDTO() {
    }

    public ExcursionEditDTO(@NotNull @Size(min = 3, max = 500) String description, @NotNull @Size(min = 3, max = 500) String destination, @NotNull @Min(0) Double price, @NotNull LocalDate excursionDate, @NotNull Duration excursionDuration, int durationMinutes, @NotNull Long tripId) {
        this.description = description;
        this.destination = destination;
        this.price = price;
        this.excursionDate = excursionDate;
        this.excursionDuration = excursionDuration;
        this.durationMinutes = durationMinutes;
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

    public Duration getExcursionDuration() {
        return excursionDuration;
    }

    public void setExcursionDuration(Duration excursionDuration) {
        this.excursionDuration = excursionDuration;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
        this.excursionDuration = Duration.ofMinutes(durationMinutes);
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
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
        if (!(o instanceof ExcursionEditDTO)) return false;
        ExcursionEditDTO excursion = (ExcursionEditDTO) o;
        return Objects.equals(getDestination(), excursion.getDestination()) &&
                Objects.equals(getPrice(), excursion.getPrice()) &&
                Objects.equals(getExcursionDate(), excursion.getExcursionDate()) &&
                Objects.equals(getExcursionDuration(), excursion.getExcursionDuration()) &&
                Objects.equals(getTripId(), excursion.getTripId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDestination(), getPrice(), getExcursionDate(), getExcursionDuration(), getTripId());
    }
}
