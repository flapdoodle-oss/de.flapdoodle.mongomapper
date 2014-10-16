package de.flapdoodle.mongomapper;

import org.bson.types.ObjectId;

import com.mongodb.DBCollection;

public abstract class AbstractObjectIdStore<K, W, R> extends AbstractStore<K, ObjectId, W, R> {

    public <M extends ObjectWriteMapper<W> & ObjectReadMapper<R>> AbstractObjectIdStore(MongoCollection collection, M mapper) {
        super(collection, mapper);
    }

    public <M extends ObjectWriteMapper<W> & ObjectReadMapper<R>> AbstractObjectIdStore(DBCollection collection, M mapper) {
        super(collection, mapper);
    }

    @Override
    protected abstract K toId(ObjectId id);

    @Override
    protected abstract ObjectId fromId(K id);

}
