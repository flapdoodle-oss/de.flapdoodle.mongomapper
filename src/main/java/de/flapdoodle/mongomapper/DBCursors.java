package de.flapdoodle.mongomapper;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Range;
import com.mongodb.DBCursor;

public abstract class DBCursors {

    private DBCursors() {
        // no instance
    }

    /**
     * Beschränkt den DBCursor auf optionalRange, wenn kein Interval gesetzt wurde, wird der DBCursor direkt zurück gegeben.
     *
     * @param optionalRange
     *            lowerEndpoint und upperEndpoint von Range müssen kleiner sein als Integer.MAX_VALUE
     * @param dbCursor
     * @return
     */
    public static DBCursor selectRange(Optional<Range<Long>> optionalRange, DBCursor dbCursor) {
        if (optionalRange.isPresent()) {
            Range<Long> range = optionalRange.get();
            long longLow = range.lowerEndpoint();
            long longHigh = range.upperEndpoint();

            Preconditions.checkArgument(longLow < Integer.MAX_VALUE, "range.start exceeds Integer.MAX_VALUE");
            Preconditions.checkArgument(longHigh <= Integer.MAX_VALUE, "range.end exceeds Integer.MAX_VALUE");

            int low = (int) longLow;
            int limit = (int) (longHigh - longLow);
            return dbCursor.skip(low).limit(limit);
        }
        return dbCursor;

    }
}
