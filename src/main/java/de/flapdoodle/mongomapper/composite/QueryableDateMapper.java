package de.flapdoodle.mongomapper.composite;

import org.joda.time.DateTime;

import de.flapdoodle.mongomapper.AbstractAttributeMapper;
import de.flapdoodle.mongomapper.AttributeValueMap;
import de.flapdoodle.mongomapper.types.DateMapper;
import de.flapdoodle.mongomapper.types.IntMapper;

public class QueryableDateMapper extends AbstractAttributeMapper<DateTime> {

    private static final DateMapper VALUE=new DateMapper("value");
    private static final IntMapper YEAR=new IntMapper("year");
    
    public QueryableDateMapper() {
        super(VALUE,YEAR);
    }

    @Override
    protected AttributeValueMap asAttributeMap(DateTime value) {
        return new AttributeValueMap.Builder()
            .put(VALUE, value)
            .put(YEAR, value.year().get())
            .build();
    }

    @Override
    protected DateTime asObject(AttributeValueMap attributes) {
        return attributes.get(VALUE);
    }

}
