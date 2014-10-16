package de.flapdoodle.mongomapper;

import com.mongodb.DBObject;

public interface ObjectWriteMapper<T> {

    /**
     * Wandelt ein Objekt in ein DBObject
     *
     * @param value
     * @return
     */
    DBObject asDBObject(T value);

}
