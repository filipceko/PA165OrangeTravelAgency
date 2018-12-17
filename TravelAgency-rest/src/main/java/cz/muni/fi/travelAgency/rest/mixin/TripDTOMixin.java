package cz.muni.fi.travelAgency.rest.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "excursions", "reservations" })
public class TripDTOMixin {
}
