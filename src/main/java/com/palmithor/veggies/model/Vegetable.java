package com.palmithor.veggies.model;

import com.palmithor.veggies.entity.VegetableEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author palmithor
 * @since 8.2.2017.
 */
public class Vegetable extends BaseModel {

    private final String name;
    private final BigDecimal price;

    public Vegetable() {
        super();
        this.name = null;
        this.price = null;
    }

    public Vegetable(final Long id, final Date updated, final Date created, final String name, final BigDecimal price) {
        super(id, updated, created);
        this.name = name;
        this.price = price;
    }

    public Vegetable(final VegetableEntity vegetableEntity) {
        super(vegetableEntity.getId(), vegetableEntity.getUpdated(), vegetableEntity.getCreated());
        this.name = vegetableEntity.getName();
        this.price = vegetableEntity.getPrice();
    }
}
