package de.flapdoodle.mongomapper.composite;

import org.joda.time.DateTime;

import de.flapdoodle.mongomapper.AbstractAttributeMapper;
import de.flapdoodle.mongomapper.AttributeMapper;
import de.flapdoodle.mongomapper.AttributeValueMap;
import de.flapdoodle.mongomapper.query.CascadedProperty;
import de.flapdoodle.mongomapper.query.QueryProperties;
import de.flapdoodle.mongomapper.query.QueryProperty;
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

    public <P extends QueryProperty<?, ? extends QueryProperty<?,?>>> Properties<P> value(P parent) {
        return new Properties<P>(parent, this, VALUE);
    }
    
    public class Properties<P extends QueryProperty<?, ? extends QueryProperty<?,?>>> extends CascadedProperty<DateTime, P> {

        public Properties(P parent, QueryableDateMapper baseMapper, AttributeMapper<DateTime> valueMapper) {
            super(parent, valueMapper);
            
        }
        
        public CascadedProperty<Integer, P> year() {
            return QueryProperties.with(this.parent().get(), YEAR);
        }
    }
}
