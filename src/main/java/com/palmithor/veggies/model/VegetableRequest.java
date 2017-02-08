package com.palmithor.veggies.model;

import com.google.gson.annotations.SerializedName;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

/**
 * Request for creating a vegetable
 *
 * @author palmithor
 * @since 8.2.2017.
 */
public class VegetableRequest {

    @Length(min = 1, max = 255)
    @NotNull
    @SerializedName("name") private final String name;

    @NotNull
    @Digits(fraction = 2, integer = 6)
    @SerializedName("price") private final Double price;

    public VegetableRequest() {
        this.name = null;
        this.price = null;
    }

    public VegetableRequest(final String name, final Double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }
}
