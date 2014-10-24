package de.flapdoodle.mongomapper.querybuilder;

import org.junit.Test;

import de.flapdoodle.mongomapper.query.Properties;
import de.flapdoodle.mongomapper.query.QueryableProperty;
import de.flapdoodle.mongomapper.query.VoidProperty;
import de.flapdoodle.mongomapper.types.StringMapper;

public class QueryBuilderTest {

    @Test
    public void play() {
        
        StringMapper fooAttr=new StringMapper("foo");
        QueryableProperty<String, VoidProperty> foo = Properties.queryable(fooAttr);
        
        new QueryBuilder()
            .is(foo, "bar")
//            .or(a,b,c,d)
            ;
        
    
    }

    
}
