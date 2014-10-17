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
import de.flapdoodle.mongomapper.types.IntMapper;
import de.flapdoodle.mongomapper.types.StringMapper;

public class RootMapper extends AbstractAttributeMapper<Root> {

    private static final StringMapper foo=new StringMapper("foo");
    private static final IntMapper bar=new IntMapper("bar");
    private static final ObjectAsAttributeMapper<Sub, SubMapper> sub=new ObjectAsAttributeMapper<Sub, SubMapper>("sub", SubMapper.INSTANCE);
    private static final ObjectAsAttributeMapper<DateTime, QueryableDateMapper> created=new ObjectAsAttributeMapper<>("created", new QueryableDateMapper());
    
    public static RootMapper INSTANCE=new RootMapper();
    
    private RootMapper() {
        super(foo,bar,sub,created);
    }

    public QueryableProperty<String, VoidProperty> foo() {
        return Properties.queryable(foo);
    }
    
    public QueryableProperty<Integer, VoidProperty> bar() {
        return Properties.queryable(bar);
    }
    
    public SubMapper.Props<VoidProperty> sub() {
        return sub.wrapped().properties(Optional.<VoidProperty>absent(), sub);
    }
    
    public QueryableDateMapper.Value<Property<DateTime, VoidProperty>> created() {
        return created.wrapped().value(Properties.with(created));
    }
    
    @Override
    protected AttributeValueMap asAttributeMap(Root value) {
        return new AttributeValueMap.Builder()
            .put(foo, value.foo())
            .put(bar, value.bar())
            .put(sub, value.sub())
            .put(created, value.created())
            .build();
    }

    @Override
    protected Root asObject(AttributeValueMap attributes) {
        return new Root(attributes.get(foo), attributes.get(bar),attributes.get(created), Optional.fromNullable(attributes.get(sub)));
    }

}
