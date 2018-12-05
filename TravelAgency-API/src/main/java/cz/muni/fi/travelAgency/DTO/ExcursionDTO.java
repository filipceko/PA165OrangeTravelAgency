package cz.muni.fi.travelAgency.DTO;

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDate;

/**
 * Excursion Data Transfer Object representing all the information about a
 * single excursion
 *
 * @author Rajivv
 */
public class ExcursionDTO extends ExcursionCreateDTO {

    /**
     * ID of the excursion
     */
    private Long id;

    /**
     * Simple non-parametric constructor
     */
    public ExcursionDTO() {
    }

    /**
     * constructor based on {@link ExcursionCreateDTO} as parent object
     *
     * @param parent ExcursionCreateDTO that was used to create this excursion
     * @param id     this excursion obtained
     */
    public ExcursionDTO(ExcursionCreateDTO parent, Long id) {
        super(parent.getDescription(), parent.getDestination(), parent.getPrice(), parent.getExcursionDate(), parent.getExcursionDuration(), parent.getTrip());
        this.id = id;
    }

    /**
     * Parametric constructor with trip and with ID
     *
     * @param description       of this excursion
     * @param destination       of this excursion
     * @param price             of this excursion
     * @param trip              the excursion is for
     * @param excursionDate     of this excursion
     * @param excursionDuration of this excursion
     * @param id                of this excursion
     */
    public ExcursionDTO(@NotNull String description, @NotNull String destination, @NotNull Double price, @NotNull LocalDate excursionDate, @NotNull Duration excursionDuration, @NotNull TripDTO trip, Long id) {
        super(description, destination, price, excursionDate, excursionDuration, trip);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
