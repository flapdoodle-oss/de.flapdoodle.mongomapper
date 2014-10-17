package de.flapdoodle.mongomapper.querybuilder;

import org.joda.time.DateTime;
import org.junit.Test;

import com.mongodb.DBObject;

import de.flapdoodle.mongomapper.example.RootMapper;

public class QueryBuilderTest {

    @Test
    public void queryBuilderStuff() {
        RootMapper mapper = RootMapper.INSTANCE;
        
        DBObject query = new QueryBuilder()
            .is(mapper.created(), DateTime.now())
            .is(mapper.foo(), "nix")
            .is(mapper.bar(), 2)
            .is(mapper.sub().created().year(), 2014)
            .is(mapper.sub().name(), "sunny")
            .get();
        
        System.out.println("query: "+query);
    }


}
