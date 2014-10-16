package de.flapdoodle.mongomapper;

import com.mongodb.DBCollection;

public abstract class AbstractCompositeIdStore<K, W, R> extends AbstractStore<K, String, W, R> {

    public <M extends ObjectWriteMapper<W> & ObjectReadMapper<R>> AbstractCompositeIdStore(MongoCollection collection, M mapper) {
        super(collection, mapper);
    }

    public <M extends ObjectWriteMapper<W> & ObjectReadMapper<R>> AbstractCompositeIdStore(DBCollection collection, M mapper) {
        super(collection, mapper);
    }

    @Override
    protected abstract K toId(String id);

    @Override
    protected abstract String fromId(K id);

}
