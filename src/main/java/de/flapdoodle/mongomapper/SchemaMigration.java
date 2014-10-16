package de.flapdoodle.mongomapper;

import com.google.common.base.Optional;
import com.mongodb.DBObject;

public interface SchemaMigration {

    DBObject query();

    Optional<DBObject> change(DBObject entry);

}
