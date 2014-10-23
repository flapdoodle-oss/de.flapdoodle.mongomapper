package de.flapdoodle.mongomapper.types;

import org.bson.types.ObjectId;

import de.flapdoodle.mongomapper.AttributeMapper;

public final class ObjectIdMapper implements AttributeMapper<ObjectId> {

    @Override
    public String name() {
        return "_id";
    }

    @Override
    public Object asDBObject(ObjectId value) {
        return value;
    }

    @Override
    public ObjectId asObject(Object value) {
        return (ObjectId) value;
    }
}
