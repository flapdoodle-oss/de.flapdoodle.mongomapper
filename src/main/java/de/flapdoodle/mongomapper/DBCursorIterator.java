package de.flapdoodle.mongomapper;

import java.util.Iterator;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;


public class DBCursorIterator<T> implements Iterator<T> {
    private final ObjectReadMapper<T> readMapper;
    private final DBCursor cursor;

    /**
     *
     * Erzeuge eine neue <code>DBCursorIterator</code> Instanz.
     *
     * @param readMapper
     * @param cursor
     */
    public DBCursorIterator(ObjectReadMapper<T> readMapper, DBCursor cursor) {
        this.readMapper = readMapper;
        this.cursor = cursor;
    }

    @Override
    public boolean hasNext() {
        return cursor.hasNext();
    }

    @Override
    public T next() {
        DBObject notificationObject = cursor.next();
        return readMapper.asObject(notificationObject);

    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove not supported");
    }

}
