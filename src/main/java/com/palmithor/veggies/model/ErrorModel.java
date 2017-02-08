package com.palmithor.veggies.model;

/**
 * Really simple model for defining errors
 *
 * @author palmithor
 * @since 8.2.2017.
 */
public class ErrorModel {

    private final String message;

    public ErrorModel() {
        this.message = null;
    }

    public ErrorModel(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
