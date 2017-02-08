package com.palmithor.veggies.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * An entity to represent vegetable database entity
 *
 * @author palmithor
 * @since 8.2.2017.
 */
@Entity(name = "vegetables")
public class VegetableEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vegetable_id_seq")
    @SequenceGenerator(name="vegetable_id_seq", sequenceName = "vegetable_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public VegetableEntity() {
    }

    public VegetableEntity(final String name, final BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }
}
