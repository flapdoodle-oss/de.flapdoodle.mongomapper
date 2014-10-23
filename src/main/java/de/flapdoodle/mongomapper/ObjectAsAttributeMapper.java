package de.flapdoodle.mongomapper;

import com.google.common.base.Preconditions;
import com.mongodb.DBObject;

public class ObjectAsAttributeMapper<T,W extends ObjectMapper<T>> implements AttributeMapper<T> {

    private final W mapper;
    private final String name;
    
    public ObjectAsAttributeMapper(String name, W mapper) {
        this.name = name;
        this.mapper = mapper;
    }
    
    @Override
    public String name() {
        return name;
    }
    
    public W wrapped() {
        return mapper;
    }

    @Override
    public Object asDBObject(T value) {
        return mapper.asDBObject(value);
    }

    @Override
    public T asObject(Object value) {
        if (value!=null) {
            Preconditions.checkArgument(value instanceof DBObject,"value is not of type %s",DBObject.class);
            return mapper.asObject((DBObject) value);
        }
        return null;
    }
}
