package de.flapdoodle.mongomapper.query;

import de.flapdoodle.mongomapper.AttributeMapper;

public abstract class QueryProperties {

    private QueryProperties() {
        // no instance
    }
    
    public static <T> SimpleProperty<T> with(AttributeMapper<T> mapper) {
        return new SimpleProperty<T>(mapper);
    }
    
    public static <T, P extends QueryProperty<?, ? extends QueryProperty<?,?>>> CascadedProperty<T, P> with(P parent, AttributeMapper<T> mapper) {
        return new CascadedProperty<T,P>(parent, mapper);
    }
    
    
}
