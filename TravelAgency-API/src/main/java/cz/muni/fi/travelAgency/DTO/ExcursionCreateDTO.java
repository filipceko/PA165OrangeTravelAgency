package cz.muni.fi.travelAgency.DTO;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import cz.muni.fi.travelAgency.enums.Currency;
import java.util.Objects;

/**
 *
 * @author Rajivv
 */
public class ExcursionCreateDTO {

    @NotNull
    @Size(min = 3, max = 500)
    private String description;

    @NotNull
    @Size(min = 3, max = 500)
    private String destination;

    @NotNull
    @Min(0)
    private BigDecimal price;

    @NotNull
    private Currency currency;

    @NotNull
    private TripDTO trip;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDestination() {
        return description;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public TripDTO getTrip() {
        return trip;
    }

    public void setTrip(TripDTO trip) {
        this.trip = trip;
       
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExcursionCreateDTO)) {
            return false;
        }
        ExcursionCreateDTO excursion = (ExcursionCreateDTO) o;
        return Objects.equals(getDescription(), excursion.getDescription())
                && Objects.equals(getPrice(), excursion.getPrice())
                && Objects.equals(getCurrency(), excursion.getCurrency())
                && Objects.equals(getDestination(), excursion.getDestination())
                && Objects.equals(getTrip(), excursion.getTrip());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDestination(), getPrice(), getCurrency(), getDescription(), getTrip());
    }

}
