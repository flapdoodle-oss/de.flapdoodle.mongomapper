package de.flapdoodle.mongomapper;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

public interface MongoCollection extends MongoIndexableCollection {

    WriteResult insert(DBObject dbObject);

    DBCursor find(DBObject basicDBObject);

    WriteResult remove(DBObject dbObject);

    WriteResult update(DBObject query, DBObject dbObject);

    WriteResult save(DBObject dbObject);

}
