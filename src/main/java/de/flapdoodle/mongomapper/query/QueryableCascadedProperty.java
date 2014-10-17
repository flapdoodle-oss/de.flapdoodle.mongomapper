package de.flapdoodle.mongomapper.query;

import com.google.common.base.Optional;

import de.flapdoodle.mongomapper.AttributeMapper;

public class QueryableCascadedProperty<T, P extends Property<?, ? extends Property<?,?>>> extends CascadedProperty<T, P> implements QueryableProperty<T, P> {

    public QueryableCascadedProperty(AttributeMapper<T> mapper) {
        super(mapper);
    }
    
    public QueryableCascadedProperty(P parent, AttributeMapper<T> mapper) {
        super(parent, mapper);
    }

    public QueryableCascadedProperty(Optional<P> parent, AttributeMapper<T> mapper) {
        super(parent, mapper);
    }

    @Override
    public AttributeMapper<T> mapper() {
        return super.mapper();
    }
}
