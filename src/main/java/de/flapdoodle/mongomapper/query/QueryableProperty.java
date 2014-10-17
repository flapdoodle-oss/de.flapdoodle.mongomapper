package de.flapdoodle.mongomapper.query;

import de.flapdoodle.mongomapper.AttributeMapper;


public interface QueryableProperty<T,P extends Property<?, ?>> extends Property<T, P> {
    
    AttributeMapper<T> mapper();
}
