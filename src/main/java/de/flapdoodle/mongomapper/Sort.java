package de.flapdoodle.mongomapper;

import java.util.Collection;

import com.google.common.collect.Lists;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import de.flapdoodle.mongomapper.query.Properties;
import de.flapdoodle.mongomapper.query.Property;

public class Sort {
    public static enum Direction {
        Ascent, Descent
    }

    private final Property<?, ? extends Property<?,?>> property;
    private final Direction direction;

    /**
     *
     * Erzeuge eine neue <code>Sort</code> Instanz.
     *
     * @param property
     * @param direction
     */
    public Sort(Property<?, ? extends Property<?,?>> property, Direction direction) {
        this.property = property;
        this.direction = direction;
    }

    /**
     * Gibts Sorts als DBObject zurück.
     *
     * @param sorts
     * @return
     */
    public static DBObject asDBObject(Collection<Sort> sorts) {
        BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        for (Sort s : sorts) {
            builder.add(Properties.name(s.property), asDBObjectValue(s.direction));
        }
        return builder.get();
    }

    /**
     * Setzt die Sortierrichtung
     *
     * @param direction
     * @return
     */
    public static int asDBObjectValue(Sort.Direction direction) {
        return direction == Direction.Ascent ? 1 : -1;
    }

    /**
     * Gibts Sorts als DBObject zurück.
     *
     * @param sorts
     * @return
     */
    public static DBObject asDBObject(Sort... sorts) {
        return asDBObject(Lists.newArrayList(sorts));
    }
}
