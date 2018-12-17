package cz.muni.fi.travelAgency.rest.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Simona Raucinova
 */
@JsonIgnoreProperties({ "excursions", "reservations" })
public class TripDTOMixin {
}
