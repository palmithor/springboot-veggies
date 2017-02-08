package com.palmithor.veggies.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author palmithor
 * @since 8.2.2017.
 */
public class DeleteResult {

    @SerializedName("deleted_id") private final Long deletedId;

    public DeleteResult() {
        this.deletedId = null;
    }

    public DeleteResult(final Long deletedId) {
        this.deletedId = deletedId;
    }
}
