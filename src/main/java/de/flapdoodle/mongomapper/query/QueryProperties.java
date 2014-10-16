package de.flapdoodle.mongomapper.query;

import de.flapdoodle.mongomapper.AttributeMapper;

public abstract class QueryProperties {

    private QueryProperties() {
        // no instance
    }
    
    public static <T> QueryProperty<T, Void> with(AttributeMapper<T> mapper) {
        return new SingleProperty<T>(mapper);
    }
    
    static class CascadedProperty<T, P> implements QueryProperty<T, P> {
        
    }
    
    static class SingleProperty<T> implements QueryProperty<T, Void> {

        private AttributeMapper<T> mapper;

        public SingleProperty(AttributeMapper<T> mapper) {
            this.mapper = mapper;
        }
        
    }
    
}
