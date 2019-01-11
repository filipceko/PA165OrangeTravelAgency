package cz.muni.fi.travelAgency.DTO;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Data Transfer Object for Reservation Creation.
 *
 * @author Filip Cekovsky
 */
public class ReservationCreateDTO {
    /**
     * Trip the reservation was made for.
     */
    @NotNull
    private Long tripId;

    /**
     * Excursions booked for the reservation.
     */
    @NotNull
    private List<String> excursions = new LinkedList<>();

    public ReservationCreateDTO() {
    }

    public ReservationCreateDTO(@NotNull Long tripId, @NotNull Collection<ExcursionDTO> excursions) {
        this.tripId = tripId;
        for (ExcursionDTO excursionDTO : excursions) {
            this.excursions.add(excursionDTO.getDestination());
        }
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public List<String> getExcursions() {
        return excursions;
    }

    public void setExcursions(List<String> excursions) {
        this.excursions = excursions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationCreateDTO)) return false;
        ReservationCreateDTO that = (ReservationCreateDTO) o;
        return Objects.equals(getTripId(), that.getTripId()) &&
                Objects.equals(getExcursions(), that.getExcursions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTripId(), getExcursions());
    }
}
