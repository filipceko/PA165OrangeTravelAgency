package cz.muni.fi.travelAgency.entities;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.DecimalMin;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;



/**
 *
 * Representing Excursion Entity
 *
 * @author xrajivv
 */
@Entity
@Table(name = "EXCURSION")
public class Excursion {

    /**
     * Unique ID set by the DB
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Description of the Excursion
     */
    @NotNull
    @Column(nullable = false)
    private String description;
    /**
     * Destination of the Excursion
     */
    @NotNull
    @Column(nullable = false)
    private String destination;
    /**
     * Price of the Excursion
     */
    @DecimalMin("0.0")
    @NotNull
    @Column(nullable = false)
    private BigDecimal price;
    /**
     * Date of the Excursion
     */
    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date excursionDate;
    /**
     * Duration of the Excursion
     */
    @NotNull
    @Column(nullable = false)
    private Duration excursionDuration;
    
    @ManyToOne
    private Set<Trip> trips = new HashSet<Trip>();

    public Excursion() {
    }

    /**
     * @return unique ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID, should be used primary by the DB
     *
     * @param id of the entity
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return Description of the excursion
     */
    public String getDescription() {
        return description;
    }

    /**
     * Name setter
     *
     * @param description of the excursion
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return Destination of the excursion
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Destination setter
     *
     * @param destination of the excursion
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * @return price of the excursion
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Price setter
     *
     * @param price non-null price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return date of the excursion
     */
    public Date getExcursionDate() {
        return excursionDate;
    }

    /**
     * Date setter
     *
     * @param excursionDate non-null excursionDate
     */
    public void setExcursionDate(Date excursionDate) {
        this.excursionDate = excursionDate;
    }

    /**
     * @return duration of the excursion
     */
    public Duration getExcursionDuration() {
        return excursionDuration;
    }

    /**
     * Duration setter
     *
     * @param excursionDuration non-null excursionDuration
     */
    public void setExcursionDuration(Duration excursionDuration) {
        this.excursionDuration = excursionDuration;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Excursion)) {
            return false;
        }
        Excursion excursion = (Excursion) obj;
        return Objects.equals(getDescription(), excursion.getDescription())
                && Objects.equals(getDestination(), excursion.getDestination())
                && Objects.equals(getPrice(), excursion.getPrice())
                && Objects.equals(getExcursionDate(), excursion.getExcursionDate())
                && Objects.equals(getExcursionDuration(), excursion.getExcursionDuration());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDescription(), getDestination(), getPrice(), getExcursionDate(), getExcursionDuration());
    }
}
