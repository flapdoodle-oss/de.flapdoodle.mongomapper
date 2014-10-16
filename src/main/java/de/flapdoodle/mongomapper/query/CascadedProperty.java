package de.flapdoodle.mongomapper.query;

import com.google.common.base.Optional;

import de.flapdoodle.mongomapper.AttributeMapper;

public class CascadedProperty<T, P extends QueryProperty<?, ? extends QueryProperty<?,?>>> implements QueryProperty<T, P> {

    private Optional<P> parent;
    private AttributeMapper<T> mapper;

    public CascadedProperty(P parent, AttributeMapper<T> mapper) {
        this.parent = Optional.of(parent);
        this.mapper = mapper;
    }
    
    public CascadedProperty(AttributeMapper<T> mapper) {
        this.parent = Optional.absent();
        this.mapper = mapper;
    }
    
    @Override
    public String name() {
        return mapper.name();
    }
    
    @Override
    public Optional<P> parent() {
        return parent;
    }
    
}