package com.palmithor.veggies.model;

import java.util.Date;

/**
 * Base model to allow setting all common values in one place
 *
 * @author palmithor
 * @since 8.2.2017.
 */
abstract class BaseModel {

    private final Long id;
    private final Date updated;
    private final Date created;

    BaseModel() {
        this.id = null;
        this.updated = null;
        this.created = null;
    }

    BaseModel(final Long id, final Date updated, final Date created) {
        this.id = id;
        this.updated = updated;
        this.created = created;
    }
}
