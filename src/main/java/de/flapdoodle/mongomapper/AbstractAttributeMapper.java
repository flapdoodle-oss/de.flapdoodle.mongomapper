package de.flapdoodle.mongomapper;

import java.util.List;

import com.google.common.collect.ImmutableList;

public abstract class AbstractAttributeMapper<T> extends AbstractAttributeReadWriteMapper<T, T> implements ObjectMapper<T> {

    public AbstractAttributeMapper(AttributeMapper<?> first, AttributeMapper<?> ... mapper) {
        this(ImmutableList.<AttributeMapper<?>>builder().add(first).add(mapper).build());
    }

    public AbstractAttributeMapper(List<AttributeMapper<?>> mapper) {
        super(mapper);
    }

}
