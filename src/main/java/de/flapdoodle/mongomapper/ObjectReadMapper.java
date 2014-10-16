package de.flapdoodle.mongomapper;

import com.mongodb.DBObject;

public interface ObjectReadMapper<T> {

    /**
     * wandelt ein DBObject in ein Objekt
     *
     * @param dbValue
     * @return
     */
    T asObject(DBObject dbValue);

}
