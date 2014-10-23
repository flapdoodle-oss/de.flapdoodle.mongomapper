package de.flapdoodle.mongomapper;


public interface WriteMapper<T,D> {
    D asDBObject(T value);
}
