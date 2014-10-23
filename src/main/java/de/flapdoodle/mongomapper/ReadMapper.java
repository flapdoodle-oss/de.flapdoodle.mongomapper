package de.flapdoodle.mongomapper;


public interface ReadMapper<T,S> {
    T asObject(S source);
}
