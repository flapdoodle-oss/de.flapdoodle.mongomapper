package de.flapdoodle.mongomapper.query;

import com.google.common.base.Optional;

public interface QueryProperty<T,P extends QueryProperty<?, ?>> {
    
    Optional<P> parent();

    String name();
}
