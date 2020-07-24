package pl.Korty.Korty.model.entities;

import javax.persistence.*;

@Entity
@Table(name = "squash_courts")
public class Squash_CourtsEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private Long id_address;

    @Column
    private Integer fields_num;

    public Squash_CourtsEntities() {

    }
    public Squash_CourtsEntities(Long id, Long id_address, Integer fields_num) {
        this.id = id;
        this.id_address = id_address;
        this.fields_num = fields_num;
    }

    public Long getId() {
        return id;
    }

    public Long getId_address() {
        return id_address;
    }

    public Integer getFields_num() {
        return fields_num;
    }
}
