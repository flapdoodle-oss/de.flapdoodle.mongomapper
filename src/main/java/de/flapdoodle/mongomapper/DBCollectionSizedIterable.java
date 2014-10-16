package de.flapdoodle.mongomapper;

import java.util.Iterator;

import com.google.common.base.Optional;
import com.google.common.collect.Range;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class DBCollectionSizedIterable<T> implements SizedIterable<T> {

    private final MongoCollection collection;
    private final DBObject query;
    private final Optional<Range<Long>> range;
    private final DBObject sort;
    private final ObjectReadMapper<T> objectMapper;

    /**
     *
     * Erzeuge eine neue <code>DBCollectionSizedIterable</code> Instanz.
     *
     * @param objectMapper
     * @param collection
     * @param query
     * @param sort
     * @param range
     */
    public DBCollectionSizedIterable(ObjectReadMapper<T> objectMapper, MongoCollection collection, DBObject query, DBObject sort,
            Optional<Range<Long>> range) {

        this.objectMapper = objectMapper;
        this.collection = collection;
        this.query = query;
        this.sort = sort;
        this.range = range;
    }

    /**
     * Gibt einen DBCursor raus, der sich innerhalb der zuvor definierten Range befindet.
     *
     * @return
     */
    protected DBCursor cursor() {
        return DBCursors.selectRange(range, collection.find(query).sort(sort));
    }

    @Override
    public Iterator<T> iterator() {
        return new DBCursorIterator<>(objectMapper, cursor());
    }

    @Override
    public long size() {
        return collection.find(query).size();
    }

}
