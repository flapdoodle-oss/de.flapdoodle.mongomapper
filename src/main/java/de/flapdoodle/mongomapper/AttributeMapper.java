package de.flapdoodle.mongomapper;

public interface AttributeMapper<T> {

    /**
     * Reicht name als String raus.
     *
     * @return
     */
    String name();

    /**
     * Wandelt value in einem DBObject.
     *
     * @param value
     * @return
     */
    Object asDBobject(T value);

    /**
     * Wandelt value in ein Objekt
     *
     * @param value
     * @return
     */
    T asObject(Object value);
}
