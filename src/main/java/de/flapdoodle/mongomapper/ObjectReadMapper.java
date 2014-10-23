package de.flapdoodle.mongomapper;

import com.mongodb.DBObject;

public interface ObjectReadMapper<T> extends ReadMapper<T,DBObject> {

}
