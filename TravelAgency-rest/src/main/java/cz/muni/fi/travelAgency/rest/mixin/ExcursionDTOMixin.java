package cz.muni.fi.travelAgency.rest.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Simona Raucinova
 */
@JsonIgnoreProperties({ "durationString", "durationMinutes" })
public class ExcursionDTOMixin {
}
