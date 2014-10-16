package de.flapdoodle.mongomapper;

public interface SizedIterable<T> extends Iterable<T> {

    /**
     * Größe.
     *
     * @return
     */
    long size();
}
