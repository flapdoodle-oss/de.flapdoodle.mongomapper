package de.flapdoodle.mongomapper.types;

import java.util.Date;

import org.joda.time.DateTime;

import com.google.common.base.Preconditions;

import de.flapdoodle.mongomapper.AttributeMapper;

public class DateMapper implements AttributeMapper<DateTime> {

    private final String name;

    public DateMapper(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Object asDBobject(DateTime value) {
        return value != null ? dateAsDBObject(value) : null;
    }

    @Override
    public DateTime asObject(Object value) {
        return value != null ? objectAsDateTime(value) : null;
    }

    public static Date dateAsDBObject(DateTime value) {
        return Preconditions.checkNotNull(value, "value is null").toDate();
    }

    public static DateTime objectAsDateTime(Object value) {
        return new DateTime(Preconditions.checkNotNull(value, "value is null"));
    }
}
