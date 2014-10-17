package de.flapdoodle.mongomapper.composite;

import org.joda.time.DateTime;

import de.flapdoodle.mongomapper.AbstractAttributeMapper;
import de.flapdoodle.mongomapper.AttributeMapper;
import de.flapdoodle.mongomapper.AttributeValueMap;
import de.flapdoodle.mongomapper.query.CascadedProperty;
import de.flapdoodle.mongomapper.query.Properties;
import de.flapdoodle.mongomapper.query.Property;
import de.flapdoodle.mongomapper.query.QueryableProperty;
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

    public <P extends Property<?, ? extends Property<?,?>>> Value<P> value(P parent) {
        return new Value<P>(parent, this, VALUE);
    }
    
    public class Value<P extends Property<?, ? extends Property<?,?>>> extends CascadedProperty<DateTime, P> implements QueryableProperty<DateTime, P> {

        public Value(P parent, QueryableDateMapper baseMapper, AttributeMapper<DateTime> valueMapper) {
            super(parent, valueMapper);
            
        }
        
        public QueryableProperty<Integer, P> year() {
            return Properties.queryable(this.parentProperty().get(), YEAR);
        }
    }
}
