package de.flapdoodle.mongomapper.query;

import com.google.common.base.Optional;

public interface Property<T,P extends Property<?, ?>> {

    Optional<P> parentProperty();

    String propertyName();

}
