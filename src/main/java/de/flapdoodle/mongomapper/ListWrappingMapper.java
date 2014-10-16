package de.flapdoodle.mongomapper;

public interface ListWrappingMapper<S> {

    ObjectMapper<S> wrappedMapper();

}
