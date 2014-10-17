package de.flapdoodle.mongomapper.query;

import de.flapdoodle.mongomapper.AttributeMapper;

public interface PropertyNameAndMapper<T,P extends Property<?, ?>> extends Property<T,P> {
    
    public AttributeMapper<T> mapper();

}
