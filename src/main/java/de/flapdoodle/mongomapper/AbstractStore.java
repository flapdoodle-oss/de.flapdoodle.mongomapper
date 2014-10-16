package de.flapdoodle.mongomapper;

import java.io.Serializable;
import java.util.logging.Logger;

import com.google.common.base.Optional;
import com.google.common.collect.Range;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;

import de.flapdoodle.guava.Expectations;
import de.flapdoodle.guava.Pair;

public abstract class AbstractStore<K, O, W, R> extends AbstractMapperAdapter<W, R> implements Store<K, W, R> {

    private static Logger LOG = LogFactory.logger();

    private final MongoCollection collection;

    /**
     *
     * Erzeuge eine neue <code>AbstractStore</code> Instanz.
     *
     * @param collection
     *            Collection darf nicht null sein.
     * @param mapper
     *            Mapper darf nicht null sein.
     */
    public <M extends ObjectWriteMapper<W> & ObjectReadMapper<R>> AbstractStore(MongoCollection collection, M mapper) {
        super(collection,mapper);
        this.collection = collection;
    }

    /**
    *
    * Erzeuge eine neue <code>AbstractStore</code> Instanz.
    *
    * @param collection
    *            Collection darf nicht null sein.
    * @param mapper
    *            Mapper darf nicht null sein.
    */
    public <M extends ObjectWriteMapper<W> & ObjectReadMapper<R>> AbstractStore(DBCollection collection, M mapper) {
        this(new DBCollectionAsMongoCollectionImpl(collection), mapper);
    }

    /**
     * Speichert storable in der Datenbank und gibt die ID als String zurück
     *
     * @param storable
     *            zuspeichernde Daten
     * @return
     */
    @Override
    public K store(W storable) {
        return storeEntity(storable);

    }

    protected final K storeEntity(W storable) {
        DBObject dbObject = writeMapper().asDBObject(storable);
        WriteResult result = collection.insert(dbObject);
        if (result.getError()!=null) {
            throw new MongoException("could not store "+storable+",failed with "+result.getError());
        }
        O id = (O) dbObject.get("_id");
        return toId(id);
    }

    /**
     * Speichert storable in der Datenbank und gibt die ID als String zurück
     *
     * @param storable
     *            zuspeichernde Daten
     * @return
     */
    @Override
    public boolean update(K id, W storable) {
        return updateWithConstraint(id,storable,Optional.<Pair<String, ? extends Serializable>>absent());

    }
    
    /**
     * Speichert storable in der Datenbank und gibt die ID als String zurück
     *
     * @param storable
     *            zuspeichernde Daten
     * @return
     */
    protected boolean updateWithConstraint(K id, W storable, Optional<? extends Pair<String, ? extends Serializable>> constraint) {
        DBObject dbObject = writeMapper().asDBObject(storable);
        BasicDBObject query = new BasicDBObject("_id", fromId(id));
        if (constraint.isPresent()) {
            query.append(constraint.get().a(), constraint.get().b());
        }
        WriteResult result = collection.update(query, dbObject);
        if (result.getError()!=null) {
            throw new MongoException("could not update "+storable+",failed with "+result.getError());
        }
        return result.getN()>0;

    }
    
    protected abstract K toId(O id);

    protected abstract O fromId(K id);
    /**
     * Sucht in der Datenbank nach einem Objekt mit id
     *
     * @param id
     *            die Id des Objektes das in der Datenbank gefunden werden soll.
     * @return
     */
    @Override
    public final R get(K id) {
        DBCursor query = collection.find(new BasicDBObject("_id", fromId(id)));
        return readMapper().asObject(Expectations.noneOrOne((Iterable<DBObject>) query).get());
    }
    
    @Override
    public final boolean remove(K id) {
        WriteResult result = collection.remove(new BasicDBObject("_id", fromId(id)));
        if (result.getError()!=null) {
            throw new MongoException("could not remove "+id+",failed with "+result.getError());
        }
        return result.getN()>0;
    }

    /**
     * Sucht in der Datenbank nach einem Objekt mit id
     *
     * @param id
     *            die Id des Objektes das in der Datenbank gefunden werden soll.
     * @return
     */
    @Override
    public final Optional<R> findById(K id) {
        DBCursor query = collection.find(new BasicDBObject("_id", fromId(id)));
        Optional<DBObject> noneOrOne = Expectations.noneOrOne((Iterable<DBObject>) query);
        return noneOrOne.isPresent() ? Optional.of(readMapper().asObject(noneOrOne.get())) : Optional.<R>absent();
    }

    /**
     * Erstellt aus query, sort und range ein Iterable zur Anfrage auf die Datenbank.
     *
     * @param query
     * @param sort
     * @param range
     * @return
     */
    protected SizedIterable<R> find(DBObject query, DBObject sort, Optional<Range<Long>> range) {
        return new DBCollectionSizedIterable<>(readMapper(), collection, query, sort, range);
    }

    protected DBObject defaultSort() {
        return new BasicDBObject();
    }

    public static Optional<Range<Long>> all() {
        return Optional.absent();
    }

}
