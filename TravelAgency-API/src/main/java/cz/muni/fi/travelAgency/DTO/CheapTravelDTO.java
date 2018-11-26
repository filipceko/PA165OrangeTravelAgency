package cz.muni.fi.travelAgency.DTO;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * Data Transfer some sub-set of trips and excursions.
 */
public class CheapTravelDTO {

    private Collection<TripDTO> trips = new HashSet<>();

    private Collection<ExcursionDTO> excursions = new HashSet<>();

    public CheapTravelDTO() {
    }

    public CheapTravelDTO(Collection<TripDTO> trips, Collection<ExcursionDTO> excursions) {
        if (trips != null) {
            this.trips = trips;
            if (excursions != null) {
                this.excursions = excursions;
            }
        }
    }

    public Collection<TripDTO> getTrips() {
        return trips;
    }

    public void setTrips(Collection<TripDTO> trips) {
        this.trips = trips;
    }

    public Collection<ExcursionDTO> getExcursions() {
        return excursions;
    }

    public void setExcursions(Collection<ExcursionDTO> excursions) {
        this.excursions = excursions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CheapTravelDTO)) return false;
        CheapTravelDTO that = (CheapTravelDTO) o;
        return Objects.equals(getTrips(), that.getTrips()) &&
                Objects.equals(getExcursions(), that.getExcursions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTrips(), getExcursions());
    }
}
