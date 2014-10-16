package de.flapdoodle.mongomapper.query;

import com.google.common.base.Optional;

import de.flapdoodle.mongomapper.AttributeMapper;

public class SimpleProperty<T> implements QueryProperty<T, VoidProperty> {

    private AttributeMapper<T> mapper;

    public SimpleProperty(AttributeMapper<T> mapper) {
        this.mapper = mapper;
    }
    
    @Override
    public String name() {
        return mapper.name();
    }
    
    @Override
    public Optional<VoidProperty> parent() {
        return Optional.absent();
    }
    
}