package de.flapdoodle.mongomapper;

import com.google.common.base.Preconditions;
import com.mongodb.DBObject;

public class ObjectAsAttributeMapper<T> implements AttributeMapper<T> {

    private final ObjectMapper<T> mapper;
    private final String name;
    
    public ObjectAsAttributeMapper(String name, ObjectMapper<T> mapper) {
        this.name = name;
        this.mapper = mapper;
    }
    
    @Override
    public String name() {
        return name;
    }
    
    public ObjectMapper<T> wrapped() {
        return mapper;
    }

    @Override
    public Object asDBobject(T value) {
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
