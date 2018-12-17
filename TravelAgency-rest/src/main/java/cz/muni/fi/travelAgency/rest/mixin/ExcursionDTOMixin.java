package cz.muni.fi.travelAgency.rest.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "durationString", "durationMinutes" })
public class ExcursionDTOMixin {
}
