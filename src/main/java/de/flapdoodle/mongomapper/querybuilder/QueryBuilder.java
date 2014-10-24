package de.flapdoodle.mongomapper.querybuilder;

import de.flapdoodle.mongomapper.query.Property;
import de.flapdoodle.mongomapper.query.QueryableProperty;

public class QueryBuilder {

    public <T, P extends Property<?, ?>> QueryBuilder is(QueryableProperty<T, P> property, T value) {
        return this;
    }
}
