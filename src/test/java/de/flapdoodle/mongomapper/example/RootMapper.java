package de.flapdoodle.mongomapper.example;

import de.flapdoodle.mongomapper.AbstractAttributeMapper;
import de.flapdoodle.mongomapper.AttributeValueMap;
import de.flapdoodle.mongomapper.types.IntMapper;
import de.flapdoodle.mongomapper.types.StringMapper;

public class RootMapper extends AbstractAttributeMapper<Root> {

    private static final StringMapper foo=new StringMapper("foo");
    private static final IntMapper bar=new IntMapper("bar");
    
    public static RootMapper INSTANCE=new RootMapper();
    
    private RootMapper() {
        super(foo,bar);
    }

    public StringMapper foo() {
        return foo;
    }
    
    public IntMapper bar() {
        return bar;
    }
    
    @Override
    protected AttributeValueMap asAttributeMap(Root value) {
        return new AttributeValueMap.Builder()
            .put(foo, value.foo())
            .put(bar, value.bar())
            .build();
    }

    @Override
    protected Root asObject(AttributeValueMap attributes) {
        return new Root(attributes.get(foo), attributes.get(bar));
    }

}
