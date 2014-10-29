package de.flapdoodle.mongomapper.querybuilder.idea;

import org.junit.Test;

import de.flapdoodle.mongomapper.AbstractAttributeMapper;
import de.flapdoodle.mongomapper.AttributeMapper;
import de.flapdoodle.mongomapper.AttributeValueMap;
import de.flapdoodle.mongomapper.ObjectAsAttributeMapper;
import de.flapdoodle.mongomapper.query.Properties;
import de.flapdoodle.mongomapper.query.Property;
import de.flapdoodle.mongomapper.query.QueryableCascadedProperty;
import de.flapdoodle.mongomapper.query.QueryableProperty;
import de.flapdoodle.mongomapper.query.VoidProperty;
import de.flapdoodle.mongomapper.types.StringMapper;

public class TypedQueryBuilderTest {

    @Test
    public void playSomeIdeas() {
        TypedQueryBuilder<Foo, VoidProperty> builder = TypedQueryBuilder.start(FooMapper.INSTANCE);
        
        builder.is(FooMapper.INSTANCE.foo(), "fooValue");
        //builder.is(FooMapper.INSTANCE.sub().name(), "fooValue");
    }

    static class Bar {
        
    }
    
    static class Sub {
        public final String name;
        
        public Sub(String name) {
            this.name = name;
        }
    }
    
    static class Foo {
        public final String foo;
        public final Sub sub;
        
        public Foo(Sub sub,String foo) {
            this.sub = sub;
            this.foo = foo;
        }
    }
    
    static class SubMapper extends AbstractAttributeMapper<Sub> {
        private static final AttributeMapper<String> NAME=new StringMapper("name");
        
        public static SubMapper INSTANCE=new SubMapper();
        
        private SubMapper() {
            super(NAME);
        }

        public <P extends Property<?,?>> QueryableProperty<String, P> name(P parent) {
            return Properties.queryable(parent, NAME);
        }
        
        
        @Override
        protected AttributeValueMap asAttributeMap(Sub value) {
            return AttributeValueMap.builder()
                    .put(NAME, value.name)
                    .build();
        }

        @Override
        protected Sub asObject(AttributeValueMap attributes) {
            return new Sub(attributes.get(NAME));
        }
    }
    
    static class SubProperty extends QueryableCascadedProperty<Sub, Property<Foo, VoidProperty>> {

        public SubProperty(AttributeMapper<Sub> mapper) {
            super(mapper);
        }
        
        public QueryableProperty<String, SubProperty> name() {
            return Properties.queryable(this, SubMapper.NAME);
        }
        
    }
    
    static class FooMapper extends AbstractAttributeMapper<Foo> {
        private static final AttributeMapper<String> FOO=new StringMapper("foo");
        private static final ObjectAsAttributeMapper<Sub, SubMapper> SUB=new ObjectAsAttributeMapper<Sub, SubMapper>("sub",SubMapper.INSTANCE);
        
        public static FooMapper INSTANCE=new FooMapper();
        
        private FooMapper() {
            super(FOO,SUB);
        }

        public QueryableProperty<String, Property<Foo, VoidProperty>> foo() {
            return Properties.queryable(Foo.class, FOO);
        }
        
        public SubProperty sub() {
            return new SubProperty(SUB);
        }
        
        
        @Override
        protected AttributeValueMap asAttributeMap(Foo value) {
            return AttributeValueMap.builder()
                    .put(SUB, value.sub)
                    .put(FOO, value.foo)
                    .build();
        }

        @Override
        protected Foo asObject(AttributeValueMap attributes) {
            return new Foo(attributes.get(SUB),attributes.get(FOO));
        }
    }
}
