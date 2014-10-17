package de.flapdoodle.mongomapper.example;

import org.joda.time.DateTime;

import com.google.common.base.Optional;

import de.flapdoodle.mongomapper.AbstractAttributeMapper;
import de.flapdoodle.mongomapper.AttributeValueMap;
import de.flapdoodle.mongomapper.ObjectAsAttributeMapper;
import de.flapdoodle.mongomapper.composite.QueryableDateMapper;
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

    public <P extends Property<?,? extends Property<?,?>>> Props<P> properties(P parent) {
        return new Props<>(parent);
    }
    
    public static class Props<P extends Property<?, ? extends Property<?,?>>> implements Property<Sub,P> {

        private P parent;

        public Props(P parent) {
            this.parent = parent;
        }

        @Override
        public Optional<P> parentProperty() {
            return Optional.of(parent);
        }

        @Override
        public String propertyName() {
            throw new IllegalArgumentException("hmmm..");
        }

        public QueryableProperty<String, P> name() {
            return Properties.queryable(parent, name);
        }
        
        public QueryableDateMapper.Value<? extends Property<DateTime, P>> created() {
            return created.wrapped().value(Properties.with(parent, created));
        }
    }

}
