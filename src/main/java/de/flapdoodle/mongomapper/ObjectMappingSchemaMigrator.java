package de.flapdoodle.mongomapper;

import com.google.common.collect.ImmutableList;

public interface ObjectMappingSchemaMigrator {

    ImmutableList<? extends SchemaMigration> migrations();

}
