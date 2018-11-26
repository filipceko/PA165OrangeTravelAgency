package cz.muni.fi.travelAgency.DTO;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

/**
 * Reservation Data Transfer Object representing all the information about a single reservation
 *
 * @author Filip Cekovsky
 */
public class ReservationDTO extends ReservationCreateDTO {

    /**
     * ID of the reservation
     */
    private Long id;

    /**
     * Date the reservation was made;
     */
    @NotNull
    private LocalDate reserveDate;

    /**
     * Simple non-parametric constructor
     */
    public ReservationDTO() {
    }

    /**
     * constructor based on {@link ReservationCreateDTO} as parent object
     *
     * @param parent      ReservationCreateDTO that was used to create this reservation
     * @param id          this reservation obtained
     * @param reserveDate date the reservation was created
     */
    public ReservationDTO(ReservationCreateDTO parent, Long id, @NotNull LocalDate reserveDate) {
        super(parent.getCustomer(), parent.getTrip(), parent.getExcursions());
        this.id = id;
        this.reserveDate = reserveDate;
    }

    /**
     * Constructor without excursions and with ID
     *
     * @param customer    who made this reservation
     * @param trip        the reservation is for
     * @param id          of this reservation
     * @param reserveDate the date this reservation was made
     */
    public ReservationDTO(@NotNull CustomerDTO customer, @NotNull TripDTO trip, Long id, @NotNull LocalDate reserveDate) {
        super(customer, trip);
        this.id = id;
        this.reserveDate = reserveDate;
    }

    /**
     * Constructor with excursions and ID
     *
     * @param customer    who made this reservation
     * @param trip        the reservation is for
     * @param excursions  booked with this reservation
     * @param id          of this reservation
     * @param reserveDate the date this reservation was made
     */
    public ReservationDTO(@NotNull CustomerDTO customer, @NotNull TripDTO trip, @NotNull Set<ExcursionDTO> excursions, Long id, @NotNull LocalDate reserveDate) {
        super(customer, trip, excursions);
        this.id = id;
        this.reserveDate = reserveDate;
    }

    /**
     * Constructor without excursions and ID
     *
     * @param customer    who made the reservation
     * @param trip        the reservation is for
     * @param reserveDate tha date this reservation was made
     */
    public ReservationDTO(@NotNull CustomerDTO customer, @NotNull TripDTO trip, @NotNull LocalDate reserveDate) {
        super(customer, trip);
        this.reserveDate = reserveDate;
    }

    /**
     * Constructor with excursions and without ID
     *
     * @param customer    who made the reservation
     * @param trip        the reservation is for
     * @param excursions  booked with this reservation
     * @param reserveDate the date this reservation was made
     */
    public ReservationDTO(@NotNull CustomerDTO customer, @NotNull TripDTO trip, @NotNull Set<ExcursionDTO> excursions, @NotNull LocalDate reserveDate) {
        super(customer, trip, excursions);
        this.reserveDate = reserveDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(@NotNull LocalDate reserveDate) {
        this.reserveDate = reserveDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationDTO)) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
