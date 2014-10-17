package de.flapdoodle.mongomapper.example;

import org.joda.time.DateTime;

import com.google.common.base.Optional;

import de.flapdoodle.mongomapper.AbstractAttributeMapper;
import de.flapdoodle.mongomapper.AttributeMapper;
import de.flapdoodle.mongomapper.AttributeValueMap;
import de.flapdoodle.mongomapper.ObjectAsAttributeMapper;
import de.flapdoodle.mongomapper.composite.QueryableDateMapper;
import de.flapdoodle.mongomapper.query.CascadedProperty;
import de.flapdoodle.mongomapper.query.CompositeProperty;
import de.flapdoodle.mongomapper.query.Properties;
import de.flapdoodle.mongomapper.query.Property;
import de.flapdoodle.mongomapper.query.QueryableProperty;
import de.flapdoodle.mongomapper.query.VoidProperty;
import de.flapdoodle.mongomapper.types.StringMapper;

public class SubMapper extends AbstractAttributeMapper<Sub> {

    private static final StringMapper name=new StringMapper("name");
    private static final ObjectAsAttributeMapper<DateTime, QueryableDateMapper> created=new ObjectAsAttributeMapper<>("created", new QueryableDateMapper());
    
    public static SubMapper INSTANCE=new SubMapper();
    
    private SubMapper() {
        super(name,created);
    }

    public QueryableProperty<String, VoidProperty> name() {
        return Properties.queryable(name);
    }
    
    public QueryableDateMapper.Value<Property<DateTime, VoidProperty>> created() {
        return created.wrapped().value(Properties.with(created));
    }
    
    @Override
    protected AttributeValueMap asAttributeMap(Sub value) {
        return new AttributeValueMap.Builder()
            .put(name, value.name())
            .put(created, value.created())
            .build();
    }

    @Override
    protected Sub asObject(AttributeValueMap attributes) {
        return new Sub(attributes.get(name), attributes.get(created));
    }

    public <P extends Property<?,? extends Property<?,?>>> Props<P> properties(Optional<P> parent, AttributeMapper<Sub> mapper) {
        return new Props<P>(parent, mapper);
    }
    
    public static class Props<P extends Property<?, ? extends Property<?,?>>> extends CascadedProperty<Sub,P> implements CompositeProperty<Sub, P> {
        
        public Props(Optional<P> parent, AttributeMapper<Sub> mapper) {
            super(parent, mapper);
        }

        public QueryableProperty<String, Props<P>> name() {
            return Properties.queryable(this, name);
        }
        
        public QueryableDateMapper.Value<? extends Property<DateTime, Props<P>>> created() {
            return created.wrapped().value(Properties.with(this, created));
        }
        
        @Override
        public AttributeMapper<Sub> mapper() {
            return super.mapper();
        }
    }

}
