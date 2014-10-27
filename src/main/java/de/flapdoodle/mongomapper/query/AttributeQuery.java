package de.flapdoodle.mongomapper.query;

import de.flapdoodle.mongomapper.Attr;

public abstract class AttributeQuery<T> implements Query {

    private final Attr<T> attribute;

    public AttributeQuery(Attr<T> attribute) {
        this.attribute = attribute;
    }

    public Attr<T> attribute() {
        return this.attribute;
    }

}
