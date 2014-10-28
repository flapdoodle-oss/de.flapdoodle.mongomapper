package de.flapdoodle.mongomapper.querybuilder.idea;

import org.junit.Test;

import de.flapdoodle.mongomapper.AbstractAttributeMapper;
import de.flapdoodle.mongomapper.AttributeMapper;
import de.flapdoodle.mongomapper.AttributeValueMap;
import de.flapdoodle.mongomapper.query.Properties;
import de.flapdoodle.mongomapper.query.Property;
import de.flapdoodle.mongomapper.query.QueryableProperty;
import de.flapdoodle.mongomapper.query.VoidProperty;
import de.flapdoodle.mongomapper.types.StringMapper;

public class TypedQueryBuilderTest {

    @Test
    public void playSomeIdeas() {
        TypedQueryBuilder<Foo, VoidProperty> builder = TypedQueryBuilder.start(FooMapper.INSTANCE);
        
        builder.is(FooMapper.INSTANCE.foo(), "fooValue");
    }

    static class Bar {
        
    }
    
    static class Foo {
        public final String foo;
        
        public Foo(String foo) {
            this.foo = foo;
        }
    }
    
    static class FooMapper extends AbstractAttributeMapper<Foo> {
        private static final AttributeMapper<String> FOO=new StringMapper("foo");
        
        public static FooMapper INSTANCE=new FooMapper();
        
        private FooMapper() {
            super(FOO);
        }

        public QueryableProperty<String, Property<Foo, VoidProperty>> foo() {
            return Properties.queryable(Foo.class, FOO);
        }
        
        
        @Override
        protected AttributeValueMap asAttributeMap(Foo value) {
            return AttributeValueMap.builder()
                    .put(FOO, value.foo)
                    .build();
        }

        @Override
        protected Foo asObject(AttributeValueMap attributes) {
            return new Foo(attributes.get(FOO));
        }
    }
}
