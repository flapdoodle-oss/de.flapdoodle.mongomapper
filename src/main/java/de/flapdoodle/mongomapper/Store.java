package de.flapdoodle.mongomapper;

import com.google.common.base.Optional;
import com.google.common.collect.Range;

public interface Store<K, W, R> {

    public static final Optional<Range<Long>> ALL=Optional.absent();
    
    /**
     * Sucht für einen Key (id) gespeicherte Daten.
     *
     * @param id
     * @return
     */
    R get(K id);


    /**
     * Sucht für einen Key (id) gespeicherte Daten.
     *
     * @param id
     * @return
     */
    Optional<R> findById(K id);
    
    /**
     * Speichert storable und gibt den dazugehörigen Key zurück.
     *
     * @param storable
     * @return
     */
    K store(W storable);
    
    boolean remove(K id);

    boolean update(K id, W storable);
}
