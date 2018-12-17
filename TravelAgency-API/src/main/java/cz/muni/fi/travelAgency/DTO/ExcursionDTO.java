package cz.muni.fi.travelAgency.DTO;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Excursion Data Transfer Object representing all the information about a
 * single excursion
 *
 * @author Rajivv, Simona Raucinova
 */
public class ExcursionDTO {

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
     * Pretty print representation of duration.
     */
    private String durationString;

    /**
     * Trip the excursion was made for.
     */
    private TripDTO trip;

    /**
     * Simple non-parametric constructor
     */
    public ExcursionDTO() {
    }

    /**
     * Parametric constructor with trip and with ID
     *
     * @param id                of this excursion
     * @param trip              the excursion is for
     * @param destination       of this excursion
     * @param excursionDate     of this excursion
     * @param excursionDuration of this excursion
     * @param price             of this excursion
     * @param description       of this excursion
     */
    public ExcursionDTO(Long id, @NotNull TripDTO trip, @NotNull String destination, @NotNull LocalDate excursionDate,
                        @NotNull Duration excursionDuration, @NotNull Double price, @NotNull String description) {
        this(trip, destination, excursionDate, excursionDuration, price, description);
        this.id = id;
    }

    /**
     * Parametric constructor with trip and without ID
     *
     * @param trip              the excursion is for
     * @param destination       of this excursion
     * @param excursionDate     of this excursion
     * @param excursionDuration of this excursion
     * @param price             of this excursion
     * @param description       of this excursion
     */
    public ExcursionDTO(TripDTO trip, @NotNull @Size(min = 3, max = 500) String destination, @NotNull LocalDate excursionDate,
                        @NotNull Duration excursionDuration, @NotNull @Min(0) Double price,
                        @NotNull @Size(min = 3, max = 500) String description) {
        this.description = description;
        this.destination = destination;
        this.price = price;
        this.excursionDate = excursionDate;
        this.excursionDuration = excursionDuration;
        this.trip = trip;
        this.durationString = formatDuration(excursionDuration);
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
        this.durationString = formatDuration(excursionDuration);
    }

    public String getDurationString() {
        return durationString;
    }

    public void setDurationString(String durationString) {
        this.durationString = durationString;

    }

    public TripDTO getTrip() {
        return trip;
    }

    public void setTrip(TripDTO trip) {
        this.trip = trip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExcursionDTO)) return false;
        ExcursionDTO excursion = (ExcursionDTO) o;
        return Objects.equals(getDestination(), excursion.getDestination()) &&
                Objects.equals(getPrice(), excursion.getPrice()) &&
                Objects.equals(getExcursionDate(), excursion.getExcursionDate()) &&
                Objects.equals(getExcursionDuration(), excursion.getExcursionDuration()) &&
                Objects.equals(getTrip(), excursion.getTrip());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDestination(), getPrice(), getExcursionDate(), getExcursionDuration(), getTrip());
    }

    private String formatDuration(Duration duration) {
        return duration.toString()
                .substring(2)
                .replaceAll("(\\d[HMS])(?!$)", "$1 ")
                .toLowerCase();
    }
}
