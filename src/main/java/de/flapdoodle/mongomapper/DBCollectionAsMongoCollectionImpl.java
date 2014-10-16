package de.flapdoodle.mongomapper;

import java.util.logging.Logger;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

public class DBCollectionAsMongoCollectionImpl implements MongoCollection {

    private static final Logger LOG=LogFactory.logger();
    
    private final DBCollection collection;

    public DBCollectionAsMongoCollectionImpl(DBCollection collection) {
        this.collection = Preconditions.checkNotNull(collection,"collection is null");
    }

    @Override
    public void ensureIndex(DBObject asIndexKeys, DBObject asIndexOptions) {
        collection.ensureIndex(asIndexKeys, asIndexOptions);
    }

    @Override
    public WriteResult insert(DBObject dbObject) {
        return collection.insert(dbObject);
    }

    @Override
    public WriteResult save(DBObject dbObject) {
        return collection.save(dbObject);
    }
    
    @Override
    public WriteResult update(DBObject query, DBObject dbObject) {
        return collection.update(query,dbObject);
    }

    @Override
    public WriteResult remove(DBObject dbObject) {
        return collection.remove(dbObject);
    }
    
    @Override
    public DBCursor find(DBObject dbObject) {
        return collection.find(dbObject);
    }

    @Override
    public void apply(SchemaMigration migration) {
        LOG.warning("Starting "+migration);
        
        long changes=0;
        long entries=0;
        DBObject query = migration.query();
        
        LOG.warning("Starting "+migration+" with query: "+query);
        
        DBCursor cursor = collection.find(query);
        for (DBObject entry : cursor) {
            entries++;
            Optional<DBObject> rewritten=migration.change(entry);
            if (rewritten.isPresent()) {
                collection.save(rewritten.get());
                changes++;
            }
        }
        
        LOG.warning("Done "+migration+" with "+changes+" changes for "+entries+" entries");
    }
    
    
}
