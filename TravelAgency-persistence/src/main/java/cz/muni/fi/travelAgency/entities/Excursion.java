package cz.muni.fi.travelAgency.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class Excursion {

    @Id
    private Long id;


    public Excursion(){}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}