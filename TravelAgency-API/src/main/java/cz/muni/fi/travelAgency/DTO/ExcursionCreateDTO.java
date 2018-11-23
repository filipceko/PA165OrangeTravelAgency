package cz.muni.fi.travelAgency.DTO;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
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
    private Long tripId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDestination() {
        return destination;
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

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExcursionCreateDTO)) {
            return false;
        }
        ExcursionCreateDTO that = (ExcursionCreateDTO) o;
        return Objects.equals(getDescription(), that.getDescription())
                && Objects.equals(getPrice(), that.getPrice())
                && Objects.equals(getDestination(), that.getDestination())
                && Objects.equals(getTripId(), that.getTripId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDestination(), getPrice(), getDescription(), getTripId());
    }

}
