package de.flapdoodle.mongomapper;

import com.mongodb.DBObject;

public interface MongoIndexableCollection {

    void ensureIndex(DBObject asIndexKeys, DBObject asIndexOptions);

    void apply(SchemaMigration migration);

}
