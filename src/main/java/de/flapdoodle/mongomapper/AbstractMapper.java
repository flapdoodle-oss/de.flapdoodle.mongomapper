package de.flapdoodle.mongomapper;

import org.bson.types.ObjectId;

import com.google.common.base.Preconditions;
import com.mongodb.DBObject;

public abstract class AbstractMapper {

    public static <T> T asType(DBObject source, String propertyName, Class<T> type) {
        Object value = source.get(propertyName);
        Preconditions.checkNotNull(value, "property %s is null", propertyName);
        Preconditions.checkArgument(type.isInstance(value), "property %s is not of type %s but %s (%s)", propertyName, type, value,
                value.getClass());
        return (T) value;
    }
    
    public static String idAsString(DBObject source) {
        return id(source).toString();
    }

    public static ObjectId id(DBObject source) {
        return asType(source, "_id", ObjectId.class);
    }
}
