package de.flapdoodle.mongomapper;

import org.bson.types.ObjectId;

import com.mongodb.DBCollection;

public abstract class AbstractStringIdStore<W, R> extends AbstractObjectIdStore<String, W, R> {

    public <M extends ObjectWriteMapper<W> & ObjectReadMapper<R>> AbstractStringIdStore(DBCollection collection, M mapper) {
        super(collection, mapper);
    }

    @Override
    protected String toId(ObjectId id) {
        return id.toString();
    }

    @Override
    protected ObjectId fromId(String id) {
        return new ObjectId(id);
    }

}
