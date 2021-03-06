package cz.muni.fi.travelAgency.DTO;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * Data Transfer some sub-set of trips and excursions.
 *
 * @author Filip Cekovsky
 */
public class CheapTravelDTO {

    /**
     * Collection of trips
     */
    private Collection<TripDTO> trips = new HashSet<>();

    /**
     * Collection of excursions, all excursions should be related to a trip from trips
     */
    private Collection<ExcursionDTO> excursions = new HashSet<>();

    public CheapTravelDTO() {
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
