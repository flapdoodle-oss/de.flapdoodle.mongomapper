package de.flapdoodle.mongomapper.example;

import org.joda.time.DateTime;

import de.flapdoodle.mongomapper.AbstractAttributeMapper;
import de.flapdoodle.mongomapper.AttributeValueMap;
import de.flapdoodle.mongomapper.ObjectAsAttributeMapper;
import de.flapdoodle.mongomapper.composite.QueryableDateMapper;
import de.flapdoodle.mongomapper.query.CascadedProperty;
import de.flapdoodle.mongomapper.query.Properties;
import de.flapdoodle.mongomapper.query.QueryableProperty;
import de.flapdoodle.mongomapper.query.VoidProperty;
import de.flapdoodle.mongomapper.types.IntMapper;
import de.flapdoodle.mongomapper.types.StringMapper;

public class RootMapper extends AbstractAttributeMapper<Root> {

    private static final StringMapper foo=new StringMapper("foo");
    private static final IntMapper bar=new IntMapper("bar");
    private static final ObjectAsAttributeMapper<DateTime, QueryableDateMapper> created=new ObjectAsAttributeMapper<>("created", new QueryableDateMapper());
    
    public static RootMapper INSTANCE=new RootMapper();
    
    private RootMapper() {
        super(foo,bar,created);
    }

    public QueryableProperty<String, VoidProperty> foo() {
        return Properties.queryable(foo);
    }
    
    public QueryableProperty<Integer, VoidProperty> bar() {
        return Properties.queryable(bar);
    }
    
    public QueryableDateMapper.Value<CascadedProperty<DateTime, VoidProperty>> created() {
        return created.wrapped().value(new CascadedProperty<DateTime, VoidProperty>(created));
    }
    
    @Override
    protected AttributeValueMap asAttributeMap(Root value) {
        return new AttributeValueMap.Builder()
            .put(foo, value.foo())
            .put(bar, value.bar())
            .put(created, value.created())
            .build();
    }

    @Override
    protected Root asObject(AttributeValueMap attributes) {
        return new Root(attributes.get(foo), attributes.get(bar),attributes.get(created));
    }

}
