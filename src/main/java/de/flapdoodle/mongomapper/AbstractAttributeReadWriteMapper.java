package de.flapdoodle.mongomapper;

import java.util.List;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mongodb.DBObject;

public abstract class AbstractAttributeReadWriteMapper<R,W> extends AbstractMapper implements ObjectReadMapper<R>,ObjectWriteMapper<W> {

    private final ImmutableList<AttributeMapper<?>> mapper;
    private ImmutableSet<String> knowAttributes;

    public AbstractAttributeReadWriteMapper(AttributeMapper<?> first, AttributeMapper<?> ... mapper) {
        this(ImmutableList.<AttributeMapper<?>>builder().add(assertNotNull(first)).add(mapper).build());
    }

    private static AttributeMapper<?> assertNotNull(AttributeMapper<?> first) {
        return Preconditions.checkNotNull(first,"first is null, if you are using static fields and static singleton watch out for init order");
    }

    public AbstractAttributeReadWriteMapper(List<AttributeMapper<?>> mapper) {
        Preconditions.checkArgument(!mapper.isEmpty(), "mapper is empty");
        this.mapper = ImmutableList.copyOf(mapper);
        this.knowAttributes = AttributeMappers.names(mapper);
    }

    @Override
    public final DBObject asDBObject(W value) {
        AttributeValueMap attributesSet = asAttributeMap(value);
        Set<String> mappedAttributes = attributesSet.asMap().keySet();
        Preconditions.checkArgument(knowAttributes.containsAll(mappedAttributes),"not all attribute mapped %s != %s",mappedAttributes, knowAttributes);
        return AttributeMappers.asDBObject(attributesSet, mapper);
    };
    
    protected abstract AttributeValueMap asAttributeMap(W value);

    @Override
    public final R asObject(DBObject dbValue) {
        AttributeValueMap attributes = AttributeMappers.asMap(dbValue, mapper);
        return asObject(attributes);
    }

    protected abstract R asObject(AttributeValueMap attributes);
}
