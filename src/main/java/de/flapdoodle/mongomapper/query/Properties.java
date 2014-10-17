package de.flapdoodle.mongomapper.query;

import de.flapdoodle.mongomapper.AttributeMapper;

public abstract class Properties {

    private Properties() {
        // no instance
    }
    
    public static <T> Property<T, VoidProperty> with(AttributeMapper<T> mapper) {
        return new CascadedProperty<T, VoidProperty>(mapper);
    }
    
    public static <T> QueryableProperty<T, VoidProperty> queryable(AttributeMapper<T> mapper) {
        return new QueryableCascadedProperty<T, VoidProperty>(mapper);
    }
    
    public static <T, P extends Property<?, ? extends Property<?,?>>> CascadedProperty<T, P> with(P parent, AttributeMapper<T> mapper) {
        return new CascadedProperty<T,P>(parent, mapper);
    }
    
    public static <T, P extends Property<?, ? extends Property<?,?>>> QueryableProperty<T, P> queryable(P parent, AttributeMapper<T> mapper) {
        return new QueryableCascadedProperty<T,P>(parent, mapper);
    }
    
}
