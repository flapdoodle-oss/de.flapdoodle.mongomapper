package de.flapdoodle.mongomapper;

public interface AttributeMapper<T> extends ReadMapper<T, Object>,WriteMapper<T, Object> {

    /**
     * Reicht name als String raus.
     *
     * @return
     */
    String name();

}
