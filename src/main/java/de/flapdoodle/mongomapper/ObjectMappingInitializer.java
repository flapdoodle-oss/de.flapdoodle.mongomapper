package de.flapdoodle.mongomapper;

import com.google.common.collect.ImmutableList;

public interface ObjectMappingInitializer {

    /**
     * Reicht eine Liste von Indizes raus.
     * @return
     */
    ImmutableList<IndexDefinition> indexes();
}
